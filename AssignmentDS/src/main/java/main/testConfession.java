package main;
import java.io.*;
import java.util.*;
public class testConfession {
    static LinkedList<confessionPair> confessionList = new LinkedList<>();
    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);

        String id = "DS00001";
        StringBuilder content = new StringBuilder();

        System.out.println("Please enter your confession content.");
        System.out.println("Insert \"-1\" to submit your confession.");
        System.out.println("Confession content:");
        while (true){
            String insert = input.nextLine();
            if (insert.equals("-1")){
                break;
            }
            content.append(insert).append("\n");
        }
        id = idIncrement();
        addToList(id, content.toString());
        addToFile(id, content.toString());
//        printList();
        System.out.println(confessionList.get(0).toString());

    }

    public static void addToList(String id, String content){
        confessionList.add(new confessionPair(id, content));
    }

    public static void addToFile(String id, String content){
        String filename = "confession.txt";
        String filename2 = "id.txt";
        try {
            File fileObj = new File(filename);
            PrintWriter pw = new PrintWriter(new FileOutputStream(fileObj, true));
            pw.println(content);
            pw.flush();
            pw.close();
            File fileObj2 = new File(filename2);
            PrintWriter pw2 = new PrintWriter(new FileOutputStream(fileObj2, true));
            pw2.println(id);
            pw2.flush();
            pw2.close();
        } catch (IOException ex){
            System.out.println(ex.getMessage());
        }
    }

    public static String idIncrement(){
        String filename = "id.txt";
        String FileID = null;
        try {
            File fileObj = new File(filename);
            Scanner readFile = new Scanner(new FileInputStream(fileObj));
            while (readFile.hasNextLine()){
                FileID = readFile.nextLine();
            }
            readFile.close();
        } catch (FileNotFoundException ex){
            System.out.println(ex.getMessage());
        }
        assert FileID != null;
        return "DS" + (Integer.parseInt(FileID.substring(2)) + 1);
    }

    public static void printList(){
        for (int i = 0; !confessionList.isEmpty() ; i++) {
            System.out.println(confessionList.get(i).toString());
        }
    }
}
