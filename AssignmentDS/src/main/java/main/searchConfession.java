package main;

import java.util.Scanner;

public class searchConfession {

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        searchDisplay();
        String searchedID = input.nextLine();
        start(searchedID);
    }

    public static void start(String queriedID){

    }

    public static void patternCheck(String text){
        String dateRegex = "/^\\d{4}-(0[1-9]|1[0-2])-(0[1-9]|[12][0-9]|3[01])$/";
        String dateTimeRegex;
    }


    public static void searchDisplay(){
        System.out.println("============================================================"); // 60 = signs
        System.out.println(">> Searching formats and options:");
        System.out.println(">> \"YYYY-MM-DD\" - search by date");
        System.out.println(">> \"YYYY-MM-DD HH:mm am/pm\" - search by date time");
        System.out.println(">> \"#DSXXXXX\" - search by post ID");
        System.out.println(">> <any>");
        System.out.println("------------------------------------------------------------"); // 60 - signs
    }

    public static void displayOptions(){
        System.out.println("------------------------------------------------------------"); // 60 - signs
        System.out.println(">> \"D\" - view next page");
        System.out.println(">> <number> or \"#DSXXXXX\" - view selected confession post");
        System.out.println(">> \"R\" - search again");
        System.out.println(">> \"Q\" - quit searching post");
    }
}
