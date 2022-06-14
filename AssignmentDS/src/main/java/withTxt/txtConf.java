package withTxt;

import fileUtil.FileUtil;
import main.confessionPair;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Scanner;

public class txtConf {

    private final FileUtil fileUtil = new FileUtil();
    private final Scanner input = new Scanner(System.in);

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

    public void confessDisplay(){
        System.out.println("\n============================================================"); // 60 = signs
        System.out.println(">> Please enter you confession content.");
        System.out.println(">> Insert \"-1\" to submit your confession.");
        System.out.println("------------------------------------------------------------"); // 60 - signs
        System.out.println("Confession content:");
    }

    public void replyDisplay(){
        System.out.println("\n============================================================"); // 60 = signs
        System.out.println(">> Please enter your reply confession content.");
        System.out.println(">> Insert \"-1\" to submit your reply confession.");
        System.out.println("------------------------------------------------------------"); // 60 - signs
        System.out.println("Reply confession content:");
    }

    public void createConfession(){
        confessDisplay();
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
        // queue
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

    public boolean addContentToFile(String contentID, String contentSentence, confessionPair content){
        HashMap<String, String> map = new HashMap<>();
        map.put(contentID, contentSentence);
        return fileUtil.addToFile(map, content.getDate(),"confession.txt",false) && fileUtil.addContentToFile(map);
    }

    public void createReplyConfession(String rootID){
        replyDisplay();
        StringBuilder confessionContent = new StringBuilder();
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

        String replyID = String.format("DS%05d", idNum);
        confessionPair replies = new confessionPair(replyID, rootID);
        confessionPair replyContent = new confessionPair(replyID, confessionContent.toString());

        if(addReplyToFile(replyID, rootID, replies)){
            successfulReplyPostDisplay(replies);
            addContentToFile(replyID, confessionContent.toString(), replyContent);
        }
        else {
            unSuccessfulPostDisplay();
        }
    }

    public boolean addReplyToFile(String rootID, String replyID, confessionPair replies){
        HashMap<String, String> map = new HashMap<>();
        map.put(rootID, replyID);
        return fileUtil.addToFile(map, replies.getDate(),"reply.txt", true);
    }

    public void successfulReplyPostDisplay(confessionPair content){
        System.out.println("============================================================"); // 60 = signs
        System.out.println(">> Submitted at " + content.getDate() + ".");
        System.out.println(">> Reply confession post ID: " + content.getId() + ".");
        System.out.println(">> Your confession will be published soon.");
        System.out.println("============================================================"); // 60 = signs
    }
}
