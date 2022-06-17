package main;

import SQL.SQLconnect;
import SQL.SQLutil;

import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class searchConfession {

    static SQLutil util = new SQLutil();
    static SQLconnect connect = new SQLconnect();
    static Connection con = connect.connector();
    static Scanner input = new Scanner(System.in);
    public static void main(String[] args) {
        searchDisplay();
//        System.out.print("> ");
//        String searchedID = input.nextLine();
//        System.out.println("============================================================"); // 60 = signs
//        System.out.println();
//        start(searchedID);

        String [] queries = {"2022-06-14", "2022-06-16"}; // "2022-06-16 5:21 AM" "2022-06-14 2:23 PM", "2022-06-14" , "ds00010" , "DS00023" ,"hi"
        for (String queriedID : queries) {
            System.out.print("> " + queriedID);
            System.out.println("\n============================================================"); // 60 = signs
            System.out.println();
            start(queriedID);
        }
    }

    public static void start(String queriedID){
        int SEARCH_TYPE = patternCheck(queriedID);
        System.out.println("============================================================"); // 60 = signs
        switch (SEARCH_TYPE){
            case 0 :
                anyType(queriedID);
                break;
            case 1 :
                IDType(queriedID);
                break;
            case 2 :
                DTType(queriedID);
                break;
            case 3 :
                dateType(queriedID);
                break;
        }
    }

    public static void dateType(String queried){
        ArrayList<String> dates = util.getDate(con);
        System.out.println(dates);
        ArrayList<confessionPair> ls = util.readFromTable(con);
        Queue<String> ids = new LinkedList<>();
        Queue<String> content = new LinkedList<>();
        for (int i = 0; i < dates.size(); i++){
            if (dates.get(i).equalsIgnoreCase(queried))
                for(int j = i; j >= 0; j--){
                    Date date = ls.get(j).getCurrentDate();
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    String temp = dateFormat.format(date);
                    if (temp.equalsIgnoreCase(queried)){
                        ids.offer(ls.get(j).getId());
                        content.offer(ls.get(j).getContent());
                    }
                }
        }
        Set<String> temp = new LinkedHashSet<>(ids);
        ids = new LinkedList<>(temp);

        temp = new LinkedHashSet<>(content);
        content = new LinkedList<>(temp);
        System.out.println(ids.size());

        System.out.println(">> Search results by the keywords \"" + queried + "\".");
        if (ids.isEmpty() && content.isEmpty())
            System.out.println(">> The search yielded no results");

        // TODO: try buat next page with this test case
        int index = 1;
        while(!ids.isEmpty() && !content.isEmpty()){
            String id = ids.poll() , confessions = content.poll();
            confessions = confessions.replace("\n", " ");
            if (confessions.length() > 43){
                confessions = confessions.substring(0, 43) + "...";
            }
            System.out.printf((index) + "%3s", " ");
            System.out.println(id + " \"" + confessions + "\"");
            index = index + 1 ;
        }

        String choice;
        do {
            displayOptions(3);
            System.out.println("------------------------------------------------------------"); // 60 - signs
            System.out.print("> ");
            choice = input.next();
            if ("r".equalsIgnoreCase(choice)) {
                input.nextLine();
                main(null);
                choice = "q";
            }
            else if (patternCheck(choice) == 1){
                IDType(choice);
                choice = "q";
            }
            else if ("d".equalsIgnoreCase(choice)){
                // TODO: next page
                System.out.println("how do i flip to the next page bruh");
            }
        } while (!choice.equalsIgnoreCase("q"));
    }

    public static void DTType(String queried){
        ArrayList<String> dateTimes = util.getDateTime(con);
        ArrayList<confessionPair> ls = util.readFromTable(con);
        Queue<String> ids = new LinkedList<>();
        Queue<String> content = new LinkedList<>();
        for(String element: dateTimes)
            if (element.equalsIgnoreCase(queried))
                for(confessionPair pairIn : ls)
                    if (pairIn.getDate().equals(queried)){
                        ids.offer(pairIn.getId());
                        content.offer(pairIn.getContent());
                    }

        System.out.println(">> Search results by the keywords \"" + queried + "\".");
        if (ids.isEmpty() && content.isEmpty())
            System.out.println(">> The search yielded no results");

        for (int i = 0; i < ids.size(); i++){
            String id = ids.poll() , confessions = content.poll();
            confessions = confessions.replace("\n", " ");
            if (confessions.length() > 43){
                confessions = confessions.substring(0, 43) + "...";
            }
            System.out.printf((i+1) + "%3s", " ");
            System.out.println(id + " \"" + confessions + "\"");
        }

        String choice;
        do {
            displayOptions(2);
            System.out.println("------------------------------------------------------------"); // 60 - signs
            System.out.print("> ");
            choice = input.next();
            if ("r".equalsIgnoreCase(choice)) {
                input.nextLine();
                main(null);
                choice = "q";
            }
            else if (patternCheck(choice) == 1){
                IDType(choice);
                choice = "q";
            }
            else if ("d".equalsIgnoreCase(choice)){
                // TODO: next page
                System.out.println("how do i flip to the next page bruh");
            }


        } while (!choice.equalsIgnoreCase("q"));
    }

    public static void IDType(String queried) {
        System.out.println(">> Search results by the keywords \"" + queried + "\".");
        ArrayList<confessionPair> ls = util.readFromTable(con);
        String content;
        confessionPair pair = null;
        for (confessionPair l : ls) {
            if (l.getId().equals(queried))
                pair = l;
        }
        if (pair == null) {
            System.out.println("The search key does not exist");
            return;
        }
        System.out.println(pair);
        String choice;
        do {
            displayOptions(1);
            System.out.println("------------------------------------------------------------"); // 60 - signs
            System.out.print("> ");
            choice = input.next();
            if ("r".equalsIgnoreCase(choice)) {
                input.nextLine();
                main(null);
                choice = "q";
            }
        } while (!choice.equalsIgnoreCase("q"));

    }
    /*
    TODO not complete yet
        left only the next page part
     */
    public static void anyType(String queried){
        ArrayList<String> contents = util.getContents(con);
        ArrayList<confessionPair> ls = util.readFromTable(con);
        Queue<String> ids = new LinkedList<>();
        Queue<String> content = new LinkedList<>();
        for (String element: contents)
            if (element.contains(queried))
                for (confessionPair pair: ls)
                    if (pair.getContent().equals(element)) {
                        ids.offer(pair.getId());
                        content.offer(element);
                    }

        System.out.println(">> Search results by the keywords \"" + queried + "\".");
        if (ids.isEmpty() && content.isEmpty())
            System.out.println(">> The search yielded no results");

        for (int i = 0; i < ids.size(); i++){
            String id = ids.poll() , confessions = content.poll();
            confessions = confessions.replace("\n", " ");
            if (confessions.length() > 43){
                confessions = confessions.substring(0, 43) + "...";
            }
            System.out.printf((i+1) + "%3s", " ");
            System.out.println(id + " \"" + confessions + "\"");

        }

        String choice;
        do {
            displayOptions(0);
            System.out.println("------------------------------------------------------------"); // 60 - signs
            System.out.print("> ");
            choice = input.next();
            if ("r".equalsIgnoreCase(choice)) {
                input.nextLine();
                main(null);
                choice = "q";
            }
            else if (patternCheck(choice) == 1){
                IDType(choice);
                choice = "q";
            }
            else if ("d".equalsIgnoreCase(choice)){
                System.out.println("how do i flip to the next page bruh");
            }

        } while (!choice.equalsIgnoreCase("q"));

    }

    public static int patternCheck(String text){
        final String dateRegex = "\\d{4}-(0[1-9]|1[0-2])-(0[1-9]|[12][0-9]|3[01])";
        final String dateTimeRegex = "\\d{4}-(0[1-9]|1[0-2])-(0[1-9]|[12][0-9]|3[01]) ([1-9]|0[1-9]|1[0-2]):[0-5][0-9] ([AaPp][Mm])$";
        final String postIDRegex = "(DS)([0-9])([0-9])([0-9])([0-9])([0-9])$";

        Pattern date = Pattern.compile(dateRegex);
        Pattern dateTime = Pattern.compile(dateTimeRegex);
        Pattern id = Pattern.compile(postIDRegex);

        Matcher dateMatch = date.matcher(text);
        Matcher dateTimeMatch = dateTime.matcher(text);
        Matcher IDMatch = id.matcher(text);

        if (dateMatch.matches()){
            return 3;
        }
        else if (dateTimeMatch.matches()){
            return 2;
        }
        else if (IDMatch.matches()){
            return 1;
        }
        else
            return 0;
    }

    public static void searchDisplay(){
        System.out.println("============================================================"); // 60 = signs
        System.out.println(">> Searching formats and options:");
        System.out.println(">> \"YYYY-MM-DD\" - search by date");
        System.out.println(">> \"YYYY-MM-DD HH:mm am/pm\" - search by date time");
        System.out.println(">> \"DSXXXXX\" - search by post ID");
        System.out.println(">> <any> - search by keywords");
        System.out.println("------------------------------------------------------------"); // 60 - signs
    }

    public static void displayOptions(int type){
        System.out.println("------------------------------------------------------------"); // 60 - signs
        if (type != 1 && type != 2)
            System.out.println(">> \"D\" - view next page");
        if (type != 1)
            System.out.println(">> <number> or \"#DSXXXXX\" - view selected confession post");
        System.out.println(">> \"R\" - search again");
        System.out.println(">> \"Q\" - quit searching post");
    }
}
