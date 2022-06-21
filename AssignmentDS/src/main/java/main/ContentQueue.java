
package main;

import SQL.SQLutil;
import SQL.SQLconnect;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.Timer;
import java.util.TimerTask;
import static spam.spamFilter.predictSpam;

public class ContentQueue {
    resultPair pair = new resultPair();
    ArrayList list = new ArrayList();
    MyQueue<String> queue = new MyQueue<>();
    SQLutil util = new SQLutil();
    SQLconnect Conn = new SQLconnect();
    private Connection con = Conn.connector();
    Timer timer = new Timer();
    int seconds = 0;
    int time = 0;
    
    class resultPair{
        boolean resultSpam;
        int time;

        public resultPair() {
        }
        
        public boolean isResultSpam() {
            return resultSpam;
        }

        public void setResultSpam(boolean resultSpam) {
            this.resultSpam = resultSpam;
        }

        public int getTime() {
            return time;
        }

        public void setTime(int time) {
            this.time = time;
        }
        
    }
    
    private Connection connect(){
        SQLconnect sql = new SQLconnect();
        return sql.connector();
    }
    
    public void delete(String content){
        LinkedList<String> contents = util.getQueueContents(con);
        LinkedList<Integer> contentID = util.getQueueID(con);        
        int index = contents.indexOf(content);
        int toBeRemoved = contentID.get(index);
    }
    
    public void runTask(confessionPair content, int time){
        TimerTask task = new TimerTask(){
        public void run(){
            seconds++;
            if(seconds > time){
                stop(); 
                confession confess = new confession();
                confess.addContent(content);
                delete(queue.getQueue(queue.getSize()-1));
                }
            }
        };
       timer.scheduleAtFixedRate(task,1000,1000); 
    }
    
    public void runTaskReply(confessionPair content, int time, String rootID, String confessionID){
        TimerTask task = new TimerTask(){
        public void run(){
            seconds++;
            if(seconds > time){
                stop(); 
                confession confess = new confession();
                confess.addContent(content);
                confess.addReply(rootID, confessionID);
                }
            }
        };
       timer.scheduleAtFixedRate(task,1000,1000); 
    }
    
    public void stop(){
        timer.cancel();
    }
    
    public void successfulReviewed(confessionPair content){
        int time = pair.getTime();
        System.out.println(">> Your confession will be posted in " + time + " minutes.");
        runTask(content, time);
    }
    
    public void successfulReviewedReply(confessionPair content, String rootID, String confessionID){
        int time = pair.getTime();
        System.out.println(">> Your confession will be posted in " + time + " minutes.");
        runTaskReply(content, time, rootID, confessionID);
    }
    
    public boolean resultSpam(resultPair pair){
        if(pair.isResultSpam())
            return true;
        else
            return false;
    }
    
    public resultPair getPair(){
        return this.pair;
    }
    
    public void setPair(resultPair pair){
        this.pair = pair;
    }
    
    public resultPair mainStep(confessionPair content){
        resultPair pair = new resultPair();
        addQueueSQL(content);
        addToQueue();
        boolean result = true;
        System.out.println(queue.getSize());
        if(queue.getSize() <= 5){
            time = 15;
//            result = checkSpam(); 
            result = false;
            pair.setResultSpam(result);
            pair.setTime(time);
        }else if(queue.getSize() <= 10){
            time = 10;
//            result = checkSpam();
            result = false;
            pair.setResultSpam(result);
            pair.setTime(time);
        }else if(queue.getSize() > 10){
            time = 5;
//            result = checkSpam();
            result = false;
            pair.setResultSpam(result);
            pair.setTime(time);
        }
        System.out.println(pair.isResultSpam());
        System.out.println(pair.getTime());
        this.setPair(pair);
        return pair;
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
            }
        } catch (SQLException ex){
            ex.printStackTrace();
        }
    }
       
    public boolean checkSpam(){
        boolean spam = predictSpam(queue.peek().split(" ") , true);
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
        System.out.println();
        
    }
    
//    public void delete() {
//        String sql = "DELETE FROM QueueTable WHERE ID = ?";
//
//        try (Connection conn = this.connect();
//                PreparedStatement pstmt = conn.prepareStatement(sql)) {
//            String id = getID(conn);
//            // set the corresponding param
//            pstmt.setInt(1, id);
//            // execute the delete statement
//            pstmt.executeUpdate();
//
//        } catch (SQLException e) {
//            System.out.println(e.getMessage());
//        }
//    }
    
    public String getID(Connection con){
        // this method gets the latest id
        if (con.equals(null))
            return "The queue is empty.";
        String query = "select * from QueueTable";
        PreparedStatement ps;
        ResultSet rs;
        String id = "";
        try{
            ps = con.prepareStatement(query);
            rs = ps.executeQuery();
            while (rs.next()){
                id = rs.getString("ID");
            }
        } catch (SQLException ex){
            ex.printStackTrace();
        }
        return id;
    }
}
