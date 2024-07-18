
import java.util.*;
import java.net.*;
import java.io.*;

public class Master implements Runnable {
    /* reduce inside Master in a method filter rooms */

    private static int workers = 0;
    private static final List<W_Server> workerObj = new ArrayList<>();
    private Socket socket;
    public static final List<Integer> WorkerPorts = new ArrayList<>();
    public static final String WORKER_ADDRESS = "localhost";

    public Master(int workers,List<Room> rooms) {
        Master.workers = workers;
        initializeWorkers(rooms);
    }

    Master() {
    }

    public void initializeWorkers(List<Room> rooms) {
        System.out.println("initialize workers inside Master");
       
        ArrayList<Integer> proxeiro = new ArrayList<>();
        for(int i =0; i<rooms.size(); i++){
            proxeiro.add(calcNodeId(rooms.get(i).getName()));
        }
        for (int i = 0; i < Master.workers; i++) {
            W_Server w = new W_Server(9090 + i);//need to change
            workerObj.add(w);
            WorkerPorts.add(9090 + i);   
        }
        for(int i=0; i< proxeiro.size();i++){
            workerObj.get(proxeiro.get(i)).addRoom(rooms.get(i));
        }

        for(int i=0; i< Master.workers;i++){
            Master.workerObj.get(i).start();
        }
        
        

    }

    public Master(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        BufferedReader in = null;
        PrintWriter out = null;
        try {
            System.out.println("inside Master");
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);
            // read data from client
            String data = in.readLine();
            System.out.println("Message read succesfully: " + data);
            while(!(data.equals("exit"))){
                

                 // deserialize data
                List<String> des_data = Serializer.deserializeList(data);
                List<String> filters = new ArrayList<>();
                for(int i=4 ; i<des_data.size(); i=i+2){
                    filters.add(des_data.get(i));
                }

                // depending on the user,option made call the right worker
                int option = Integer.parseInt(des_data.get(2));
                
                if ((des_data.get(0).equals("m") && (option == 3 || option == 5)) || (des_data.get(0).equals("t") && option == 1)) {
                    // call all workers
                    System.out.println("Inside if calling all workers....");

                    String response = "";

                    for (int i = 0; i < WorkerPorts.size(); i++) {
                        try {

                            // Connect to the worker server
                            Socket workerSocket = new Socket(WORKER_ADDRESS, WorkerPorts.get(i));

                            // Send serialized to server
                            sendRequest(workerSocket, data);
                            // Receive and process respone
                            response += processResponse(workerSocket);

                            workerSocket.close();

                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        response += ",";
                    }
                    
                    if(option == 5){
                        List<String> proxeiro = Serializer.deserializeList(response);
                        List<String> areas = new ArrayList<>();
                        for(int i=0; i<proxeiro.size(); i=i+2){
                            if(!areas.contains(proxeiro.get(i))){
                                areas.add(proxeiro.get(i));
                                areas.add(proxeiro.get(i+1));
                            }else{
                                int j = areas.indexOf(proxeiro.get(i));
                                int k = Integer.parseInt(areas.get(j+1));
                                int l = Integer.parseInt(proxeiro.get(i+1)) ;
                                int sum = k+l;
                                areas.set(j+1, String.valueOf(sum));
                                    
                                
                            }
                        }
                        out.println(Serializer.serializeList(areas));
                    }else{
                        System.out.println("response before reduce "+response);
                        String reducedResponse = reducer(response,filters);
                        System.out.println("response after "+reducedResponse);
                        out.println(reducedResponse);
                    }
                    
                } else {

                    // call worker with workerId
                    System.out.println("Inside if calling workerId....");
                    int workerId = calcNodeId(des_data.get(3));

                    try {

                        // Connect to the worker server
                        System.out.println(workerId);
                        System.out.println("this is the port"+ Master.WorkerPorts.get(workerId));
                        Socket workerSocket = new Socket(WORKER_ADDRESS, Master.WorkerPorts.get(workerId));

                        // Send serialized to server
                        sendRequest(workerSocket, data);
                        // Receive and process respone
                        String Response = processResponse(workerSocket);

                        // respond to Client
                        out.println(Response);

                        workerSocket.close();

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                // read data from client
                data = in.readLine();
                System.out.println("Message read succesfully: " + data);
            }
            System.out.println("Server Master closing connection with client");
            socket.close();
            
           
            

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static void sendRequest(Socket socket, String info) throws IOException {
        OutputStream output = socket.getOutputStream();
        PrintWriter writer = new PrintWriter(output, true);
        writer.println(info);
    }

    private static String processResponse(Socket socket) throws IOException {
        InputStream in = socket.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        String response = reader.readLine();
        System.out.println("Client received response\nServer response: " + response);
        return response;
    }

    public int calcNodeId(String roomName) {
        return Math.abs(roomName.hashCode()) % Master.workers;
    }

    public String reducer(String pairListToReduce,List<String> filters) {//different reducer

        List<String> values = Serializer.deserializeList(pairListToReduce); 
        List<String> proxeiro = new ArrayList<>(values); 
        for(String f:filters){
            System.out.println("this is the filter "+f);
            while (proxeiro.contains(f)) {
                proxeiro.remove(f);
            }
            
        }
        while (proxeiro.contains("")) {
            proxeiro.remove("");
        }
        
        Set<String> uniqueValuesSet = new HashSet<>(proxeiro);
        String uniqueValuesString = String.join(",", uniqueValuesSet);

        return uniqueValuesString;

    }

}