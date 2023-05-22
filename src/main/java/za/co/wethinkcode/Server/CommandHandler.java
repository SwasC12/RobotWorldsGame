package za.co.wethinkcode.Server;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import za.co.wethinkcode.Server.World.Position;
import za.co.wethinkcode.Server.World.Robot;
import za.co.wethinkcode.Server.World.Status;
import za.co.wethinkcode.Server.World.World;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import static java.lang.String.valueOf;
import static za.co.wethinkcode.Server.World.Status.*;
//import static sun.security.tools.keytool.Main.rb;


public class CommandHandler {


    public static List<Robot> myRobots = new ArrayList<>();
    public static List<Robot> deadRobots = new ArrayList<>();
    public static List<Robot> reloadRobots = new ArrayList<>();
    public static List<Robot> repairRobots = new ArrayList<>();

    LookCommandHandler lookCommandHandler;
    HandleFireCommand handleFireCommand;
    int obstX_target;
    int obstY_target;
    Object reader;

    private int shotDistance;

    public CommandHandler() {
        this.myRobots = new ArrayList<>();
        this.deadRobots = new ArrayList<>();
//        this.reloadRobots = new ArrayList<>();
    }


    //list of valid commands accepted from client
    public static List<String> ListOfClientCommands = new ArrayList<>(List.of("launch", "look", "forward", "back", "turn", "repair", "reload", "fire", "state"));

    static int maximumNumberOfRobots = 20;

    Robot robot;

    //create robot, and respective attributes
    public Robot generateRobot(String robotName, int shieldStrength, int maxShots, int shotDistance){
        int x = Robot.generateXAndY()[0];
        int y = Robot.generateXAndY()[1];
        Robot robot = new Robot("ROBOT", robotName, x, y, "OK", World.lookDistance, World.reloadTime, World.repairTime, shieldStrength, "UP", maxShots, valueOf(NORMAL));
        robot.setRobotName(robotName);
        robot.setRobotName(robotName);
        robot.setRobotShots(maxShots);
        robot.setShields(shieldStrength);
        robot.setShotDistance(shotDistance);
        return robot;
    }


