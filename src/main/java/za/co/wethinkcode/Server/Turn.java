package za.co.wethinkcode.Server;

import org.json.simple.JSONObject;
import za.co.wethinkcode.Server.World.Robot;

import static za.co.wethinkcode.Server.CommandHandler.myRobots;

public class Turn {


    public static JSONObject execute( String str, String robotName) {
        int index = 0;
        if (myRobots.size() >= 1){

            for (Robot rob: myRobots){
                if (rob.getRobotName().equalsIgnoreCase(robotName)){
                    break;
                }else {
                    index=index+1;
                }
            }
        }
        JSONObject fileJson = new JSONObject();

        fileJson.put("result", myRobots.get(index).getRobotResults());


        JSONObject subJson1 = new JSONObject();
        subJson1.put("message", "Done");
        JSONObject subJson2 = new JSONObject();

        if (str.contains("right")) {
            myRobots.get(index).updateDirection(true);
            subJson2.put("direction", myRobots.get(index).getRobotDirection());
        }else {
            myRobots.get(index).updateDirection(false);
            subJson2.put("direction", myRobots.get(index).getRobotDirection());
        }

        subJson2.put("position", "[" + myRobots.get(index).getRobotX() + "," + myRobots.get(index).getRobotY() + "]");

        subJson2.put("shields", myRobots.get(index).getRobotShields());
        subJson2.put("shots", myRobots.get(index).getRobotShots());
        subJson2.put("status", myRobots.get(index).getRobotStatus().toString());
        fileJson.put("data", subJson1);
        fileJson.put("state", subJson2);

        return fileJson;

    }

}
