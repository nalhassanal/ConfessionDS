package main;

import fileUtil.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Scanner;

public class confession {
    private final FileUtil fileUtil = new FileUtil();
    private final Scanner input = new Scanner(System.in);

    public confession(){

    }

    public void mainDisplay(){
        System.out.println("============================================================"); // 60 = signs
        System.out.println(">> Please enter the confession post ID that you want to reply.");
        System.out.println(">> Leave it blank if you don't want to reply to a confession post.");
        System.out.println("------------------------------------------------------------"); // 60 - signs
        System.out.print("Reply confession post ID: ");
        String replyID = input.nextLine();
        if (replyID.isBlank())
            createConfession();
        else{
            if (checkForKey(replyID)) {
                System.out.println("------------------------------------------------------------"); // 60 - signs
                System.out.println(">> Confession post ID exists!");
                System.out.println("============================================================"); // 60 = signs
                // reply method 
                // masuk dalma file yang reply
                createConfession();
            }
        }
    }

    public boolean checkForKey(String ID){
        return fileUtil.hasDuplicateKeys(ID);
    }

    public void createConfession(){
        System.out.println("\n============================================================"); // 60 = signs
        System.out.println(">> Please enter you confession content.");
        System.out.println(">> Insert \"-1\" to submit your confession.");
        System.out.println("------------------------------------------------------------"); // 60 - signs
        System.out.println("Confession content:");
        StringBuilder confessionContent = new StringBuilder();
        String confessionID;
        while (true){
            String insert = input.nextLine();
            if (insert.equals("-1")){
                break;
            }
            confessionContent.append(insert).append("\n");
        }

        System.out.println("============================================================"); // 60 = signs
        String temp = IDIncrement();
        int idNum = Integer.parseInt(temp.substring(2));
        confessionID = String.format("DS%05d", idNum);
        confessionPair confess = new confessionPair(confessionID, confessionContent.toString());
        if(addContentToFile(confessionID, confessionContent.toString(), confess)){
            successfulPostDisplay(confess);
        }
        else {
            unSuccessfulPostDisplay();
        }
    }

    public String IDIncrement(){
        HashMap<String, String> map = fileUtil.mapFromFile();
        String newID;
        ArrayList<String> keys = new ArrayList<>(map.keySet());
        Collections.sort(keys);
        newID = "DS" + (Integer.parseInt(keys.get(keys.size() - 1).substring(2)) + 1);
        return newID;
    }

    public void unSuccessfulPostDisplay(){
        System.out.println("============================================================"); // 60 = signs
        System.out.println(">> Your submission has failed");
        System.out.println("============================================================"); // 60 = signs
    }

    public void successfulPostDisplay(confessionPair content){
        System.out.println("============================================================"); // 60 = signs
        System.out.println(">> Submitted at "+content.getDate() + ".");
        System.out.println(">> Confession post ID: " + content.getId() + ".");
        System.out.println(">> Your confession will be published soon.");
        System.out.println("============================================================"); // 60 = signs
    }

    public boolean addContentToFile(String contentID, String contentSentence, confessionPair content){
        HashMap<String, String> map = new HashMap<>();
        map.put(contentID, contentSentence);
        return fileUtil.addToFile(map, content.getDate(),"confession.txt") && fileUtil.addContentToFile(map);
    }

}
