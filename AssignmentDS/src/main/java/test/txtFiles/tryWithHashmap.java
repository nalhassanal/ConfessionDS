package test.txtFiles;

import main.confessionPair;

import java.io.*;
import java.util.HashMap;
import java.util.Scanner;

public class tryWithHashmap {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        HashMap<String, String> map = new HashMap<>();
        String id = "DS00002";
        StringBuilder content = new StringBuilder();
        while (true){
            String insert = input.nextLine();
            if (insert.equals("-1"))
                break;
            content.append(insert).append("\n");
        }
        confessionPair confess = new confessionPair(id, content.toString());
        map.put(id, content.toString());
        if (addToFile(map))
            System.out.println("successful");
        System.out.println("lol");
    }

    public static boolean addToFile(HashMap<String, String> map){
        HashMap<String, String> mapFromFile = mapFromFile();
        for (String key: map.keySet()){
            if (mapFromFile.containsKey(key))
                return false;
        }
        boolean success = false;

        String filepath = ".\\dataFiles\\test.txt";
        File file = new File(filepath);
        BufferedWriter bw = null;
        try(FileWriter fw = new FileWriter(file, true)){
            bw = new BufferedWriter(fw);
            bw.write(map.toString());
            bw.write("\n");
            bw.flush();
            success = true;
        }catch (IOException ex){ex.printStackTrace();}
        finally {
            if (bw != null)
                try {
                    bw.close();
                } catch (IOException ex){ex.printStackTrace();}
        }
        return success;
    }


    public static HashMap<String, String> mapFromFile(){
        HashMap<String, String> map = new HashMap<>();
        String filepath = ".\\dataFiles\\test.txt";
        File file = new File(filepath);
        BufferedReader br = null;

        try(FileReader fr = new FileReader(file)){
            br = new BufferedReader(fr);
            String line = null;
            while ((line = br.readLine()) != null){
                if (line.contains("}"))
                    line = line.replace("}", "");

                String [] data = line.split("=");

                String key = data[0].trim().replace("{", "");
                String value = data[1].trim();

                if (!key.equals("") && !value.equals(""))
                    map.put(key, value);
            }
        }catch (IOException e){e.printStackTrace();}
        finally {
            if (br != null)
                try {
                    br.close();
                }catch (IOException ex){ex.printStackTrace();}
        }

        return map;
    }


}
