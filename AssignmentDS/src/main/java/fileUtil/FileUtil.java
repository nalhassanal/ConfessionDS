package fileUtil;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

import java.io.*;
import java.util.HashMap;

public class FileUtil {

    // used to store reply list
    // maybe buat like pass in "reply.txt" as argument untuk path tu so that reply ada satu text file
    public boolean addToFile(Multimap<String, String> map1, String path){
        BufferedWriter bw = null;

        if(!path.contains(".txt"))
            return false;
        if (hasDuplicateKeys(map1))
            return false;
        String filepath = ".\\dataFiles\\" + path;
        boolean success = false;
        File file = new File(filepath);
        if (!file.exists()) {
            try {
                file.createNewFile();
            }catch (IOException e){
                e.printStackTrace();
            }
        }

        // prioritize creating the fileWriter object first, if it fails catch IOException
        // if successful continue with try body
        try(FileWriter fw = new FileWriter(file, true)){
            bw = new BufferedWriter(fw); // creates a buffered writer object
            bw.newLine(); // adds a new line after for readability
            bw.write(map1.toString() + System.lineSeparator()); // writes the content of the given map object
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

    // used for confessions
    public boolean addToFile(HashMap<String, String> map,String date, String path){
        BufferedWriter bw = null;
        boolean success = false;
        if(!path.contains(".txt"))
            return false;

        // to make sure each id is unique
        for(String keys : map.keySet()){
            if (hasDuplicateKeys(keys))
                return false;
        }
        String filepath = ".\\dataFiles\\" + path;
        File file = new File(filepath);
        if (!file.exists()) {
            try {
                file.createNewFile();
            }catch (IOException e){
                e.printStackTrace();
            }
        }

        // prioritize creating the fileWriter object first, if it fails catch IOException
        // if successful continue with try body
        try(FileWriter fw = new FileWriter(file, true)){
            bw = new BufferedWriter(fw); // creates a buffered writer object
            bw.newLine(); // adds a new line after for readability
            bw.write(map+ "=" + date + System.lineSeparator()); // writes the content of the given map object
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

    // used for reply
    public Multimap<String, String> readFromFile(){
        Multimap<String, String> map = ArrayListMultimap.create(); // creates a Multimap object for return
        // IMPORTANT SET UP LOCATION FIRST
        String filepath = ".\\dataFiles\\confession.txt"; // specify filepath IMPORTANT
        File file = new File(filepath);
        BufferedReader br = null;
        // try creating a fileReader object first, if it succeeds continue with block, else continue to catch
        try (FileReader fr = new FileReader(file)){
            br = new BufferedReader(fr); // create a bufferedReader object
            String line;
            while ((line = br.readLine()) != null){ // goes through each line in the text file
                // split line by = sign
                String [] data = line.split("=");

                // get the individual components such as the key and value
                // also removes other unneeded character such as '{' and '}'
                String key = data[0].trim().replace("{", "");
                String value = data[1].trim().replace("}","");

                // checks if the key and value pairs are not empty
                // will put the key value pair in the map if and only if true
                if (!key.equals("") && !value.equals(""))
                    map.put(key, value);
            }
        } catch (IOException ex){
            ex.printStackTrace();
        }
        finally {
            // always closes the inputStream
            if(br != null)
                try {
                    br.close();
                }catch (IOException ex){ex.printStackTrace();}
        }
        return map;
    }

    // used to store confessions
    // returns all id, content in one hashmap
    public HashMap<String, String> mapFromFile(){
        HashMap<String, String> map = new HashMap<>();
        String filepath = ".\\dataFiles\\confession.txt";
        File file = new File(filepath);
        BufferedReader br = null;

        try(FileReader fr = new FileReader(file)){
            br = new BufferedReader(fr);
            String line;
            while ((line = br.readLine()) != null){
                if (line.contains("}"))
                    continue;
                String [] data = line.split("=");
                String key, value;
                if (data[0].startsWith("{"))
                    key = data[0].trim().replace("{", "");
                else
                    key = "";
                if (data.length <=1){
                    value = "\n";
                }
                else
                    value = data[1].trim();

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

    // used for reply kot
    public boolean hasDuplicateKeys(Multimap<String, String> map){
        Multimap<String, String> mapFromFile = readFromFile();
        if (mapFromFile == null)
            return true;
        for(String keyFromFile: mapFromFile.keySet()){
            for (String key: map.keySet()){
                if (key.equals(keyFromFile))
                    return false;
            }
        }
        return true;
    }

    // used for confessions
    public boolean hasDuplicateKeys(String id){
        HashMap<String, String> mapFromFile = mapFromFile();
        return mapFromFile.containsKey(id);
    }
}
