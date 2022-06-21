package main;

import java.util.Scanner;

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
            }
        } while (!choice.equalsIgnoreCase("q"));
        System.out.println("\nThank You For Using Our Service !");
    }

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

    public static void post(){
        String stop;
        confession conf = new confession();
        do {
            conf.mainDisplay(); // akan ada condition
            System.out.println("Do you want to continue? [yes/no]");
            stop = input.nextLine();
        } while (!stop.equalsIgnoreCase("no"));
        System.out.println("Thank you for using our service");
        // which will break the loop
        // ex. spam filer maybe?
    }

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

    public static void search(){
        searchConfession search = new searchConfession();
        String choice;
        do {
            search.start();
            System.out.println("============================================================"); // 60 = signs
            System.out.println(">> Do you want to continue?");
            System.out.println(">> W - yes");
            System.out.println(">> X - no");
            choice = input.nextLine();
        }while (!choice.equalsIgnoreCase("x"));
    }

}
