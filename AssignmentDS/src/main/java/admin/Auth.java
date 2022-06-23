package admin;

import SQL.SQLconnect;
import SQL.SQLutil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    public boolean register(){
        String inUserN, inUserP, inUserE;
        System.out.println("------------------------------------------------------------"); // 60 - signs
//        System.out.print(">> Enter your email address: ");
//        inUserE = input.nextLine();
//        while (!checkEmail(inUserE)){
//            System.out.println("Please enter a valid email");
//            System.out.print(">> Enter your email address: ");
//            inUserE = input.nextLine();
//        }
        System.out.print(">> Enter your preferred username: ");
        inUserN = input.nextLine();
        while (!checkUsername(inUserN)){
            System.out.println("Your username must contain 4 or more characters");
            System.out.println("can only contain \"&\", \"-\", \"_\", \"!\"");
            System.out.print(">> Enter your username: ");
            inUserN = input.nextLine();
        }
        System.out.print(">> Enter your preferred password: ");
        inUserP = input.nextLine();
        while (!checkPassword(inUserP)){
            System.out.println("Your password must contain 8 or more characters ");
            System.out.println("with numbers and at least one capital letter");
            System.out.print(">> Enter your password: ");
            inUserP = input.nextLine();
        }

        return addToTable(new User(inUserN, inUserP));
    }

    public boolean addToTable(User user){
        boolean success = false;
        String query = "insert into adminUser (username, password) values (?, ?)";
        PreparedStatement ps;

        try{
            ps = con.prepareStatement(query);
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPassword());
            success = !ps.execute();
            ps.close();
        } catch (SQLException ex){
            ex.printStackTrace();
        }

        return success;
    }

//    public boolean checkEmail(String email){
//        final String regex = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
//        Pattern emailPattern = Pattern.compile(regex);
//        Matcher emailMatch = emailPattern.matcher(email);
//
//        return emailMatch.matches();
//    }

    public boolean checkUsername(String username){
        final String regex = "[\\w&\\-_!]{4,}";
        Pattern userPattern = Pattern.compile(regex);
        Matcher userMatch = userPattern.matcher(username);

        return userMatch.matches();
    }

    public boolean checkPassword(String password){
        final String regex = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z]).{8,}$";

        Pattern passwordPattern = Pattern.compile(regex);
        Matcher passwordMatch = passwordPattern.matcher(password);

        return passwordMatch.matches();
    }

    public void authDisplay(){
        System.out.println("============================================================"); // 60 = signs
        System.out.println("Welcome to Confessions DS Admin");
        System.out.println("============================================================"); // 60 = signs
//        System.out.println(">> L - login");
//        System.out.println(">> R - register");
    }

}
