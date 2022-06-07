package fileUtil;

import java.io.*;
import java.util.ArrayList;

public class testLoadGraph {
    public static void main(String[] args) {
        FileUtil fileUtil = new FileUtil();
        ArrayList<String> list = readFile();
        for(String element: list)
            System.out.println(element);
    }

    public static void loadGraph(ArrayList<String> ls){

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
