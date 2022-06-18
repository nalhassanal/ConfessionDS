
package main;

import SQL.SQLconnect;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import static spam.spamFilter.predictSpam;

public class ContentQueue {
    MyQueue<String> queue = new MyQueue<>();
    private Connection con;
    Timer timer = new Timer();
    int seconds = 0;
    int time = 0;
    
    public ContentQueue(){
        con = connect();
    }
    
    private Connection connect(){
        SQLconnect sql = new SQLconnect();
        return sql.connector();
    }
    
    public void main(){
        
    }
    
    TimerTask task = new TimerTask(){
        public void run(){
            seconds++;
            if(seconds > time){
                stop(); 
            }
        }
    };
    
    public void start(){
        timer.scheduleAtFixedRate(task,1000,1000);
    }
    
    public void stop(){
        timer.cancel();
    }
    
    public boolean mainStep(confessionPair content){
        addQueueSQL(content);
        addToQueue();
        boolean result = false;
        if(queue.getSize() <= 5){
            time = 15;
            start();
            result = checkSpam();   
        }else if(queue.getSize() <= 10){
            time = 10;
            start();
            result = checkSpam();
        }else if(queue.getSize() > 10){
            time = 5;
            start();
            result = checkSpam();
        }
        return result;
    }
    
    public boolean addQueueSQL(confessionPair content){
        boolean success = false;
        PreparedStatement ps;
        String query;

            query = "insert into QueueTable (content, time, ID) values (?,?,?)";
        try {
            ps = con.prepareStatement(query);
            ps.setString(1, content.getContent());
            ps.setString(2, content.getDate());
            ps.setString(3, content.getId());
            success = !ps.execute();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return success;
    }
    
    public void addToQueue(){
        String query = "select * from QueueTable";
        PreparedStatement ps;
        ResultSet rs;
        try{
            ps = con.prepareStatement(query);
            rs = ps.executeQuery();
            while (rs.next()){
                queue.enqueue(rs.getString("content"));
//                rs.deleteRow();
            }
        } catch (SQLException ex){
            ex.printStackTrace();
        }
    }
       
    public boolean checkSpam(){
        boolean spam = predictSpam(queue.peek().split(" ") , false);
        return spam;
    }
    
    public void successfulCheckDisplay(confessionPair content){
        System.out.println("============================================================"); // 60 = signs
        System.out.println("Thank you! We have review your confession.");
        System.out.println(">> Submitted at " + content.getDate() + ".");
        System.out.println(">> Confession post ID: " + content.getId() + ".");
        System.out.println(">> Your confession have been approved and will be published soon.");
        System.out.println("============================================================"); // 60 = signs
    }
    
    public void unSuccessfulPostDisplay(){
        System.out.println("============================================================"); // 60 = signs
        System.out.println(">> Your submission has failed");
        System.out.println("============================================================"); // 60 = signs
    }
}
