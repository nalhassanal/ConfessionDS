package admin;

import SQL.SQLconnect;
import SQL.SQLutil;
import main.displayConfession;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;

public class admin {

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
     * admin class constructor that initialize the private variables
     */
    public admin(){
        SQLconnect connect = new SQLconnect();
        con = connect.connector();
        input = new Scanner(System.in);
        util = new SQLutil();
    }

    /**
     * the starter method that is the main method that accesses admin class
     */
    public void start(){
        String choice;
        do {
            adminDisplay();
            System.out.println("------------------------------------------------------------"); // 60 - signs
            System.out.print(">> ");
            choice = input.nextLine();
            switch (choice.toLowerCase()) {
                case "w":
                    viewPosts();
                    break;

                case "d":
                    deletePosts();
                    break;

                case "x":
                    break;

                default:
                    System.out.println("Wrong input");
                    System.out.println("------------------------------------------------------------"); // 60 - signs
                    System.out.println();
                    start();
                    choice = "x";
            }
        } while (!choice.equalsIgnoreCase("x"));

    }

    /**
     * view posts method that allows admin to see the available posts
     */
    public void viewPosts(){
        displayConfession disp = new displayConfession();
        System.out.println("============================================================");
        System.out.println("Available Confession ID :");
        disp.displayIDAvailable();
        System.out.println("\n============================================================"); // 60 = signs
        System.out.println(">> Enter the post ID that you want to see");
        String startingID = input.nextLine();
        if(disp.hasID(startingID)){
            disp.start(startingID);
        }
    }

    /**
     * the starter method that allows admin to delete posts
     * asks admin for the post that they want to delete
     * redirects to successful or unsuccessful displays after deletion is completed
     */
    public void deletePosts(){
        String query;
        System.out.println("\n============================================================"); // 60 = signs
        System.out.println(">> Enter the ID that you want to remove");
        query = input.nextLine();

        deleteStatusDisplay(delete(query));
    }

    /**
     * checks for neighbours of selected post ID and gets all neighbours related to the neighbours
     * @param selected the selected post ID that is to be deleted
     * @return true if the manipulation of the mysql database is successful, false otherwise
     */
    public boolean delete(String selected) {
        boolean result, success;

        if (!checkReply(selected)) {
            System.out.println("no neighbours");
            result = deleteRow(selected);
        } else {
            ArrayList<replies> ls = getNeighbour(selected);
            LinkedList<replies> related = new LinkedList<>();
            for (replies each : ls) {
                String reply = each.getReply();
                if (checkReply(reply)) {
                    ArrayList<replies> next = getNeighbour(reply);
                    related.addAll(next);
                }
            }
            if (related.isEmpty()) {
                // delete things in ls
                success = deleteRoots(ls);
            } else {
                // delete things in related && ls
                success = deleteRoots(related) && deleteRoots(ls);
            }
            result = success;
        }

        return result;
    }

    /**
     * reads the reply table from database to find replies to the source(root) ID
     * @param source root of the neighbours
     * @return an array list of replies class object that contains reply data
     */
    public ArrayList<replies> getNeighbour(String source){
        ArrayList<replies> replies = util.readReply(con);
        ArrayList<replies> ls = new ArrayList<>();
        replies rep = new replies();
        for (replies reply: replies){
            if (reply.getRoot().equals(source)){
                rep.setRoot(source);
                rep.setReply(reply.getReply());
                ls.add(rep);
                rep = new replies();
            }
        }
        return ls;
    }

    /**
     * checks the parameter with the reply table to check and see if it has any replies
     * @param idToBeChecked the confession ID to be checked
     * @return true if it has any reply, false otherwise
     */
    private boolean checkReply(String idToBeChecked){
        ArrayList<replies> replies = util.readReply(con);
        for (replies ele : replies){
            if (ele.getRoot().equals(idToBeChecked)){
                return true;
            }
        }
        return false;
    }

    /**
     * deletes the elements in the list from the reply table and from the confession table in the database
     * @param ls the arrayList that contains the replies
     * @return true if all the elements in the ls is deleted from all related tables
     */
    public boolean deleteRoots(ArrayList<replies> ls){
        boolean result = false;
        for(replies reply: ls){
            result = deleteRow(reply.getRoot()) && deleteRow(reply.getReply())
                    && deleteReplyRow(reply.getRoot());
        }
        return result;
    }

    /**
     * deletes the elements in the list from the reply table and from the confession table in the database
     * @param ls the singly linked list that contains the replies to the replies
     * @return true if all the elements in the ls is deleted from all related tables
     */
    public boolean deleteRoots(LinkedList<replies> ls){
        boolean result = false;
        for(replies reply: ls){
            result = deleteRow(reply.getRoot()) && deleteRow(reply.getReply())
                    && deleteReplyRow(reply.getRoot());
        }
        return result;
    }

    /**
     * deletes the parameter from the reply table in the database
     * @param selected the confession ID to be deleted
     * @return true if the database is changed, false otherwise
     */
    private boolean deleteReplyRow(String selected){
        int id = Integer.parseInt(selected.substring(2));
        String updateQuery = "delete from reply where main = " + id;
        int result = 0;
        boolean success = false;
        Statement stmt;
        try{
            stmt = con.createStatement();
            result = stmt.executeUpdate(updateQuery);
            stmt.close();
        } catch (SQLException ex){
            ex.printStackTrace();
        }
        if (result == 1)
            success = true;
        return success;
    }

    /**
     * deletes the parameter from the confession table in the database
     * @param selected the confession ID to be deleted
     * @return true if the database is changed, false otherwise
     */
    private boolean deleteRow(String selected){
        int id = Integer.parseInt(selected.substring(2));
        boolean last = true, success = false;

        String updateQuery = "delete from confession where id = " + id;
        if (id < util.getID(con))
            last = false;

        int result = 0;

        Statement stmt;
        try{
            stmt = con.createStatement();
            result = stmt.executeUpdate(updateQuery);
            if (last) {
                String updateCounter = "ALTER TABLE confession AUTO_INCREMENT = " + id;
                result = stmt.executeUpdate(updateCounter);
            }
            stmt.close();
        }catch (SQLException ex){
            ex.printStackTrace();
        }

        if (result == 1)
            success = true;
        return success;
    }

    /**
     * the display method that shows all the admin options
     */
    public void adminDisplay(){
        System.out.println("\n============================================================"); // 60 = signs
        System.out.println("DS CONFESSION");
        System.out.println("Admin Panel");
        System.out.println("\n============================================================"); // 60 = signs
        System.out.println(">> \"W\" - View all posts");
        System.out.println(">> \"D\" - Delete posts");
        System.out.println(">> \"X\" - Exit");
    }

    /**
     * the status display method
     * @param success the parameter that controls the text to be displayed
     */
    public void deleteStatusDisplay(boolean success){
        System.out.println("------------------------------------------------------------"); // 60 - signs
        if(success)
            System.out.println(">> Deletion successful");
        else
            System.out.println(">> Deletion failed");
        System.out.println();
    }
}
