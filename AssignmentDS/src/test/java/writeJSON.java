import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonWriter;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.FileWriter;
import java.io.IOException;

public class writeJSON {
    public static void main(String[] args) {
        JSONObject obj1 = new JSONObject();
        obj1.put("first" , 22);
        obj1.put("second", 33);
        obj1.put("third", 11);

        JSONObject obj2 = new JSONObject();
        obj2.put("first", 11);
        obj2.put("second", 22);
        obj2.put("third", 33);
        obj2.put("fourth", 44);

        JSONObject ob = new JSONObject();
        ob.put("object 1", obj1);

        JSONObject ob2 = new JSONObject();
        ob2.put("object 2", obj2);

        JSONArray arr = new JSONArray();
        arr.add(ob);
        arr.add(ob2);

        System.out.println(arr);
        Gson gson = new Gson();

        try(FileWriter file = new FileWriter(".\\JSONFiles\\test.json", false)){
//            file.write(arr.toString());
//            file.flush();
            JsonWriter jw = new JsonWriter(file);
//            gson.toJson(arr, jw);
            file.flush();
            file.close();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

}
