
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.*;


public class M_Server {
    public static final int PORT = 8080; // port for client connection


    
    public static void main(String[] args) {
       
        List<Room> rooms = new ArrayList<>();
        rooms.add(makeRoom("Room16","Maria",3,"ath","www.image",500,"01/06/2024-31/07/2024"));
        rooms.add(makeRoom("Room24","Dimitra",3,"skg","www.image",300,"01/06/2024-31/07/2024"));
        rooms.add(makeRoom("Room35","Giannis",4,"ath","www.image",500,"01/06/2024-31/07/2024"));
        rooms.add(makeRoom("Room41","Maria",3,"ath","www.image",500,"01/06/2024-31/07/2024"));
        rooms.add(makeRoom("Room52","Dimitra",3,"skg","www.image",300,"01/06/2024-31/07/2024"));
        rooms.add(makeRoom("Room63","Giannis",4,"ath","www.image",500,"01/06/2024-31/07/2024"));
        Master masterInit = new Master(Integer.parseInt(args[0]),rooms);

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Server started. Waiting for client connection...");
            Socket clientSocket;
            while (true) {
                clientSocket = serverSocket.accept();
                System.out.println("A client connected to server");
                // Create a new thread for the client
                Thread clientThread = new Thread(new Master(clientSocket));
                clientThread.start();

            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            System.out.println("All threads have finished. Closing the server socket...");
        }
    }

    public static Room makeRoom(String name,String owner,int numOfPersons,String area,String image,int price,String d2){
        Room room = new Room(name);
        room.setOwner(owner);
        room.setNumOfPersons(numOfPersons);
        room.setArea(area);
        room.setImage(image);
        room.setPrice(price);

        
        Date startDate = new Date();
        Date endDate = new Date();
        try{
            String[] dateParts = d2.split("-");
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");           
            startDate = dateFormat.parse(dateParts[0].trim());
            endDate = dateFormat.parse(dateParts[1].trim());
        }catch(ParseException e){
            e.printStackTrace();
        }
        room.setAvailableDates(startDate,endDate);
        return room;
    }

}
