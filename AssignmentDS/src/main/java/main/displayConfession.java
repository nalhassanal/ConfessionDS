package main;
import SQL.SQLconnect;
import SQL.SQLutil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class displayConfession {
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
     *
     */
    public displayConfession(){
        SQLconnect connector = new SQLconnect();
        con = connector.connector();
        util = new SQLutil();
        input = new Scanner(System.in);
    }

    /**
     * @param startID
     */
    public void start(String startID){
        if(con == null){
            System.out.println("Could not connect to database");
            System.out.println("Please check connection");
            return;
        }
        if (checkLatest(startID)){
            System.out.println("Reached limit for confessions posts");
            return;
        }
        if (Integer.parseInt(startID.substring(2)) < 1){
            System.out.println("You have reached the start of the list");
            return;
        }
        String replyTo = getOriginID(startID);
        boolean hasReply = true;
        if (replyTo == null) {
            hasReply = false;
        }
        print(startID, hasReply);
        String choice;
        do {
            displayOptions(startID, hasReply);
            choice = input.nextLine();
            switch (choice.toLowerCase()){
                case "d":
                    start(increment(startID));
                    choice = "q";
                    // travel list by recursion
                    break;
                case "a":
                    start(decrement(startID));
                    choice = "q";
                    // travel list by recursion
                    break;
                case "s":
                    if (checkReply(startID)){
                        processReplies(getReplyID(startID));
                        choice = "q";
                    }
                    else {
                        System.out.println("post has no posts replying to it");
                    }
                    break;
                case "w":
                    start(replyTo);
                    choice = "q";
                    // this condition macam tekan exit dua kali lah
                    // but tak bagi option nak patah balik ke conf asal
                    break;
            }
        } while (!choice.equalsIgnoreCase("q"));
    }

    /**
     * @param id
     */
    public void processReplies(List<String> id){
        for (String element: id){
            if (element == null)
                return;
            else
                start(element);
        }
    }

    /**
     * @param id
     * @return
     */
    public String decrement(String id){
        int num = Integer.parseInt(id.substring(2));
        return String.format("DS%05d", num -1);
    }

    /**
     * @param id
     * @return
     */
    public String increment(String id){
        int num = Integer.parseInt(id.substring(2));
        return String.format("DS%05d", num + 1);
    }

    /**
     * @param ID
     * @param reply
     */
    public void print(String ID, boolean reply){
        ArrayList<confessionPair> ls = util.readFromTable(con);
        ArrayList<String> keys = new ArrayList<>();
        for (confessionPair element: ls){
            keys.add(element.getId());
        }
        int index = keys.indexOf(ID);
        confessionPair pair = ls.get(index);
        System.out.println("#"+pair.getId());
        System.out.println("["+pair.getDate()+"]\n");
        if (reply)
            System.out.println("Reply to #"+ getOriginID(ID));
        System.out.println(pair.getContent());
    }

    /**
     * @param id
     * @return
     */
    public boolean checkOrigin(String id){
        boolean success = false;
        int num = Integer.parseInt(id.substring(2)), retID = -1;
        String query = "select * from reply";
        PreparedStatement ps;
        ResultSet rs;
        try {
            ps = con.prepareStatement(query);
            rs = ps.executeQuery();
            while (rs.next()){
                if (rs.getInt("reply") == num){
                    retID = rs.getInt("main");
                    success = true;
                }
            }
            ps.close();
            rs.close();
        } catch (SQLException ex){ex.printStackTrace();}
        return success;
    }

    /**
     * @param startID
     * @return
     */
    public String getOriginID(String startID){
        if (!checkOrigin(startID))
            return null;
        int id = Integer.parseInt(startID.substring(2)), retID = -1;
        String query = "select * from reply";
        PreparedStatement ps;
        ResultSet rs;
        try {
            ps = con.prepareStatement(query);
            rs = ps.executeQuery();
            while (rs.next()){
                if (rs.getInt("reply") == id){
                    retID = rs.getInt("main");
                }
            }
            ps.close();
            rs.close();
        } catch (SQLException ex){ex.printStackTrace();}
        if (retID == -1)
            return null;
        return String.format("DS%05d", retID);
    }

    /**
     * @param id
     * @return
     */
    public boolean checkReply(String id){
        boolean success = false;
        int num = Integer.parseInt(id.substring(2)), retID = -1;
        String query = "select * from reply";
        PreparedStatement ps;
        ResultSet rs;
        try {
            ps = con.prepareStatement(query);
            rs = ps.executeQuery();
            while (rs.next()){
                if (rs.getInt("main") == num) {
                    retID = rs.getInt("reply");
                    success = true;
                }
            }
            ps.close();
            rs.close();
        } catch (SQLException ex){ex.printStackTrace();}
        return success;
    }

    /**
     * @param id
     * @return
     */
    public ArrayList<String> getReplyID(String id){
        if (!checkReply(id))
            return null;
        int num = Integer.parseInt(id.substring(2));
        String query = "select * from reply";
        ArrayList<Integer> retID = new ArrayList<>();
        PreparedStatement ps;
        ResultSet rs;
        try {
            ps = con.prepareStatement(query);
            rs = ps.executeQuery();
            while (rs.next()){
                if (rs.getInt("main") == num) {
                    retID.add(rs.getInt("reply"));
                }
            }
            ps.close();
            rs.close();
        } catch (SQLException ex){ex.printStackTrace();}

        if (retID.isEmpty())
            return null;

        ArrayList<String> IDs = new ArrayList<>();
        for(int element: retID){
            IDs.add(String.format("DS%05d", element));
        }
        return IDs;
    }

    /**
     * @param id
     * @return
     */
    public boolean checkLatest(String id){
        int latestIDNum = util.getID(con);
        int idNum = Integer.parseInt(id.substring(2));
        return idNum > latestIDNum;
    }

    /**
     * @param startID
     * @param hasReply
     */
    public void displayOptions(String startID, boolean hasReply){
        String reply = getOriginID(startID);// maybe method to check for replies from startID
        System.out.println("============================================================"); // 60 = signs
        System.out.println(">> Options:");
        System.out.println(">> \"D\" - view next post");
        System.out.println(">> \"A\" - view previous post");
        if (hasReply)
            System.out.printf(">> \"W\" - view #%s post\n", reply);
        System.out.println(">> \"S\" - view posts that are replying to this post");
        System.out.println(">> \"Q\" - quit viewing post");
        System.out.println("------------------------------------------------------------"); // 60 - signs
    }

    /**
     *
     */
    public void displayIDAvailable(){
        ArrayList<confessionPair> ls = util.readFromTable(con);
        ArrayList<String> keys = new ArrayList<>();
        for (confessionPair element: ls){
            keys.add(element.getId());
        }

        for (int i = 0; i <keys.size(); i++){
            if ( i % 5 == 0) {
                System.out.println();
            }
            System.out.print(keys.get(i) + "\t\t");
        }
    }

    /**
     * @param ID
     * @return
     */
    public boolean hasID(String ID){
        ArrayList<confessionPair> ls = util.readFromTable(con);
        ArrayList<String> keys = new ArrayList<>();
        for (confessionPair element: ls){
            keys.add(element.getId());
        }
        return keys.contains(ID);
    }
}
