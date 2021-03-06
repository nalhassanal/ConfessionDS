package main;

import SQL.SQLconnect;
import SQL.SQLutil;

import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class is the driver class for searching through a confession
 */
public class searchConfession {
    /**
     * Scanner object to allow user input to be read
     */
    private final Scanner input;
    /**
     * SQLutil class object to allow access to SQL utilities
     */
    private final SQLutil util;
    /**
     * Connection object to allow connection to the mysql database
     */
    private final Connection con;

    /**
     * constructor that will initialize the class variables
     */
    public searchConfession(){
        SQLconnect connect = new SQLconnect();
        con = connect.connector();
        input = new Scanner(System.in);
        util = new SQLutil();
    }

    /**
     * the starter method for the searchConfession class
     */
    public void start(){
        if(con == null){
            System.out.println("Could not connect to database");
            System.out.println("Please check connection");
            return;
        }
        searchDisplay();
        System.out.print("> ");
        String searchedID = input.nextLine();
        System.out.println("============================================================"); // 60 = signs
        System.out.println();

        int SEARCH_TYPE = patternCheck(searchedID);
        System.out.println("============================================================"); // 60 = signs
        switch (SEARCH_TYPE){
            case 0 :
                anyType(searchedID);
                break;
            case 1 :
                IDType(searchedID);
                break;
            case 2 :
                DTType(searchedID);
                break;
            case 3 :
                dateType(searchedID);
                break;
        }
    }

    /**
     * method that processes the searched text that is of date format
     * and also prints out all the results of the search query
     * @param queried the searched(input) text
     */
    public void dateType(String queried){
        ArrayList<String> dates = util.getDate(con);
//        System.out.println(dates);
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
//        System.out.println(ids.size());

        System.out.println(">> Search results by the keywords \"" + queried + "\".");
        if (ids.isEmpty() && content.isEmpty()) {
            System.out.println(">> The search yielded no results");
            start();
            return;
        }

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
                start();
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

    /**
     * method that processes the searched text that is of date & time format
     * and also prints out all the results of the search query
     * @param queried the searched(input) text
     */
    public void DTType(String queried){
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
        if (ids.isEmpty() && content.isEmpty()) {
            System.out.println(">> The search yielded no results");
            start();
            return;
        }

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
                start();
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

    /**
     * method that processes the searched text that is of post ID format
     * and also prints out all the results of the search query
     * @param queried the searched(input) text
     */
    public void IDType(String queried) {
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
            start();
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
                start();
                choice = "q";
            }
        } while (!choice.equalsIgnoreCase("q"));

    }

    /**
     * method that processes the searched text that is of any(open) format
     * and also prints out all the results of the search query
     * @param queried the searched(input) text
     */
    public void anyType(String queried){
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
        if (ids.isEmpty() && content.isEmpty()) {
            System.out.println(">> The search yielded no results");
            start();
            return;
        }
        HashMap<Integer, String> numIDpair = new HashMap<>();

        for (int i = 0; i < ids.size(); i++){
            String id = ids.poll() , confessions = content.poll();
            confessions = confessions.replace("\n", " ");
            if (confessions.length() > 43){
                confessions = confessions.substring(0, 43) + "...";
            }
            numIDpair.put(i+1, id);
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
                start();
                choice = "q";
            }
            else if(patternCheck(choice) == 0 &&!choice.equalsIgnoreCase("q") && numIDpair.containsKey(Integer.valueOf(choice))){
                IDType(numIDpair.get(Integer.valueOf(choice)));
                choice = "q";
            }
            else if (patternCheck(choice) == 1){
                IDType(choice);
                choice = "q";
            }
            else if ("d".equalsIgnoreCase(choice)){
                System.out.println("how do i flip to the next page");
            }

        } while (!choice.equalsIgnoreCase("q"));

    }

    /**
     * method that checks the input text with 3 separate regex that will determine
     * which format of search is input
     * @param text input text
     * @return 3 for date pattern, 2 for date and time pattern, 1 for post ID pattern, 0 for any pattern
     */
    public int patternCheck(String text){
        final String dateRegex = "\\d{4}-(0[1-9]|1[0-2])-(0[1-9]|[12][0-9]|3[01])";
        final String dateTimeRegex = "\\d{4}-(0[1-9]|1[0-2])-(0[1-9]|[12][0-9]|3[01]) ([1-9]|0[1-9]|1[0-2]):[0-5][0-9] ([AaPp][Mm])$";
        final String postIDRegex = "([Dd][Ss])([0-9])([0-9])([0-9])([0-9])([0-9])$";

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

    /**
     * method that displays all the format that can be used to search confessions
     */
    public void searchDisplay(){
        System.out.println("============================================================"); // 60 = signs
        System.out.println(">> Searching formats and options:");
        System.out.println(">> \"YYYY-MM-DD\" - search by date");
        System.out.println(">> \"YYYY-MM-DD HH:mm am/pm\" - search by date time");
        System.out.println(">> \"DSXXXXX\" - search by post ID");
        System.out.println(">> <any> - search by keywords");
        System.out.println("------------------------------------------------------------"); // 60 - signs
    }

    /**
     * method that displays the choices that can be done
     * @param type control which choices are displayed
     */
    public void displayOptions(int type){
        System.out.println("------------------------------------------------------------"); // 60 - signs
//        if (type != 1 && type != 2)
//            System.out.println(">> \"D\" - view next page");
        if (type != 1)
            System.out.println(">> <number> or \"#DSXXXXX\" - view selected confession post");
        System.out.println(">> \"R\" - search again");
        System.out.println(">> \"Q\" - quit searching post");
    }
}
