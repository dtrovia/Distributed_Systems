

import java.io.*;
import java.net.*;
import java.util.*;

public class W_Server extends Thread {

    private int port ;
    private ServerSocket serverSocket;
    private List<Room> rooms = new ArrayList<>();
    private boolean writing = false;
    private boolean reading = false;

    public W_Server(int port) {
        this.port = port;
        try {
            this.serverSocket = new ServerSocket(this.port);//through command
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    

    @Override
    public void run(){
        while (true) {
            try {
                Socket clientSocket = serverSocket.accept();
                System.out.println(
                        "Worker on port " + port + " received connection from " + clientSocket.getInetAddress());
                // Handle client requests in a separate thread
                new Worker(clientSocket,this).start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    

    public synchronized void setWriting(boolean writing) {
        this.writing = writing;
    }
    public synchronized void setReading(boolean reading) {
        this.reading = reading;
    }
    public synchronized void addRoom(Room room){
       
        this.rooms.add(room);
    }


    public synchronized Room getRoom(String roomName){
        for(Room room:rooms){
            if(room.getName().equals(roomName)){
                
                return room;
            }
        }
        return null;
    }
    public synchronized boolean isWriting() {
        return writing;
    }
    public synchronized boolean isReading() {
        return reading;
    }
    public List<Room> getArrayRooms(){return rooms;}

}

