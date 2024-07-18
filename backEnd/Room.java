import java.util.*;

public class Room {

    private String name;// acts as id

    private String owner;
    private int numOfPersons;
    private int star = 3;
    private int sumStar = 3;
    private int numOfReviews = 1;// = number of stars,#stars
    private String area;
    private String image;// url of image
    private int price;

    private ArrayList<Date> availableDates = new ArrayList<>();
    private ArrayList<Date> reservations = new ArrayList<>();
    private List<String> reserve_range  = new ArrayList<>();
    

    public Room(String name) {
        this.name = name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public void setReview(int rating) {
        this.sumStar += rating;
        this.numOfReviews++;
        this.star = this.sumStar / this.numOfReviews;
    }

    public void setNumOfPersons(int numOfPersons) {
        this.numOfPersons = numOfPersons;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setAvailableDates(Date d1, Date d2) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(d1);
        this.availableDates.add(d1);

        // Iterate through dates and add them to the list
        while (cal.getTime().before(d2)) {
            cal.add(Calendar.DATE, 1);
            this.availableDates.add(cal.getTime());
        }
        this.availableDates.add(d2);
    }

    public void setReservations(Date d1, Date d2) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(d1);
        this.reservations.add(d1);
        this.availableDates.remove(d1);

        // Iterate through dates and add them to the list
        while (cal.getTime().before(d2)) {
            cal.add(Calendar.DATE, 1);
            this.reservations.add(cal.getTime());
            this.availableDates.remove(cal.getTime());
        }
        this.reservations.add(d2);
        this.availableDates.remove(d2);
        
    }
    public void setReserveRange(String range){this.reserve_range.add(range);}

    public String getName() {
        return this.name;
    }

    public ArrayList<Date> getAvailableDates() {
        return this.availableDates;
    }

    public ArrayList<Date> getReservations() {
        return this.reservations;
    }
    public List<String> getReserveRange(){return this.reserve_range;}

    public int getPrice() {
        return this.price;
    }

    public int getNumOfPersons() {
        return this.numOfPersons;
    }

    public String getArea() {
        return this.area;
    }

    public int getStars() {
        return this.star;
    }

    public String getOwner() {
        return this.owner;
    }

    public String toString() {
        return "Room: " + name + "Owner: " + owner;
    }

}
