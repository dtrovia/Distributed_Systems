package com.example.tryvol2;

import android.util.Log;
import java.util.*;
import java.io.*;
import java.net.*;


public class Client implements Runnable {

    public static final String SERVER_ADDRESS = "192.168.1.65";
    public static final int SERVER_PORT = 8080;
    private  List<String> information = new ArrayList<>();
    private boolean requestexit = false;
    private boolean listready = false;
    private ClientCallBack activity;


    public Client(){
        Log.d("Client","made an object");
    }

    @Override
    public void run() {
        try {
            Log.d("00000000","inside client run") ;

            // Connect to server
            Socket socket = new Socket(Client.SERVER_ADDRESS, Client.SERVER_PORT);

           //handle request
            while(!requestexit){

                if(listready){
                    Log.d("00000000","about to make the request") ;
                    String data = Serializer.serializeList(information);
                    sendRequestToServer(socket,data);
                    processResponseFromServer(socket);
                    listready = false;
                }
            }
            sendRequestToServer(socket,"exit");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setExit(){requestexit = true;}
    public void makeData(List<String> data,ClientCallBack activity){
        Log.d("00000000","inside make data") ;

        Log.d("00000000",data.get(1)) ;
        information=data ;
        listready = true ;
        this.activity = activity;
    }

    private void sendRequestToServer(Socket socket, String info) throws IOException {
        OutputStream output = socket.getOutputStream();
        PrintWriter writer = new PrintWriter(output, true);
        writer.println(info);
    }

    private void processResponseFromServer(Socket socket) throws IOException {
        InputStream in = socket.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        String response = reader.readLine();
        activity.onCalculationResult(response);
    }




}

