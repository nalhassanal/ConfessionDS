
package main;

import com.google.common.collect.Multimap;
import fileUtil.FileUtil;
import java.util.HashMap;
import java.util.Scanner;

public class Reply extends confession{
    private final Scanner input = new Scanner(System.in);
    private final FileUtil fileUtil = new FileUtil();
    
    public Reply() {
    }
    
    public void createReply(){
        createReplyConfession();
        
        
        
    }

    public void createReplyConfession(String replyRootID){
        System.out.println("\n============================================================"); // 60 = signs
        System.out.println(">> Please enter your reply confession content.");
        System.out.println(">> Insert \"-1\" to submit your reply confession.");
        System.out.println("------------------------------------------------------------"); // 60 - signs
        System.out.println("Reply confession content:");
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
        confessionPair replies = new confessionPair(rootID, confessionID, replies);
        if(addReplyToFile(replyRootID, confessionID, replies)){
            successfulPostDisplay(confessionID);
        }
        else {
            unSuccessfulPostDisplay();
        }
    }

    
    public boolean addReplyToFile(String rootID, String replyID, confessionPair replies){
        HashMap<String, String> map = new HashMap<>();
        map.put(rootID, replyID);
        return fileUtil.addToFile(map, replies.getDate(),"reply.txt");
    }
    
    public void successfulReplyPostDisplay(confessionPair content){
        System.out.println("============================================================"); // 60 = signs
        System.out.println(">> Submitted at "+content.getDate() + ".");
        System.out.println(">> Reply confession post ID: " + content.getId() + ".");
        System.out.println(">> Your confession will be published soon.");
        System.out.println("============================================================"); // 60 = signs
    }
    }
