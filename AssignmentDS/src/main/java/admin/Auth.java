package admin;

import SQL.SQLconnect;
import SQL.SQLutil;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Scanner;

public class Auth {
    private class resultPair{
        private boolean result;
        private User user;

        public resultPair(boolean result, User user){
            this.result = result;
            this.user = user;
        }

        public boolean isResult() {
            return result;
        }

        public User getUser() {
            return user;
        }

        @Override
        public String toString(){
            return "Result: " + result + ", User: " + user.toString();
        }
    }

    private final Scanner input = new Scanner(System.in);
    private final SQLutil util = new SQLutil();
    private admin admin = new admin();
    private Connection con;

    public Auth(){
        SQLconnect connect = new SQLconnect();
        con = connect.connector();
    }

    public void start(){
        if(con == null){
            System.out.println("Could not connect to database");
            System.out.println("Please check connection");
            return;
        }
        authDisplay();
        System.out.print(">> Continue? [y/n]");
        String choice = input.nextLine();
        if (choice.equalsIgnoreCase("y")){
            resultPair res = login();
            while (!res.isResult()){
                res = login();
            }
            System.out.println("------------------------------------------------------------"); // 60 - signs
            System.out.println();
            System.out.println("Welcome " + res.getUser().getUsername());
            admin.start();
        }
    }

    public resultPair login(){
        int progress = 0;
        boolean success = false;
        User user = new User();
        String inUserN, inUserP;
        System.out.println();
        System.out.println("------------------------------------------------------------"); // 60 - signs
        System.out.print(">> Enter your username: ");
        inUserN = input.nextLine();
        System.out.print(">> Enter your password: ");
        inUserP = input.nextLine();

        ArrayList<User> ls = util.getUsers(con);
        for(User element: ls){
            if (element.getUsername().equals(inUserN)) {
                if (element.getPassword().equals(inUserP)) {
                    progress = 2;
                    user.setUsername(element.getUsername());
                    user.setPassword(element.getPassword());
                    break;
                }
                else
                    progress = 1;
            }
        }

        if (progress==2)
            success = true;
        else {
            System.out.println("Wrong password or username");
        }

        return new resultPair(success, user);
    }

    public void authDisplay(){
        System.out.println("============================================================"); // 60 = signs
        System.out.println("Welcome to Confessions DS Admin");
        System.out.println("============================================================"); // 60 = signs
    }

}
