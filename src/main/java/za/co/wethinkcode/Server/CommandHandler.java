package za.co.wethinkcode.Server;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import za.co.wethinkcode.Server.World.Robot;

import java.util.ArrayList;
import java.util.List;



public class CommandHandler {


    public static List<Robot> myRobots = new ArrayList<>();
    LookCommandHandler lookCommandHandler;

    public CommandHandler() {
        this.myRobots = new ArrayList<>();
    }



    public static List<String> ListOfClientCommands = new ArrayList<>(List.of("launch", "look", "forward", "back", "turn", "repair", "reload", "fire", "state"));

    static int maximumNumberOfRobots = 20;

    Robot robot;


        public Robot generateRobot(String robotName){
            int x = Robot.generateXAndY()[0];
            int y = Robot.generateXAndY()[1];
            Robot robot = new Robot("ROBOT", robotName, x, y, "OK", 800, 5, 8, 6, "NORTH", 6, "NORMAL");
            robot.setRobotName(robotName);
            return robot;
        }


        public  JSONObject CommandCheck(String robotCommand, String robotName) throws Exception {
            this.lookCommandHandler = new LookCommandHandler(this);
            int index = 0;

            //find the robot passing the command !!
            if (myRobots.size() >= 1){

                for (Robot rob: myRobots){
                    if (rob.getRobotName().equalsIgnoreCase(robotName)){
                        break;
                    }else {
                        index=index+1;
                    }
                }
            }

//            if (this.myRobots.size() == 0){
//                addToList(new Robot("ROBOT", "Khetha", 10, 0, "OK", 10, 5, 8, 6, "NORTH", 6, "NORMAL"));
//            }

            if (!ListOfClientCommands.contains(robotCommand)) {
                return doesNotSuppCom("Responsefile");
            }
            else{
                if (robotCommand.contains("launch") && this.myRobots.size() < maximumNumberOfRobots) {
                        for (int i = 0; i < myRobots.size(); i++) {
                            if (myRobots.get(i).getRobotName().contains(robotName)) {
                                return nameAlreadyTaken("Responsefile");
                            }
                        }

                        this.robot = generateRobot(robotName);
                        addToList(this.robot);
                        return writeJsonFile("Responsefile", robot);

                } else if (robotCommand.contains("launch") && myRobots.size() >= maximumNumberOfRobots) {
                    return robotIsFull("Responsefile");
                } else if (robotCommand.contains("look")) {
                    robot = myRobots.get(index);
//                    System.out.println(robot.getRobotName());
                    return lookCommandHandler.writeJsonFileForLook("Responsefile", robot);
                } else if (robotCommand.contains("state")) {
                    robot = myRobots.get(index);
                    return writeJsonFileForState("Responsefile", robot);
                }
            }

            return null;
        }


    public static JSONObject writeJsonFile(String filename, Robot rb) throws Exception{

        JSONObject fileJson = new JSONObject();
        fileJson.put("result",rb.getRobotResults());


        JSONObject subJson1 = new JSONObject();
        subJson1.put("position","["+rb.getRobotX()+","+rb.getRobotY()+"]");
        subJson1.put("visibility",rb.getRobotVisibility());
        subJson1.put("reload",rb.getRobotReload());
        subJson1.put("repair",rb.getRobotRepair());
        subJson1.put("shields",rb.getRobotShields());
        JSONObject subJson2 = new JSONObject();
        subJson2.put("position","["+rb.getRobotX()+","+rb.getRobotY()+"]");
        subJson2.put("direction",rb.getRobotDirection());
        subJson2.put("shields",rb.getRobotShields());
        subJson2.put("shots",rb.getRobotShots());
        subJson2.put("status",rb.getRobotStatus());
        fileJson.put("data",subJson1);
        fileJson.put("state",subJson2);

        return fileJson;

    }

    //For arguments
    public static JSONObject couldNotParseArgJson(String filename){
        JSONObject jsonRequest = new JSONObject();
        jsonRequest.put("result","ERROR");
        JSONObject subJson1 = new JSONObject();
        subJson1.put("message","Could not parse arguments");
        jsonRequest.put("data",subJson1);
        return jsonRequest;
    }
    public static JSONObject doesNotSuppCom(String filename){
        JSONObject jsonRequest = new JSONObject();
        jsonRequest.put("result","ERROR");
        JSONObject subJson1 = new JSONObject();
        subJson1.put("message","Unsupported command");
        jsonRequest.put("data",subJson1);
        return jsonRequest;
    }

    //Launching
    public static JSONObject nameAlreadyTaken(String filename){
        JSONObject jsonRequest = new JSONObject();
        jsonRequest.put("result","ERROR");
        JSONObject subJson1 = new JSONObject();
        subJson1.put("message","Too many of you in this world");
        jsonRequest.put("data",subJson1);
        return jsonRequest;
    }

    public static JSONObject robotIsFull(String filename){
        JSONObject jsonRequest = new JSONObject();
        jsonRequest.put("result","ERROR");
        JSONObject subJson1 = new JSONObject();
        subJson1.put("message","No more space in this world");
        jsonRequest.put("data",subJson1);
        return jsonRequest;
    }

    public static JSONObject writeJsonFileForState(String filename,Robot rb) throws Exception{
//        Robot rb = new Robot("ROBOT","HAL",12,14,"OK",10,5,8,6, "NORTH",6, "NORMAL");
        JSONObject fileJson = new JSONObject();
        JSONArray dataArray = new JSONArray();
        JSONObject subJson1 = new JSONObject();
        JSONObject subJson2 = new JSONObject();
        subJson2.put("position","["+rb.getRobotX()+","+rb.getRobotY()+"]");
        subJson2.put("direction",rb.getRobotDirection());
        subJson2.put("shields",rb.getRobotShields());
        subJson2.put("shots",rb.getRobotShots());
        subJson2.put("status",rb.getRobotStatus());
        fileJson.put("state",subJson2);

        return fileJson;

    }

    public void addToList(Robot rb){
            this.myRobots.add(rb);
    }

    public List<Robot> getRobots(){
            return myRobots;
    }

    public void removeRobot(Robot rb) {
        int index = 0;
        for (Robot rob: myRobots){
            if (rb.getRobotName().equalsIgnoreCase(myRobots.get(index).getRobotName())){
                break;
            }else {
                index=index+1;
            }
        }
//        System.out.println(myRobots.size());
        myRobots.remove(myRobots.get(index));
//        System.out.println(myRobots.size());
    }

}
