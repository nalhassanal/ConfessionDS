package admin;

import SQL.SQLconnect;
import SQL.SQLutil;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Auth {
    private final Scanner input = new Scanner(System.in);
    private final SQLutil util = new SQLutil();
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
        System.out.println("------------------------------------------------------------"); // 60 - signs
        System.out.print(">> ");
        String choice = input.nextLine();
        switch (choice.toLowerCase()){
            case "l":
                if (login()){
                    System.out.println("Welcome");
                }
//                else {
//                    boolean progress;
//                    do {
//                        progress = login();
//                        System.out.println(progress); // debug;
//                    } while (progress);
//                }
                break;

            case "r":
                register();
                break;
        }
    }

    public boolean login(){
        int progress = 0;
        boolean success = false;
        String inUserN, inUserP;
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

        return success;
    }

    public void register(){
        String inUserN, inUserP;
        System.out.println("------------------------------------------------------------"); // 60 - signs
        System.out.print(">> Enter your username: ");
        inUserN = input.nextLine();
        System.out.print(">> Enter your password: ");
        inUserP = input.nextLine();
        while (!checkPassword(inUserP)){
            System.out.println("Your password must contain 8 or more characters ");
            System.out.println("with numbers and at least one capital letter");
            System.out.print(">> Enter your password: ");
            inUserP = input.nextLine();
        }



    }

    public boolean addToTable(User user){
        boolean success = false;

        return success;
    }

//    public boolean checkUsername(String username){
//        final String regex =
//    }

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
        System.out.println(">> L - login");
        System.out.println(">> R - register");
    }

}
