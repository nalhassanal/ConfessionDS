package test.txtFiles;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

import java.io.*;

public class tryWithHashmap {
    public static void main(String[] args) {

        Multimap<String, String> map1 = ArrayListMultimap.create();
        map1.put("DS00001", "DS00002");
        map1.put("DS00001", "DS00003");
        map1.put("DS00001", "DS00004");

        System.out.println(map1.keySet());
        for(String key : map1.keySet()){
            System.out.println(map1.get(key));
        }
        BufferedWriter bw = null;
        System.out.println(addToFile(bw, map1));

//        Multimap<String, String> map = readFromFile();
//        System.out.println(duplicateKeys(map1));
//
//        System.out.println(map.keySet());
//        for(String key : map.keySet()) {
//            System.out.println(map.get(key));
//        }
    }

    public static boolean duplicateKeys(Multimap<String, String> map){
        Multimap<String, String> mapFromFile = readFromFile();
        for(String keyFromFile: mapFromFile.keySet()){
            for (String key: map.keySet()){
                if (key.equals(keyFromFile))
                    return true;
            }
        }
        return false;
    }

    public static boolean addToFile(BufferedWriter bw, Multimap<String, String> map1){
        boolean success = false;
        if (duplicateKeys(map1)){
            System.out.println("There is already a key exist in the file");
            return success;
        }

        // prioritize creating the filewriter object first, if it fails catch IOException
        // if successful continue with try body
        try(FileWriter fw = new FileWriter(".\\dataFiles\\test.txt", true)){
            bw = new BufferedWriter(fw); // creates a buffered writer object
            bw.write(map1.toString()); // writes the content of the given map object
            bw.write("\n"); // adds a new line after for readability
            bw.flush(); // flush the output stream
            success = true;
        } catch (IOException ex){
            ex.printStackTrace();
        }
        finally {
            // always close the writer object
            if (bw != null)
                try {
                    bw.close();
                } catch (IOException ex){ex.printStackTrace();}
        }
        return success;
    }

    public static Multimap<String, String> readFromFile(){
        Multimap<String, String> map = ArrayListMultimap.create();
        String filepath = ".\\dataFiles\\test.txt";
        File file = new File(filepath);
        BufferedReader br = null;
        try (FileReader fr = new FileReader(file)){
            br = new BufferedReader(fr);
            String line = null;
            while ((line = br.readLine()) != null){
                // split line by = sign
                String [] data = line.split("=");

                String key = data[0].trim().replace("{", "");
                String value = data[1].trim().replace("}","");

                if (!key.equals("") && !value.equals(""))
                    map.put(key, value);
            }
        } catch (IOException ex){
            ex.printStackTrace();
        }
        finally {
            if(br != null)
                try {
                    br.close();
                }catch (IOException ex){ex.printStackTrace();}
        }
        return map;
    }

}
