package fileUtil;

import main.confessionPair;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Queue;

public class testLoadGraph {
    public static void main(String[] args) {
        FileUtil fileUtil = new FileUtil();
        ArrayList<String> list = readFile();
        int count =0;
//        for(String element: list) {
//            System.out.println(count+ " "+element);
//            count++;
//        }
//        loadGraph(list);
//        confessionPair[]
        readList(list);
    }

    public static void readList(ArrayList<String> ls){

        ArrayList<String> key = new ArrayList<>(), content = new ArrayList<>(), date = new ArrayList<>();
        String [] data;
        for (String element : ls){
            data = element.split("=");
            key.add(data[0].replace("{","").trim()); // id
            content.add(data[1].replace("}","").trim()); // nama file
            date.add(data[2].trim()); // date
        }
        readFile(content.get(5));
    }

    public static void readFile(String path){
        String filepath = ".\\dataFiles\\contentFiles\\" + path;
        File file = new File(filepath);
        BufferedReader br = null;
        String line;
        try(FileReader fr = new FileReader(file)){
            br = new BufferedReader(fr);
            while ((line = br.readLine()) != null){
                System.out.println(line);
            }
        }catch (IOException ex){
            ex.printStackTrace();
        }finally {
            if (br != null)
                try {
                    br.close();
                }catch (IOException ex){ex.printStackTrace();}
        }

    }

    public static void loadGraph(ArrayList<String> ls){
        Queue<String> q = new LinkedList<String>(ls);
        String id = "";
        ArrayList<String> content = new ArrayList<>();
        StringBuilder sb = new StringBuilder();

        // currentCount should correspond to id number
        // count is to keep track of number of lines
        int count = 0,currentCount = 0;
        while(!q.isEmpty()){
            String line = q.poll();
            if(!line.startsWith("}")) {
                count++;
                // this if block is to get the content and the id
                if (line.startsWith("{") && line.contains("=")) {
                    currentCount++;
                    line = line.replace("{", "");
                    id = line.substring(0, line.indexOf("="));
                    content.add(line.substring(line.indexOf("=") + 1));
//                    sb.append(line.substring(line.indexOf("=") + 1));
                    System.out.println(id);
                }
                else {
                    content.add(line);
                    if(line.isBlank())
                        content.add(String.valueOf(currentCount));
//                    sb.append(line);
                }
                for(String element: content){
                    if (isNumber(element)){
                        sb.append("\n");
                    }
                    sb.append(element);
                }
                content.clear();
//                System.out.println(count +", " + currentCount);
//                System.out.println(sb);
            }
            else {
                // this block should read the timestamp
                line = line.replace("}","");
                line = line.replace("=", "");
//                System.out.println(line);
            }
            if (sb.lastIndexOf("\\d") > 0){
                System.out.println("here");

            }
            System.out.println(currentCount);
//            System.out.println(sb.lastIndexOf("\\d"));
            System.out.println(sb.indexOf("\\d"));
            System.out.println(sb);
            if (count > 3)
                break;

//            if (!line.isBlank())
//                System.out.println("here");
//            sb = new StringBuilder();
        }
    }

    public static boolean isNumber(String input){
        if(input.isBlank())
            return false;
        return Character.isDigit(input.charAt(0));
    }

    public static ArrayList<String> readFile(){
        ArrayList<String> ls = new ArrayList<>();
        String filepath = ".\\dataFiles\\confession.txt";
        File file = new File(filepath);
        BufferedReader br = null;
        try(FileReader fr = new FileReader(file)){
            br = new BufferedReader(fr);
            String line;
            while ((line = br.readLine()) != null){
                if (line.isBlank())
                    continue;
                ls.add(line);
            }
        }catch (IOException ex){ex.printStackTrace();}
        finally {
            if (br != null){
                try {
                    br.close();
                }catch (IOException ex){ex.printStackTrace();}
            }
        }

        return ls;
    }
}
