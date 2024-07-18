import java.util.*;
import java.io.*;
import java.net.*;
import java.text.*;

public class Worker extends Thread {

    private Socket clientSocket;
    private W_Server worker;

    public Worker(Socket socket,W_Server worker) {//change and lock room not list
        this.worker = worker;
        this.clientSocket = socket;
    }

    @Override
    public void run() {

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter writer = new PrintWriter(clientSocket.getOutputStream(), true);

            // Read data from the client
            String requestData = reader.readLine();

            List<String> Ddata = Serializer.deserializeList(requestData);
            int operation = Integer.parseInt(Ddata.get(2));

            String response;

            if (Ddata.get(0).equals("t")) {

                switch (operation) {
                    case 1:
                        List<String> data = new ArrayList<>() ;
                        if(Ddata.get(3).equals("exit")){
                            data.add("none");
                            data.add("none");
                            response = search(data);
                            break;
                        }
                        
                        int i = 3;
                        String proxeiro = "";
                        while(!(Ddata.get(i).equals("exit"))){
                            data.add(Ddata.get(i));
                            data.add(Ddata.get(i+1));
                            proxeiro += search(data);
                            i+=2;
                            data.clear();
                        }
                        System.out.println("before sending response inside worker "+ proxeiro);
                        String[] items = proxeiro.split(",");
                        Set<String> uniqueItems = new HashSet<>();
                        for (String item : items) {
                            uniqueItems.add(item);
                        }
                        response = String.join(",", uniqueItems);
                        System.out.println("before sending response inside worker response "+ response);
   
                        break;
                    case 2:
                        response = reserveDates(Ddata);
                        break;
                    case 3:
                        System.out.println("about to make rating");
                        response = rate(Ddata);
                        break;
                    case 10:
                        response = RoomData(Ddata.get(3));
                        break;
                    default:
                        response = "Invalid operation";
                        break;
                }

            } else {

                switch (operation) {
                    case 1:
                        response = addRoom(Ddata);
                        break;
                    case 2:
                        response = addDates(Ddata);
                        break;
                    case 3:
                        response = seeReservations(Ddata);
                        break;
                    case 5:
                        response = ReservationsByArea(Ddata);
                        break;
                    default:
                        response = "Invalid operation";
                        break;
                }
            }

            writer.println(response);
            clientSocket.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String RoomData(String roomName){
        synchronized(worker.getArrayRooms()) {
            while (worker.isWriting()) {
                try {
                    worker.getArrayRooms().wait();  
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    System.err.println("Thread Interrupted");
                    return "Thread Interrupted";
                }
                
            }
            worker.setReading(true);        
        }

        try{
            synchronized(worker.getArrayRooms()){
                List<Room> rooms = worker.getArrayRooms();
                for(Room r:rooms){
                    if(r.getName().equals(roomName)){
                        return r.getOwner()+" "+r.getArea()+" "+r.getNumOfPersons()+" "+r.getPrice()+" "+r.getStars();
                    }
                }
                return "Room not found";        
            }
        }finally{
            synchronized(worker.getArrayRooms()) {
                worker.setReading(false);
                worker.getArrayRooms().notifyAll(); // Ensure that threads waiting outside the loop also get notified
            }
        }
        
    }

    public String ReservationsByArea(List<String> Data){
        
        synchronized(worker.getArrayRooms()) {
            while (worker.isWriting()) {
                try {
                    worker.getArrayRooms().wait();  
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    System.err.println("Thread Interrupted");
                    return "Thread Interrupted";
                }
                
            }
            worker.setReading(true);        
        }
        String reservations = "";

        try{
            synchronized(worker.getArrayRooms()){
                List<Room> rooms = worker.getArrayRooms();
                for(Room room:rooms){
                    List<String> rs = room.getReserveRange(); 
                    int c = 0;
                    for(String r : rs ){
                        if(compare(Data.get(3),r)){
                            c++;
                        }
                    }
                    reservations = reservations + room.getArea() +","+ c + ",";
                }
                return reservations.substring(0, reservations.length() - 1);
            }
            
        }finally {
            synchronized(worker.getArrayRooms()) {
                worker.setReading(false);
                worker.getArrayRooms().notifyAll(); // Ensure that threads waiting outside the loop also get notified
            }
        }
       
    }
    public boolean compare(String d1,String d2){// d1 = 02/02/2024-03/04/2024,d2 = 03/04/2024-04/05/2024
        try{
            String[] dateParts = d1.split("-");
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            Date s1 = dateFormat.parse(dateParts[0].trim());
            Date e1 = dateFormat.parse(dateParts[1].trim());

            dateParts = d2.split("-");
            dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            Date s2 = dateFormat.parse(dateParts[0].trim());
            Date e2 = dateFormat.parse(dateParts[1].trim());

            if(s1.after(e2) || e1.before(s2)){
                return false;
            }
            
        }catch(ParseException e){
            e.printStackTrace();
        }
        return true;
    }

    public String rate(List<String> Data) {
        synchronized(worker.getArrayRooms()) {
            while (worker.isWriting() || worker.isReading()) {
                try {
                    worker.getArrayRooms().wait();  
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    System.err.println("Thread Interrupted");
                    return "Thread Interrupted";
                }
                
            }
            worker.setWriting(true);
                
        }
        System.out.println("inside rating about to make rate");
        try {
            synchronized(worker.getArrayRooms()) {
                System.out.println(worker.getRoom(Data.get(3)));
                if (worker.getRoom(Data.get(3)) != null) {
                    worker.getRoom(Data.get(3)).setReview(Integer.parseInt(Data.get(4)));
                    return "The rating was successful!";
                }else{
                    return "The rating was not successful!"; 
                }
            }
        } finally {
            synchronized(worker.getArrayRooms()) {
                worker.setWriting(false);
                worker.getArrayRooms().notifyAll(); // Ensure that threads waiting outside the loop also get notified
            }
        }
    
          
    }
    
    public synchronized String reserveDates(List<String> Data) {

        synchronized(worker.getArrayRooms()) {
            while (worker.isWriting() || worker.isReading()) {
                try {
                    worker.getArrayRooms().wait();

                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    System.err.println("Thread Interrupted");
                    return "Thread Interrupted";
                }
            }
            worker.setWriting(true);
        }
        
        String[] dateParts = Data.get(4).split("-");
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        
        try
        { 
            
            Date startDate = new Date();
            Date endDate = new Date();
            try{
                startDate = dateFormat.parse(dateParts[0].trim());
                endDate = dateFormat.parse(dateParts[1].trim());
            }catch(ParseException e){
                e.printStackTrace();
            }
            List<Date> dates = new ArrayList<>();
            Calendar cal = Calendar.getInstance();
            cal.setTime(startDate);
            dates.add(startDate);
            while (cal.getTime().before(endDate)) {
                cal.add(Calendar.DATE, 1);
                dates.add(cal.getTime());
            }
            dates.add(endDate);
            synchronized(worker.getArrayRooms()){
                if( worker.getRoom(Data.get(3)) != null){
                    for (int i = 0; i < dates.size(); i++) {
                        if (!(worker.getRoom(Data.get(3)).getAvailableDates().contains(dates.get(i)))) {
                            
                            return "The booking was not successful";
                        }    
                    }
                    worker.getRoom(Data.get(3)).setReservations(startDate, endDate);
                    worker.getRoom(Data.get(3)).setReserveRange(Data.get(4));
                    return "The booking was successful";    
                }else{
                    
                    return "Room not found!Booking failed";
                }
            }
            
            

        }finally{
            synchronized(worker.getArrayRooms()){
                System.out.println("finally reserveDates,about to notify");
                worker.setWriting(false);
                worker.getArrayRooms().notifyAll();
            }
        }   
        

    }

    public synchronized String addDates(List<String> Data) {

        synchronized(worker.getArrayRooms()) {
            while (worker.isWriting() || worker.isReading()) {
                try {
                    worker.getArrayRooms().wait();  
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    System.err.println("Thread Interrupted");
                    return "Thread Interrupted";
                }
                
            }
            worker.setWriting(true);
                
        }
       
        String[] dateParts = Data.get(4).split("-");
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        
        try {
            Date startDate = dateFormat.parse(dateParts[0].trim());
            Date endDate = dateFormat.parse(dateParts[1].trim());

            List<Date> dates = new ArrayList<>();
            Calendar cal = Calendar.getInstance();
            cal.setTime(startDate);
            dates.add(startDate);
            while (cal.getTime().before(endDate)) {
                cal.add(Calendar.DATE, 1);
                dates.add(cal.getTime());
            }
            dates.add(endDate);
            synchronized(worker.getArrayRooms()){       
                if (worker.getRoom(Data.get(3))!= null){
                    for (int i = 0; i < dates.size(); i++) {
                        if (worker.getRoom(Data.get(3)).getReservations().contains(dates.get(i))) {
                            worker.getRoom(Data.get(3)).getReservations().remove(dates.get(i));
                        }
                    }
                    worker.getRoom(Data.get(3)).setAvailableDates(startDate, endDate);
                    if(worker.getRoom(Data.get(3)).getReserveRange().contains(Data.get(4))){
                        worker.getRoom(Data.get(3)).getReserveRange().remove(Data.get(4));
                    }    
                    return "The dates were added susccessfully!";
                }else{
                    return "Couldn't find room!Dates were not added"; 
                }
            }
        }catch(ParseException e){
            e.printStackTrace();
            return "There was an error in addDates";
        }finally{
            synchronized(worker.getArrayRooms()){
                worker.setWriting(false);
                worker.getArrayRooms().notifyAll();
            }
        } 

    }

    public synchronized String seeReservations(List<String> Data) {

        synchronized(worker.getArrayRooms()) {
            while (worker.isWriting()) {
                try {
                    worker.getArrayRooms().wait();  
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    System.err.println("Thread Interrupted");
                    return "Thread Interrupted";
                }
                
            }
            worker.setReading(true);        
        }
        String reservations = "";
        try{
            
            synchronized(worker.getArrayRooms()){
                List<Room> rooms = worker.getArrayRooms();
                for (Room room : rooms) {
                    if (room.getOwner().equals(Data.get(1))) {
                        reservations += room.getReservations();
                    }
                }
            }
        }finally {
            synchronized(worker.getArrayRooms()) {
                worker.setReading(false);
                worker.getArrayRooms().notifyAll(); // Ensure that threads waiting outside the loop also get notified
            }
        }
        return reservations;

    }

    public synchronized String addRoom(List<String> Data) {

        synchronized(worker.getArrayRooms()) {
            while (worker.isWriting() || worker.isReading()) {
                try {
                    worker.getArrayRooms().wait();  
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    System.err.println("Thread Interrupted");
                    return "Thread Interrupted";
                }
                
            }
            worker.setWriting(true);
                
        }
       
        Room r = new Room(Data.get(3));

        r.setOwner(Data.get(1));
        r.setNumOfPersons(Integer.parseInt(Data.get(4)));
        r.setArea(Data.get(5));
        r.setPrice(Integer.parseInt(Data.get(6)));
        r.setImage(Data.get(7));

        
        synchronized(worker.getArrayRooms()) {
            worker.addRoom(r);
            worker.setWriting(false);
            worker.getArrayRooms().notifyAll(); // Ensure that threads waiting outside the loop also get notified
        }
        return "Room added succesfully!";
        
        
    }

    public synchronized String search(List<String> Data) {

        synchronized(worker.getArrayRooms()) {
            while (worker.isWriting()) {
                try {
                    worker.getArrayRooms().wait();  
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    System.err.println("Thread Interrupted");
                    return "Thread Interrupted";
                }
                
            }
            worker.setReading(true);
                
        }

        List<Pair<String, String>> pairList = new ArrayList<>();

        if (Data.get(0).equals("startDate,endDate")) {

            try {
                String[] dateParts = Data.get(1).split("-");
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

                Date startDate = dateFormat.parse(dateParts[0].trim());
                Date endDate = dateFormat.parse(dateParts[1].trim());

                List<Date> dates = new ArrayList<>();
                Calendar cal = Calendar.getInstance();
                cal.setTime(startDate);
                dates.add(startDate);
                while (cal.getTime().before(endDate)) {
                    cal.add(Calendar.DATE, 1);
                    dates.add(cal.getTime());
                }
                dates.add(endDate);
                List<Room> rooms;
                synchronized(worker.getArrayRooms()){
                    rooms = worker.getArrayRooms();
                }
                for (Room room : rooms) {
                    for (int i = 0; i < dates.size(); i++) {
                        if (!(room.getAvailableDates().contains(dates.get(i)))) {
                            break;
                        }
                        if (i == dates.size() - 1) {
                            pairList.add(new Pair<>(room.getName(), Data.get(1)));
                        }
                    }

                }
            } catch (ParseException e) {
                System.out.println("Error parsing date: " + e.getMessage());
            }

        } else {
            pairList = map(Data.get(0), Data.get(1));
        }
        
        StringBuilder result = new StringBuilder();
        for (Pair<String, String> pair : pairList) {
            result.append(pair.getKey()).append(",").append(pair.getValue()).append(",");
        }
        System.out.println("result in worker "+result);
        synchronized(worker.getArrayRooms()) {
            worker.setReading(false);
            worker.getArrayRooms().notifyAll(); 
        } 
        return result.toString();
        

    }

    public List<Pair<String, String>> map(String typef, String filter) {

        List<Pair<String, String>> pairList = new ArrayList<>();
        List<Room> rooms;
        synchronized(worker.getArrayRooms()){
            rooms = worker.getArrayRooms();
        }
        switch (typef) {

            case "area":
                for (Room room : rooms) {
                    if (room.getArea().equals(filter)) {
                        pairList.add(new Pair<>(room.getName(), filter));
                    }
                }
                break;
            case "persons":
                for (Room room : rooms) {
                    if (room.getNumOfPersons() == Integer.parseInt(filter)) {
                        pairList.add(new Pair<>(room.getName(), filter));
                        
                    }
                }
                break;
            case "price":
                for (Room room : rooms) {
                    if (room.getPrice() == Integer.parseInt(filter)) {
                        pairList.add(new Pair<>(room.getName(), filter));
                    }
                }
                break;
            case "star":
                for (Room room : rooms) {
                    if (room.getStars() == Integer.parseInt(filter)) {
                        pairList.add(new Pair<>(room.getName(), filter));
                    }
                }
                break;
            case "none":
                for (Room room : rooms) { 
                    pairList.add(new Pair<>(room.getName(), filter));    
                }
            default:
                return null;
        }
        
        return pairList;

    }

}