    public  JSONObject CommandCheck(String robotCommand, String robotName, String[] args) throws Exception {
        this.lookCommandHandler = new LookCommandHandler(this);
        this.handleFireCommand = new HandleFireCommand(this);
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
        //find robot passing command in dead robots

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



                if (args.length ==2) {
                    int maxShots = Integer.parseInt(args[1].trim());

                    //configure Gun
                    Gun gun = new Gun(maxShots, robot);

                    maxShots = gun.getNumShots();
                    //from the maximum number of shots I can get a bullet distance
                    shotDistance = gun.getShotDistance();
                    int shieldStrength = World.maxShieldStrength;
                    this.robot = generateRobot(robotName,shieldStrength,maxShots,shotDistance );
                    this.robot.setMaxShield(shieldStrength);
                    this.robot.setMaxShots(maxShots);

                }
                else if (args.length ==1) {
                    JSONParser parser = new JSONParser();
//                    reader = parser.parse(new FileReader("src/main/java/za/co/wethinkcode/Resources/robots.json"));
                    CurrentDirectory currentDirectory = new CurrentDirectory();

//                    reader = parser.parse(new FileReader(currentDirectory.getAbsolutePath("/src/main/java/za/co/wethinkcode/Resources/robots.json")));
                    reader = parser.parse(new FileReader("/home/wtc/student_work/dbn11_robot_worlds/src/main/java/za/co/wethinkcode/Resources/robots.json"));
                    JSONObject data = (JSONObject) reader;
                    JSONArray robots = (JSONArray) data.get("robots");
                    for (Object robotObj : robots) {
                        JSONObject robot = (JSONObject) robotObj;
                        String name = (String) robot.get("kind");
                        if (name.equalsIgnoreCase(args[0])) {
                            int shieldStrength = Integer.parseInt(valueOf(robot.get("max-shield")));
                            int maxShots = Integer.parseInt(valueOf(robot.get("max-shots")));
                            Gun gun = new Gun(maxShots, this.robot);
                            //from the maximum number of shots I can get a bullet distance
                            shotDistance = gun.getShotDistance();

                            this.robot = generateRobot(robotName,shieldStrength,maxShots,shotDistance );
                            this.robot.setMaxShield(shieldStrength);
                            this.robot.setMaxShots(maxShots);
                            break;
                        }
                    }
                }

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

                //Responsefile generated with state of robot
                return writeJsonFileForState("Responsefile", robot);

            } else if (robotCommand.contains("reload")) {
                Reload reload = new Reload();
                robot = myRobots.get(index);
                reload.execute(robot);
                return robotIsReload(robot);

            } else if (robotCommand.contains("repair")) {
                Repair repair = new Repair();
                robot = myRobots.get(index);
                repair.execute(robot);
                return robotIsRepair(robot);

            } else if (robotCommand.equalsIgnoreCase("fire")) {
                    //if I call the fire command, then I need to update the bullet position from the robot gun
//                    BulletPosition bulletPosition =  new BulletPosition(shotDistance);

                    //check the final position of the bullet
                    robot = myRobots.get(index);
                    BulletPosition bulletPosition =  new BulletPosition(robot.getShotDistance(), robot, robot.getRobotX(), robot.getRobotY() );

                    // BulletPosition bulletPosition = new BulletPosition(robot.getShotDistance(), robot);
                    Position shotPosition = new Position(bulletPosition.getXf(), bulletPosition.getYf());
                    Position robPostion = new Position(robot.getRobotX(),robot.getRobotY());

                    if (bulletPosition.getXi()==bulletPosition.getXf()){
                        obstX_target = bulletPosition.getXi();
                    }
                    else if (bulletPosition.getYi()==bulletPosition.getYf()){
                        obstY_target = bulletPosition.getYi();
                    }

                    if (robot.getRobotShots()>0){
                        System.out.println("Bullet initial position: "+ "["+bulletPosition.getXi()+","+bulletPosition.getYi()+"]");
                        System.out.println("Shot travel distance: "+ robot.getShotDistance() +" steps");
                        System.out.println("Bullet final position: "+ "["+bulletPosition.getXf()+","+bulletPosition.getYf()+"]");

                        if ( (!handleFireCommand.shotBlockedPathByObstacle(robPostion,shotPosition) &&
                                handleFireCommand.shotBlockedPathByRobot(robPostion,shotPosition) )
                                ||
                                (!handleFireCommand.shotBlockedPathByObstacle(robPostion,shotPosition) &&
                                        handleFireCommand.shotBlockedPositionByRobot(new Position(bulletPosition.getXf(), bulletPosition.getYf()))
                                )
                        )
                        {
                            System.out.println("I am in Case 1:   MyROBOT >>>> NO OBSTACLE  >>>>>>TargetROBOT");
                            return handleFireCommand.createJSONResponseSuccess(robot);
                        }
                        else if ( ((handleFireCommand.shotBlockedPathByObstacle(robPostion,shotPosition) && handleFireCommand.shotBlockedPathByRobot(robPostion,shotPosition))
                                ||  ( (handleFireCommand.shotBlockedPathByObstacle(robPostion,shotPosition) && handleFireCommand.shotBlockedPositionByRobot(new Position(bulletPosition.getXf(), bulletPosition.getYf()))))
                        ) &&
                                Math.sqrt((obstX_target -robot.getRobotX())*(obstX_target-robot.getRobotX())
                                        + (obstY_target-robot.getRobotY())*(obstY_target-robot.getRobotY()))
                                        >
                                        Math.sqrt((handleFireCommand.getRobotPosition().getX()-robot.getRobotX())*(handleFireCommand.getRobotPosition().getX()-robot.getRobotX())
                                                + (handleFireCommand.getRobotPosition().getY()-robot.getRobotY())*(handleFireCommand.getRobotPosition().getY()-robot.getRobotY())))
                        {
                            System.out.println("I am in Case 2:   MyROBOT >>>>>>> TargetROBOT >>>>>>> OBSTACLE ");
                            return handleFireCommand.createJSONResponseSuccess(robot);
                        }

                        else {
                            System.out.println("I am in Miss Case:  MyROBOT >>>>>> OBSTACLE >>>>>>>> TargetROBOT\n" +
                                    "                 ");
                            return handleFireCommand.createJSONResponseMiss(robot);
                        }
                    }
                    else{
                        return handleFireCommand.createJSONResponseNoShots(robot);
                    }
                }

        }

        return null;
    }


    public static JSONObject writeJsonFile(String filename, Robot rb) throws Exception{
        //robots attributes
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

    public static JSONObject robotIsDEAD(){
        JSONObject jsonRequest = new JSONObject();
        jsonRequest.put("result","OK");
        JSONObject subJson1 = new JSONObject();
        subJson1.put("message","Your is DEAD, cannot execute any command");
        jsonRequest.put("data",subJson1);
        return jsonRequest;
    }

    public static JSONObject writeJsonFileForState(String filename,Robot rb) throws Exception{
        JSONObject fileJson = new JSONObject();
        JSONArray dataArray = new JSONArray();
        JSONObject subJson1 = new JSONObject();
        JSONObject subJson2 = new JSONObject();
        subJson2.put("position","["+rb.getRobotX()+","+rb.getRobotY()+"]");
        subJson2.put("direction",rb.getRobotDirection());
        subJson2.put("shields",rb.getRobotShields());
        subJson2.put("shots",rb.getRobotShots()+" (shotDistance = "+rb.getShotDistance()+" steps)");
        subJson2.put("status",rb.getRobotStatus());
        fileJson.put("state",subJson2);

        return fileJson;

    }
        //RELOADING
    public static JSONObject robotIsReload(Robot robot){
        JSONObject jsonRequest = new JSONObject();
        jsonRequest.put("result","OK");
        JSONObject subJson1 = new JSONObject();
        subJson1.put("message","DONE");
        jsonRequest.put("data",subJson1);
        JSONObject subJson3 = new JSONObject();
        subJson3.put("position","["+robot.getRobotX()+","+robot.getRobotY()+"]");
        subJson3.put("direction",robot.getRobotDirection());
        subJson3.put("status",robot.getRobotStatus());
        jsonRequest.put("state",subJson3);
        return jsonRequest;
    }
    //REPAIRING
    public static JSONObject robotIsRepair(Robot robot) {
        JSONObject jsonRequest = new JSONObject();
        jsonRequest.put("result","OK");
        JSONObject subJson1 = new JSONObject();
        subJson1.put("message","DONE");
        jsonRequest.put("data",subJson1);
        JSONObject subJson3 = new JSONObject();
        subJson3.put("position","["+robot.getRobotX()+","+robot.getRobotY()+"]");
        subJson3.put("direction",robot.getRobotDirection());
        subJson3.put("status",robot.getRobotStatus());
        jsonRequest.put("state",subJson3);
        return jsonRequest;
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
        myRobots.remove(myRobots.get(index));

    }
}
