
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
            result = checkSpam();  
            System.out.println(">> Your confession will be posted in " + time + " minutes.");
        }else if(queue.getSize() <= 10){
            time = 10;
            result = checkSpam();
            System.out.println(">> Your confession will be posted in " + time + " minutes.");
        }else if(queue.getSize() > 10){
            time = 5;
            result = checkSpam();
            System.out.println(">> Your confession will be posted in " + time + " minutes.");
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
        System.out.println(">> Your submission has failed.");
        System.out.println(">> We detected that your confession might be a spam/unappropriate content.");
        System.out.println("============================================================"); // 60 = signs
    }
    
    public boolean checkRepetition(String content){
        String[] temp;
        String[] temp2;
        int index = 0;
        temp = content.split(" ");
        for (String pertemp : temp){
            for(int i = 0; i < queue.getSize(); i++){
                temp2 = queue.getQueue(i).split(" ");
                for(String pertemp2 : temp2){
                    if(pertemp.equalsIgnoreCase(pertemp2)){
                        index++;
                    }
                }
                temp2 = null;
            }
        }
        
        double result = ((double)index / (double)temp.length) * 100.00;
        
        return result <= 50.0;
    }
    
    public static void main(String[] args) {
        ContentQueue queue = new ContentQueue();
        
        String test = "Hi, my name is adam. What is your name bruh!";
        String test2 = "arghh! awat aq xleh post";
        System.out.println(queue.checkRepetition(test));
        System.out.println(queue.checkRepetition(test2));
        
    }
}
