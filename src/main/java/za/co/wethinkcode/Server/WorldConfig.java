package za.co.wethinkcode.Server;
import java.io.*;
import java.util.Properties;
import org.json.simple.*;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class WorldConfig{
    //configuration file for world
    public void ReadConfigFile(){
        JSONParser parser = new JSONParser();
        try{
            FileReader reader = new FileReader("src/main/java/za/co/wethinkcode/Server/config.json");
            JSONObject data = (JSONObject) parser.parse(reader);

            int height = Integer.parseInt(data.get("height").toString());
            int width = Integer.parseInt(data.get("width").toString());

            reader.close();
        }catch (IOException | ParseException e){
            e.printStackTrace();
        }

    }
}