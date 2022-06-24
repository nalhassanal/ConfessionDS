package main;

import SQL.SQLconnect;
import SQL.SQLutil;

import java.sql.*;
import java.util.*;
import java.util.Date;

public class confession {
    /**
     * Scanner object to allow user input to be read
     */
    private final Scanner input;
    /**
     * SQLutil class object to allow access to SQL utilities
     */
    private final SQLutil util;
    /**
     * Connection object to allow connection to the mysql database
     */
    private final Connection con;
    /**
     * ContentQueue object to allow manipulation of queue
     */
    private final ContentQueue queue;

    /**
     * the constructor method that initializes the class variables
     */
    public confession(){
        SQLconnect sql = new SQLconnect();
        con = sql.connector();
        input = new Scanner(System.in);
        util = new SQLutil();
        queue = new ContentQueue();
    }

    /**
     * the starter method of the confession process
     */
    public void mainDisplay(){
        if(con == null){
            System.out.println("Could not connect to database");
            System.out.println("Please check connection");
            return;
        }
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

    /**
     * the driver method that allows a user to post a confession
     */
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
        if(!queue.resultSpam(queue.mainStep(confess))){
            queue.successfulCheckDisplay(confess);
            queue.successfulReviewed(confess);
        }else
            queue.unSuccessfulPostDisplay();
    }

    /**
     * the driver method that allows a user to reply to another post
     * @param rootID gets the replied to post ID
     */
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
        successfulReplyPostDisplay(confess);
        if(!queue.resultSpam(queue.mainStep(confess))){
            queue.successfulCheckDisplay(confess);
            queue.successfulReviewedReply(confess, rootID, confessionID);
        }else
            queue.unSuccessfulPostDisplay();

    }

    /**
     * the method that will add the confession into the reply table in the database
     * @param rootID the replied ID
     * @param replyID the new ID
     * @return true if the process is successful, false otherwise
     */
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
            ps.close();
        } catch (SQLException ex){
            ex.printStackTrace();
        }
        return success;
    }

    /**
     * the method that will add the confession into the confession table in the database
     * @param pair the confession itself
     * @return true if the process is successful, false otherwise
     */
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
            ps.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return success;
    }

    /**
     * checks if the input ID exists
     * @param ID input ID
     * @return true if the ID contains in the database, false otherwise
     */
    public boolean checkForKey(String ID){
        return getExistingID(Integer.parseInt(ID.substring(2))); // will remove DS from input string
    }

    /**
     * checks if the input ID exists within the database or not
     * @param toBeChecked the input ID
     * @return true if the ID contains in the database, false otherwise
     */
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
            ps.close();
            rs.close();
        } catch (SQLException ex){
            ex.printStackTrace();
        }
        return id.contains(toBeChecked);
    }

    /**
     * a method that displays the main option for a post
     */
    public void confessDisplay(){
        System.out.println("\n============================================================"); // 60 = signs
        System.out.println(">> Please enter you confession content.");
        System.out.println(">> Insert \"-1\" to submit your confession.");
        System.out.println("------------------------------------------------------------"); // 60 - signs
        System.out.println("Confession content:");
    }

    /**
     * a method that displays the main option for a reply post
     */
    public void replyDisplay(){
        System.out.println("\n============================================================"); // 60 = signs
        System.out.println(">> Please enter your reply confession content.");
        System.out.println(">> Insert \"-1\" to submit your reply confession.");
        System.out.println("------------------------------------------------------------"); // 60 - signs
        System.out.println("Reply confession content:");
    }

    /**
     * a method that will display the failure of posting a confession
     */
    public void unSuccessfulPostDisplay(){
        System.out.println("============================================================"); // 60 = signs
        System.out.println(">> Your submission has failed");
        System.out.println("============================================================"); // 60 = signs
    }

    /**
     * the display method of a successful post which will display its time of creation and its ID
     * @param content the post itself
     */
    public void successfulPostDisplay(confessionPair content){
        String date = content.getDate();
        String idreply = content.getId();
        System.out.println("============================================================"); // 60 = signs
        System.out.println(">> Submitted at "+content.getDate() + ".");
        System.out.println(">> Confession post ID: " + content.getId() + ".");
        System.out.println(">> Please wait for your confession to be reviewed.");
        System.out.println("============================================================"); // 60 = signs
    }

    /**
     * the display method of a successful reply post which will display its time of creation and its ID
     * @param content the post itself
     */
    public void successfulReplyPostDisplay(confessionPair content){
        System.out.println("============================================================"); // 60 = signs
        System.out.println(">> Submitted at " + content.getDate() + ".");
        System.out.println(">> Reply confession post ID: " + content.getId() + ".");
        System.out.println(">> Please wait for your reply to be reviewed.");
        System.out.println("============================================================"); // 60 = signs
    }
}