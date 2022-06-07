package main;
import fileUtil.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Scanner;

import dataStructure.*;

public class displayConfession {
    private static final confession conf = new confession();
    private static final FileUtil fileUtil = new FileUtil();
    private static final Scanner input = new Scanner(System.in);

    public static void main(String[] args) {
        displayChoices();
    }

    public static void displayChoices(){
        System.out.println("============================================================"); // 60 = signs
        ArrayList<String> ls = listOptions();

//        ArrayList<String> key = new ArrayList<>(), content = new ArrayList<>();
//        for(int i= 0; i<ls.size(); i++){
//            if (i%2 == 0)
//                key.add(ls.get(i));
//            else
//                content.add(ls.get(i));
//        }
//        System.out.println("We have: " + key.size() + " confessions.");
//        System.out.print("All available ID: " );
//        for (int i = 0; i<key.size(); i++) {
//            if (i > 0 && i%10 ==0)
//                System.out.print("\n\t\t\t\t  ");
//            System.out.print(key.get(i) + " ");
//        }
//        System.out.println("============================================================"); // 60 = signs
//        System.out.println("Please Enter The Id Confession You Want To See : ");
//        String inputID = input.nextLine();
//        System.out.println(content.get(key.indexOf(inputID)));

    }

    public static ArrayList<String> listOptions(){
        HashMap<String, String> map = fileUtil.mapFromFile();
//        System.out.println(map);
        ArrayList<String> keys = new ArrayList<>(map.keySet());
        Collections.sort(keys);
        ArrayList<String> ls = new ArrayList<>();
//        for(String key: keys){
//            ls.add(key);
//            ls.add(map.get(key));
////            System.out.println(key + " " + map.get(key));
//        }

        return ls;
    }

    public static void readFile(){

    }
}
