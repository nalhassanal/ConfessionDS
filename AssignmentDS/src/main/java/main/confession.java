package main;

import SQL.SQLconnect;
import SQL.SQLutil;
import fileUtil.*;

import java.sql.*;
import java.util.*;
import java.util.Date;

public class confession {
    private final FileUtil fileUtil = new FileUtil();
    private final Scanner input = new Scanner(System.in);
    private final SQLutil util = new SQLutil();
    private final ContentQueue queue = new ContentQueue();
    private Connection con;

    public confession(){
        con = connect();
    }

    private Connection connect(){
        SQLconnect sql = new SQLconnect();
        return sql.connector();
    }

    public void mainDisplay(){
        System.out.println("============================================================"); // 60 = signs
        System.out.println(">> Please enter the confession post ID that you want to reply.");
        System.out.println(">> Leave it blank if you don't want to reply to a confession post.");
        System.out.println("------------------------------------------------------------"); // 60 - signs
        System.out.print("Reply confession post ID: ");
        String replyID = input.nextLine();
        if (replyID.isBlank()) {
            // method queue
            createConfession();
        }
        else{
            if (checkForKey(replyID)) {
                System.out.println("------------------------------------------------------------"); // 60 - signs
                System.out.println(">> Confession post ID exists!");
                System.out.println("============================================================"); // 60 = signs
                createReply(replyID);
            }
        }
    }

    public void createConfession(){
        if (con == null)
            return;

        confessDisplay();
        StringBuilder confessionContent = new StringBuilder();
        String confessionID;
        while (true){
            String insert = input.nextLine();
            if (insert.equals("-1")){
                break;
            }
            confessionContent.append(insert).append("\n");
        }
        System.out.println("============================================================"); // 60 = signs
        int id = util.getID(con) + 1;
        confessionID = String.format("DS%05d", id);
        confessionPair confess = new confessionPair(confessionID, confessionContent.toString());
        successfulPostDisplay(confess);
        if(!queue.mainStep(confess)){
            queue.successfulCheckDisplay(confess);
            addContent(confess);
        }else
            unSuccessfulPostDisplay();
//        if (addContent(confess)){   // sini akan ganti dengan addToQueue
//            successfulPostDisplay(confess);
//        }else
//            unSuccessfulPostDisplay();
    }

    public void createReply(String rootID){
        if (con == null)
            return;
        replyDisplay();
        String confessionID;
        StringBuilder confessionContent = new StringBuilder();
        while (true){
            String insert = input.nextLine();
            if (insert.equals("-1")){
                break;
            }
            confessionContent.append(insert).append("\n");
        }
        System.out.println("============================================================"); // 60 = signs
        int id = util.getID(con) + 1;
        confessionID = String.format("DS%05d", id);
        confessionPair confess = new confessionPair(confessionID, confessionContent.toString());
        if (addContent(confess) && addReply(rootID, confessionID))
            successfulReplyPostDisplay(confess);
        else
            unSuccessfulPostDisplay();

    }

    public boolean addReply(String rootID, String replyID){
        boolean success = false;
        int main = Integer.parseInt(rootID.substring(2)),
                reply = Integer.parseInt(replyID.substring(2));

        String query = "insert into reply (main, reply) values (?,?)";
        PreparedStatement ps;
        try{
            ps = con.prepareStatement(query);
            ps.setInt(1, main);
            ps.setInt(2, reply);
            success = !ps.execute();
        } catch (SQLException ex){
            ex.printStackTrace();
        }
        return success;
    }

    public boolean addContent(confessionPair pair){
        boolean success = false;
        Date date = pair.getCurrentDate();
        Timestamp ts = new Timestamp(date.getTime());
        PreparedStatement ps;
        String query;

        query = "insert into confession (content, timestamp) values (?,?)";
        try {
            ps = con.prepareStatement(query);
            ps.setString(1, pair.getContent());
            ps.setTimestamp(2, ts);
            success = !ps.execute();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return success;
    }

    public boolean checkForKey(String ID){
        return getExistingID(Integer.parseInt(ID.substring(2))); // will remove DS from input string
    }

    public boolean getExistingID(int toBeChecked){
        String query = "select * from confession";
        PreparedStatement ps;
        ResultSet rs;
        ArrayList<Integer> id = new ArrayList<>();
        try{
            ps = con.prepareStatement(query);
            rs = ps.executeQuery();
            while (rs.next()){
                id.add(rs.getInt("ID"));
            }
        } catch (SQLException ex){
            ex.printStackTrace();
        }
        return id.contains(toBeChecked);
    }

    public void confessDisplay(){
        System.out.println("\n============================================================"); // 60 = signs
        System.out.println(">> Please enter you confession content.");
        System.out.println(">> Insert \"-1\" to submit your confession.");
        System.out.println("------------------------------------------------------------"); // 60 - signs
        System.out.println("Confession content:");
    }

    public void replyDisplay(){
        System.out.println("\n============================================================"); // 60 = signs
        System.out.println(">> Please enter your reply confession content.");
        System.out.println(">> Insert \"-1\" to submit your reply confession.");
        System.out.println("------------------------------------------------------------"); // 60 - signs
        System.out.println("Reply confession content:");
    }

    public void unSuccessfulPostDisplay(){
        System.out.println("============================================================"); // 60 = signs
        System.out.println(">> Your submission has failed");
        System.out.println("============================================================"); // 60 = signs
    }

    public void successfulPostDisplay(confessionPair content){
        System.out.println("============================================================"); // 60 = signs
        System.out.println(">> Submitted at "+content.getDate() + ".");
        System.out.println(">> Confession post ID: " + content.getId() + ".");
        System.out.println(">> Your confession will be reviewed soon.");
        System.out.println("============================================================"); // 60 = signs
    }

    public void successfulReplyPostDisplay(confessionPair content){
        System.out.println("============================================================"); // 60 = signs
        System.out.println(">> Submitted at " + content.getDate() + ".");
        System.out.println(">> Reply confession post ID: " + content.getId() + ".");
        System.out.println(">> Your confession will be reviewed soon.");
        System.out.println("============================================================"); // 60 = signs
    }
}
