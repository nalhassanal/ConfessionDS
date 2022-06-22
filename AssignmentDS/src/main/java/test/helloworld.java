package test;

import java.util.Arrays;
import java.util.LinkedList;

public class helloworld {
    public static void main(String[] args) {
        String word = "hi hi\nthis\nis\nreally is really\nlong";
        String [] after = word.split("[\n\s]");
        LinkedList<String> ls = new LinkedList<>(Arrays.asList(after));
//        System.out.println(after[0]);
        for(int i =0; i< after.length; i++)
            System.out.println(i + " " +after[i]);
//        for(String e: after)
//            System.out.println(e);
//        System.out.println(word);
    }
}
