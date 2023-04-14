package za.co.wethinkcode.Server;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import za.co.wethinkcode.Server.World.Direction;
import za.co.wethinkcode.Server.World.Robot;
import za.co.wethinkcode.Server.World.Status;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import static za.co.wethinkcode.Server.SimpleServer.myRobots;

public class CommandHandler {


    public static List<String> ListOfClientCommands = new ArrayList<>(List.of("launch", "look", "forward", "back", "turn", "repair", "reload", "fire", "state"));

    int maximumNumberOfRobots = 20;




    public  JSONObject CommandCheck(String robotCommand,String robotName) throws Exception {
        if (!ListOfClientCommands.contains(robotCommand)){
            return doesNotSuppCom("Responsefile");
        }
        else if (ListOfClientCommands.contains(robotCommand)) {
            if (robotCommand.contains("launch") && myRobots.size()<maximumNumberOfRobots) {
                myRobots.add(new Robot(robotName, 12, 14, "OK", 10, 5, 8, 6, Direction.NORTH, 6, Status.NORMAL));
                for (int i =0;i<myRobots.size();i++){
                    if(myRobots.get(i).getRobotName().contains(robotName)){
                        return nameAlreadyTaken("Resonsefile");
                    }
                }

                return writeJsonFile("Responsefile");

            }
            else if (robotCommand.contains("launch") && myRobots.size()>=maximumNumberOfRobots){
                return robotIsFull("Responsefile");
            }

        }
        return writeJsonFile("Responsefile");

    }





    public Object readJsonFile(String filename) throws Exception {
//        JSONObject jsonObject = (JSONObject) readJsonSimpleDemo("example.json");
        FileReader reader = new FileReader(filename);
        JSONParser jsonParser = new JSONParser();
        return jsonParser.parse(reader);
    }

    public static JSONObject writeJsonFile(String filename) throws Exception{
        Robot rb = new Robot("HAL",12,14,"OK",10,5,8,6, Direction.NORTH,6, Status.NORMAL);
        JSONObject fileJson = new JSONObject();
        fileJson.put("result",rb.getRobotResults());

        JSONArray dataArray = new JSONArray();
        JSONObject subJson1 = new JSONObject();
        subJson1.put("position","["+rb.getRobotX()+" ,"+rb.getRobotY()+"]");
        subJson1.put("visibility",rb.getRobotVisibility());
        subJson1.put("reload",rb.getRobotReload());
        subJson1.put("repair",rb.getRobotRepair());
        subJson1.put("shields",rb.getRobotShields());
        JSONObject subJson2 = new JSONObject();
        subJson2.put("position","["+rb.getRobotX()+" ,"+rb.getRobotY()+"]");
        subJson2.put("direction",rb.getRobotDirection());
        subJson2.put("shields",rb.getRobotShields());
        subJson2.put("shots",rb.getRobotShots());
        subJson2.put("status",rb.getRobotStatus());
        fileJson.put("data",subJson1);
        fileJson.put("state",subJson2);

        return fileJson;

    }

    //For arguments
    public JSONObject couldNotParseArgJson(String filename){
        JSONObject jsonRequest = new JSONObject();
        jsonRequest.put("result: ","EROR");
        JSONObject subJson1 = new JSONObject();
        subJson1.put("message: ","Could not parse arguments");
        jsonRequest.put("data",subJson1);
        return jsonRequest;
    }
    public JSONObject doesNotSuppCom(String filename){
        JSONObject jsonRequest = new JSONObject();
        jsonRequest.put("result: ","EROR");
        JSONObject subJson1 = new JSONObject();
        subJson1.put("message: ","Unsupported command");
        jsonRequest.put("data",subJson1);
        return jsonRequest;
    }

    //Launching
    public JSONObject nameAlreadyTaken(String filename){
        JSONObject jsonRequest = new JSONObject();
        jsonRequest.put("result: ","EROR");
        JSONObject subJson1 = new JSONObject();
        subJson1.put("message: ","Too many of you in this world");
        jsonRequest.put("data",subJson1);
        return jsonRequest;
    }

    public JSONObject robotIsFull(String filename){
        JSONObject jsonRequest = new JSONObject();
        jsonRequest.put("result: ","EROR");
        JSONObject subJson1 = new JSONObject();
        subJson1.put("message: ","Too many of you in this world");
        jsonRequest.put("data",subJson1);
        return jsonRequest;
    }



}
