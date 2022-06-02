import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;

public class readJSON {
    public static void main(String[] args) {
        JSONParser jsonParser = new JSONParser();
        try {
            FileReader reader = new FileReader(".\\JSONFiles\\employee.json");
            Object obj = jsonParser.parse(reader);
            JSONObject employeeObj = (JSONObject) obj;

            String firstName = (String) employeeObj.get("firstName");
            String lastName = (String) employeeObj.get("lastName");

//            System.out.println(firstName + " " + lastName);

            JSONArray employeeArray = (JSONArray) employeeObj.get("address");
            for (Object o : employeeArray) {
                System.out.println(o);
            }
//            for (int i = 0; i < employeeArray.size(); i++){
//                JSONObject address = (JSONObject)employeeArray.get(i);
//                System.out.println(address.toString());
//                String street = (String) address.get("street");
//                String city = (String) address.get("city");
//                String state = (String) address.get("state");
//                System.out.println("Address " + i + " is...");
//                System.out.println("Street: " + street + "\nstate: " + state + "\ncity: " + city);
//
//            }

        } catch (IOException | ParseException ex){
            System.out.println(ex.getMessage());
        }
    }
}
