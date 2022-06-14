package main;
import java.text.SimpleDateFormat;
import java.util.Date;
public class confessionPair implements Comparable<confessionPair> {
    private String id;
    private String content;
    private String date;
    private Date currentDate;

    public confessionPair(){
        id = null;
        content = null;
        date = null;
        currentDate = null;
    }

    public confessionPair(String content){
        this.content = content;
        currentDate = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd h:mm a");
        date = dateFormat.format(currentDate);
    }

    public confessionPair(String id, String content){
        this.id = id;
        this.content = content;
        currentDate = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd h:mm a");
        date = dateFormat.format(currentDate);
    }

    public Date getCurrentDate(){return currentDate;}

    public void setCurrentDate(Date date){
        this.currentDate = date;
    }

    public void setDate(Date date){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd h:mm a");
        this.date = dateFormat.format(date);
    }

    public String getDate(){return date;}

    public void setDate(String date) {
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        int idNum = Integer.parseInt(id.substring(2));
        return String.format("#DS%05d \n[%s]\n\n%s", idNum, date, content);
    }

    @Override
    public int compareTo(confessionPair o) {
        if (convertId(id) > convertId(o.id))
            return 1;
        else if (convertId(id) < convertId(o.id))
            return -1;
        return 0;
    }

    private int convertId (String id){
        String convId = id.substring(2);
        return Integer.parseInt(convId);
    }
}
