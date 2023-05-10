package za.co.wethinkcode.Server;

import org.json.simple.JSONObject;
import za.co.wethinkcode.Server.World.Robot;

import za.co.wethinkcode.Server.World.UpdateResponse;

import static za.co.wethinkcode.Server.CommandHandler.myRobots;

public class Back {

    public  CommandHandler commandHandler;
    public Back(CommandHandler commandHandler){
        this.commandHandler = commandHandler;
    }
    public static JSONObject execute(int nrSteps, String robotName) {
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
        if (myRobots.get(index).updatePosition(-nrSteps).equals(UpdateResponse.Done)) {
            fileJson.put("result", myRobots.get(index).getRobotResults());

            JSONObject subJson1 = new JSONObject();
            subJson1.put("message", "Done");

            JSONObject subJson2 = new JSONObject();
            subJson2.put("position", "[" + myRobots.get(index).getRobotX() + "," + myRobots.get(index).getRobotY() + "]");
            subJson2.put("direction", myRobots.get(index).getRobotDirection().toString());
            subJson2.put("shields", myRobots.get(index).getRobotShields());
            subJson2.put("shots", myRobots.get(index).getRobotShots());
            subJson2.put("status", myRobots.get(index).getRobotStatus().toString());
            fileJson.put("data", subJson1);
            fileJson.put("state", subJson2);
            return fileJson;

        }else if ((myRobots.get(index).updatePosition(nrSteps).equals(UpdateResponse.Obstructed))){
            fileJson.put("result",myRobots.get(index).getRobotResults().toString());

            JSONObject subJson3 = new JSONObject();
            subJson3.put("message","Obstructed");

            JSONObject subJson4 = new JSONObject();
            subJson4.put("position","["+myRobots.get(index).getRobotX()+","+myRobots.get(index).getRobotY()+"]");
            fileJson.put("data",subJson3);
            fileJson.put("state",subJson4);
        }
        return fileJson;
    }
}


