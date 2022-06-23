package admin;

import SQL.SQLconnect;
import SQL.SQLutil;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Scanner;

public class Auth {
    /**
     * container class that contains a boolean and also a User type variable
     */
    private class resultPair{
        /**
         * contains the result of login method
         */
        private final boolean result;
        /**
         * contains the User that is accessing the admin page
         */
        private final User user;

        /**
         * @param result the result of login method
         * @param user the User that successfully log in
         */
        public resultPair(boolean result, User user){
            this.result = result;
            this.user = user;
        }

        /**
         * getter method for result variable
         * @return result
         */
        public boolean getResult() {
            return result;
        }

        /**
         * getter method for User variable
         * @return The User type variable
         */
        public User getUser() {
            return user;
        }


        /**
         * @return string representation of class
         */
        @Override
        public String toString(){
            return "Result: " + result + ", User: " + user.toString();
        }
    }

    /**
     * Scanner object to allow user input to be read
     */
    private final Scanner input;
    /**
     * SQLutil class object to allow access to SQL utilities
     */
    private final SQLutil util;
    /**
     * admin class object to allow access to admin page
     */
    private final admin admin;
    /**
     * Connection object to allow connection to the mysql database
     */
    private final Connection con;

    /**
     * Auth class constructor that initialize the private variables
     */
    public Auth(){
        SQLconnect connect = new SQLconnect();
        con = connect.connector();
        util = new SQLutil();
        admin = new admin();
        input = new Scanner(System.in);
    }

    /**
     * the starter method that is the main method that accesses Auth class
     */
    public void start() {
        if (con == null) {
            System.out.println("Could not connect to database");
            System.out.println("Please check connection");
        } else {
            authDisplay();
            System.out.print(">> Continue? [y/n]");
            String choice = input.nextLine();
            if (choice.equalsIgnoreCase("y")) {
                resultPair res = login();
                while (!res.getResult()) {
                    res = login();
                }
                System.out.println("------------------------------------------------------------"); // 60 - signs
                System.out.println();
                System.out.println("Welcome " + res.getUser().getUsername());
                admin.start();
            }
        }
    }

    /**
     * The method that authorizes the user input to allow user to access admin page
     * @return resultPair that stores the result and the user
     */
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

    /**
     * the main display for Auth
     */
    public void authDisplay(){
        System.out.println("============================================================"); // 60 = signs
        System.out.println("Welcome to Confessions DS Admin");
        System.out.println("============================================================"); // 60 = signs
    }

}
