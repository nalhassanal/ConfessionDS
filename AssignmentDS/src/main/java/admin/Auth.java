package admin;

import SQL.SQLconnect;
import SQL.SQLutil;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Scanner;

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
                else {
                    boolean progress;
                    do {
                        progress = login();
                        System.out.println(progress); // debug;
                    } while (progress);
                }
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

    public void register(){}

    public void authDisplay(){
        System.out.println("============================================================"); // 60 = signs
        System.out.println("Welcome to Confessions DS Admin");
        System.out.println("============================================================"); // 60 = signs
        System.out.println(">> L - login");
        System.out.println(">> R - register");
    }

}
