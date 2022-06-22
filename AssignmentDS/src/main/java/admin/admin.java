package admin;

import SQL.SQLconnect;
import SQL.SQLutil;
import main.displayConfession;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;

public class admin {

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

        if(checkReply(query)){
            deleteReplyRow(query);
            deleteRow(query);
        }
//        deleteRow(query);
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

//    public boolean deleteReplies(String query){
//
//    }

    private void deleteReplyRow(String selected){
        String id = selected.substring(2);
        String updateQuery = "delete from reply where main = " + id;
        Statement stmt;
        try{
            stmt = con.createStatement();
            stmt.executeUpdate(updateQuery);
            stmt.close();
        } catch (SQLException ex){
            ex.printStackTrace();
        }
    }

    private void deleteRow(String selected){
        int id = Integer.parseInt(selected.substring(2));
        boolean last = true;

        String updateQuery = "delete from confession where id = " + id;
        if (id < util.getID(con))
            last = false;

        Statement stmt;
        try{
            stmt = con.createStatement();
            stmt.executeUpdate(updateQuery);
            if (last) {
                String updateCounter = "ALTER TABLE confession AUTO_INCREMENT = " + id;
                stmt.executeUpdate(updateCounter);
            }
            stmt.close();
        }catch (SQLException ex){
            ex.printStackTrace();
        }
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
}
