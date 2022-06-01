
package main;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.Scanner;

public class Reply {
    public static void main(String[] args) throws FileNotFoundException {
        LinkedList<String> list = new LinkedList<>();
        String filename = "confession.txt";
        
        File fileObj = new File(filename);
            Scanner readFile = new Scanner(new FileInputStream(fileObj));
            while (readFile.hasNextLine()){
                list.add(readFile.nextLine());
            }
            String temp = String.join("-", list);
            String[] content = temp.split(" ");
            System.out.println(temp);
            
            for(int i = 0; i < content.length; i++){
                System.out.print(content[i] + " ");
            }
    }
}
