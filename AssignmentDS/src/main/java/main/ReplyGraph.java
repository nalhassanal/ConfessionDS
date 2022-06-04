
package main;

import dataStructure.*;
import fileUtil.FileUtil;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Scanner;
import static main.testConfession.addToFile;
import static main.testConfession.addToList;
import static main.testConfession.confessionList;
import static main.testConfession.idIncrement;

public class ReplyGraph {
    public static void main(String[] args) throws FileNotFoundException {
        FileUtil fileUtil = new FileUtil();
        String filename = "confession.txt";
        String filename2 = "id.txt";
        LinkedList<String> content = new LinkedList<>();
        StringBuilder content2 = new StringBuilder();
        WeightedGraph<String,String> contentGraph = new WeightedGraph<>();
        Scanner sc = new Scanner(System.in);
        String[] arr;
        int index = 0;
        String id = contentGraph.getVertex(contentGraph.getSize() - 1);
        
        File fileObj = new File(filename);
            Scanner readFile = new Scanner(new FileInputStream(fileObj));
        File fileObj2 = new File(filename2);
            Scanner readFile2 = new Scanner(new FileInputStream(fileObj2));
            
            while (readFile2.hasNextLine()){
                contentGraph.addVertex(readFile2.nextLine());
            }
            while (readFile.hasNextLine()){
                content.add(readFile.nextLine());
            }
            
            String raw = content.toString().replaceAll(", ", " ").replaceAll("\\[", "").replaceAll("\\]", "");
            arr = raw.split("  ");
            
            HashMap map = fileUtil.mapFromFile();
            System.out.println(map);
            ArrayList <String> keys = new ArrayList<>(map.keySet());
            Collections.sort(keys);
            System.out.println(keys);
            for (String key: keys){
                System.out.println(key +"=`"+map.get(key));
            }
            
            
            
//            System.out.println("How Many Confession Do We Have? : " + contentGraph.getSize());
//            System.out.print("All Available Id In The Confession : ");
//            for(int i = 0; i < contentGraph.getSize(); i++){
//                System.out.print(contentGraph.getVertex(i) + " ");
//            } 
//            System.out.println("  ");
//            System.out.println("================================================================");
//            System.out.println("Please Enter The Id Confession You Want To See : ");
//            System.out.flush();
//            String indexString = sc.nextLine();
//            System.out.println();
//            
//            for(int i = 0; i < contentGraph.getSize(); i++){
//                if(contentGraph.getVertex(i).equalsIgnoreCase(indexString)){
//                    index = i;
//                }
//            }
//            System.out.println("Confession [" + contentGraph.getVertex(index) + "] : ");
//            System.out.println(arr[index]);
//            System.out.println("=================================================================");
//            System.out.println(">> Please Enter The Confession Post ID You Want To Reply.");
//            System.out.println(">> Leave It Blank If You Don't Want To Reply A Confession Post.");
//            System.out.println("-----------------------------------------------------------------");
//            System.out.println("Reply Confession Post (Capital Sensitive) : ");
//            indexString = sc.nextLine();
//            System.out.println("-----------------------------------------------------------------");
//            
//            if(contentGraph.hasVertex(indexString) == true){
//            
//                System.out.println("Confession Post ID Exists!");
//                
//                System.out.println("=================================================================");
//                System.out.println(" ");
//                System.out.println("=================================================================");
//                System.out.println(">> Please Enter Your Confession Reply Content.");
//                System.out.println(">> Insert \"-1\" To Submit Your Confession.");
//                System.out.println("-----------------------------------------------------------------");
//                System.out.println("Confession Content : ");
//
//                while (true){
//                String replyContent = sc.nextLine();
//                if (replyContent.equals("-1")){
//                    break;
//                }
//                    content2.append(replyContent).append("\n");
//                }
//
//                id = idIncrement();
//                addToList(id, content2.toString());
//                addToFile(id, content2.toString());
//
//                contentGraph.addEdge(id, indexString, content2.toString());
//
//                while (readFile2.hasNextLine()){
//                contentGraph.addVertex(readFile2.nextLine());
//                }
//                while (readFile.hasNextLine()){
//                    content.add(readFile.nextLine());
//                }
//                raw = content.toString().replaceAll(", ", " ").replaceAll("\\[", "").replaceAll("\\]", "");
//                arr = raw.split("  ");
//                
//                System.out.println("=================================================================");
//                System.out.println("");
//                System.out.println("=================================================================");
//                System.out.println("#" + contentGraph.getVertex(contentGraph.getSize()));
//                System.out.println(" ");
//                System.out.println("Reply To #" + contentGraph.getVertex(index));
//                System.out.println(arr[contentGraph.getSize()]);
//            
//            }else{
//                System.out.println("Confession Post ID Not Exists!");
//                System.out.println("=================================================================");
//                System.out.println(" ");
//                System.out.println("=================================================================");
//            }
            
    
    }
}
