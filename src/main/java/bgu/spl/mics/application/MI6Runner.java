package bgu.spl.mics.application;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/** This is the Main class of the application. You should parse the input file,
 * create the different instances of the objects, and run the system.
 * In the end, you should output serialized objects.
 */
public class MI6Runner {
    public static void main(String[] args) {
        JSONParser parser = new JSONParser();

        try (Reader reader = new FileReader("input201[3].json")) {

            JSONObject jsonObject = (JSONObject) parser.parse(reader);
            System.out.println(jsonObject);
//            String name = (String) jsonObject.get("name");
//            System.out.println(name);
//
//            long age = (Long) jsonObject.get("age");
//            System.out.println(age);

            List<String> gadgets= new LinkedList<String>();
            // loop array
            JSONArray msg = (JSONArray) jsonObject.get("Inventory");
            Iterator<String> iterator = msg.iterator();
            while (iterator.hasNext()) {
                gadgets.add(iterator.next());
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }
}
