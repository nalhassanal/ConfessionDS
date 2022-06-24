package main;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * the container class for a confession pair object
 */
public class confessionPair implements Comparable<confessionPair> {
    /**
     * the unique ID that is given to a confession at runtime
     */
    private String id;
    /**
     * the content that is given to a confession based on user input
     */
    private String content;
    /**
     * the formatted date that is created at runtime
     */
    private String date;
    /**
     * the date created at runtime
     */
    private Date currentDate;

    /**
     * a default constructor that initializes the variables to null
     */
    public confessionPair(){
        id = null;
        content = null;
        date = null;
        currentDate = null;
    }

    /**
     * a constructor that will initialize the class variables
     * a date object is also created alongside the initialization process
     * @param id the unique post ID
     * @param content the post confession content
     */
    public confessionPair(String id, String content){
        this.id = id;
        this.content = content;
        currentDate = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd h:mm a");
        date = dateFormat.format(currentDate);
    }

    /**
     * a date object getter method
     * @return the current date object
     */
    public Date getCurrentDate(){return currentDate;}

    /**
     * a date object setter method
     * @param date the new data to replace the old one
     */
    public void setCurrentDate(Date date){
        this.currentDate = date;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd h:mm a");
        this.date = dateFormat.format(date);
    }

    /**
     * a getter for the formatted date string
     * @return the date string
     */
    public String getDate(){return date;}

    /**
     * a setter method for the formatted date string
     * @param date the new data to replace the old onen
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     * the getter method for the confession ID
     * @return the confession ID
     */
    public String getId() {
        return id;
    }

    /**
     * the setter method for the confession ID
     * @param id the new data to replace the old one
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * the getter method for the confession content
     * @return the confession content
     */
    public String getContent() {
        return content;
    }

    /**
     * the setter method for the confession content
     * @param content the new data to replace the old one
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * the method to return a string representation of the class
     * @return the string representation
     */
    @Override
    public String toString() {
        int idNum = Integer.parseInt(id.substring(2));
        return String.format("DS%05d \n[%s]\n\n%s", idNum, date, content);
    }

    /**
     * a comparator object to compare between confessionPair objects
     * @param o the object to be compared.
     * @return returns 1 if its id is more than the parameter, -1 if it is less, 0 if it is equal
     */
    @Override
    public int compareTo(confessionPair o) {
        if (convertId(id) > convertId(o.id))
            return 1;
        else if (convertId(id) < convertId(o.id))
            return -1;
        return 0;
    }

    /**
     * the equals method that will compare between two confession pair object's content
     * @param o the object to be compared
     * @return true if their content is equal, false otherwise
     */
    public boolean equals(confessionPair o){
        return (getContent().equals(o.getContent()));
    }

    /**
     * a method to convert the string ID to get the numbers and leave the "DS" identifier
     * @param id the input ID
     * @return int representation of the ID
     */
    private int convertId (String id){
        String convId = id.substring(2);
        return Integer.parseInt(convId);
    }
}
