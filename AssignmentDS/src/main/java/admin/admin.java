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

    public static class User {

        public User() {
        }
    }

    private class resultPair{
        boolean res;
        ArrayList<Integer> replies;

        public resultPair(){
            res = false;
            replies = new ArrayList<>();
        }

        public boolean isRes() {
            return res;
        }

        public void setRes(boolean res) {
            this.res = res;
        }

        public ArrayList<Integer> getReplies() {
            return replies;
        }

        public void setReplies(ArrayList<Integer> replies) {
            this.replies = replies;
        }
    }

    private final Scanner input = new Scanner(System.in);
    private final SQLutil util = new SQLutil();
    private Connection con;
    public admin(){
        SQLconnect connect = new SQLconnect();
        con = connect.connector();
    }

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

    public void deletePosts(){
        String query;
        System.out.println("\n============================================================"); // 60 = signs
        System.out.println(">> Enter the ID that you want to remove");
        query = input.nextLine();

        if(delete(query))
            deleteSuccessfulDisplay();
        else
            deleteNOTsuccessfuldisplay();
    }

    public boolean delete(String selected){
        boolean success = false;
        if(!checkReply(selected)){
            System.out.println("no neighbours");
            return deleteRow(selected);
        }
        ArrayList<replies> ls = getNeighbour(selected);
        LinkedList<replies> related = new LinkedList<>();
//        System.out.println(ls);
        for(replies each: ls){
            String reply = each.getReply();
            if(checkReply(reply)){
                ArrayList<replies> next = getNeighbour(reply);
                related.addAll(next);
            }
        }
//        System.out.println(related);

        if(related.isEmpty()){
            // delete things in ls
            success = deleteRoots(ls);
        }
        else {
            // delete things in related && ls
            success = deleteRoots(related) && deleteRoots(ls);
        }

        return success;
    }

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

    private boolean checkReply(String root){
        ArrayList<replies> replies = util.readReply(con);
        for (replies ele : replies){
            if (ele.getRoot().equals(root)){
                return true;
            }
        }
        return false;
    }

    public boolean deleteRoots(ArrayList<replies> ls){
        boolean result = false;
        for(replies reply: ls){
            result = deleteRow(reply.getRoot()) && deleteRow(reply.getReply())
                    && deleteReplyRow(reply.getRoot());
        }
        return result;
    }

    public boolean deleteRoots(LinkedList<replies> ls){
        boolean result = false;
        for(replies reply: ls){
            result = deleteRow(reply.getRoot()) && deleteRow(reply.getReply())
                    && deleteReplyRow(reply.getRoot());
        }
        return result;
    }

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

    public void adminDisplay(){
        System.out.println("\n============================================================"); // 60 = signs
        System.out.println("DS CONFESSION");
        System.out.println("Admin Panel");
        System.out.println("\n============================================================"); // 60 = signs
        System.out.println(">> \"W\" - View all posts");
        System.out.println(">> \"D\" - Delete posts");
        System.out.println(">> \"X\" - Exit");
    }

    public void deleteNOTsuccessfuldisplay(){
        System.out.println("------------------------------------------------------------"); // 60 - signs
        System.out.println(">> Deletion failed");
        System.out.println();
    }

    public void deleteSuccessfulDisplay(){
        System.out.println("------------------------------------------------------------"); // 60 - signs
        System.out.println(">> Deletion successful");
        System.out.println();
    }
}
