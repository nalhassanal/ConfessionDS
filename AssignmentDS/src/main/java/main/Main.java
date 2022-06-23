package main;

import java.util.Scanner;
import admin.Auth;
public class Main {
    private final static Scanner input = new Scanner(System.in);
    public static void main(String[] args) {
        String choice;
        do {
            display();
            choice = input.nextLine();
            switch (choice.toLowerCase()) {
                case "w":
                    view();
                    break;

                case "s":
                    post();
                    break;

                case "a":
                    search();
                    break;

                case "admin":
                    admin();
                    break;

                default:
                    System.out.println("------------------------------------------------------------"); // 60 - signs
                    System.out.println("Wrong input");
                    System.out.println("Try again");
                    break;
            }
        } while (!choice.equalsIgnoreCase("q"));
        System.out.println();
        input.close();
        System.out.println("Thank You For Using Our Service !");
    }

    /**
     *
     */
    public static void display(){
        System.out.println("============================================================"); // 60 = signs
        System.out.println("Welcome to Confessions DS");
        System.out.println("============================================================"); // 60 = signs
        System.out.println(">> What do you want to do today?");
        System.out.println(">> \"W\" - view posts");
        System.out.println(">> \"S\" - post confessions");
        System.out.println(">> \"A\" - search posts");
        System.out.println(">> \"Q\" - exit");
        System.out.println("------------------------------------------------------------"); // 60 - signs
    }

    /**
     *
     */
    public static void admin(){
        Auth admin = new Auth();
        admin.start();
    }

    /**
     *
     */
    public static void post(){
        confession conf = new confession();
        conf.mainDisplay();
    }

    /**
     *
     */
    public static void view(){
        displayConfession disp = new displayConfession();
        System.out.println("============================================================");
        System.out.println("Available Confession ID :");
        disp.displayIDAvailable();
        System.out.println("\n============================================================"); // 60 = signs
        System.out.println(">> Enter the post ID that you want to see");
        String startingID = input.nextLine();
        if(disp.hasID(startingID)){
            disp.start(startingID);
        }else{
            System.out.println("\nSorry...The ID you Enter Is Not Exists. Please Only Enter The Available Id.");
            view();
        }
    }

    /**
     *
     */
    public static void search(){
        searchConfession search = new searchConfession();
        search.start();
    }
}
