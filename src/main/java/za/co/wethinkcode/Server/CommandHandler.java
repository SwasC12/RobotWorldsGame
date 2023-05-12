package za.co.wethinkcode.Server;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import za.co.wethinkcode.Server.World.Position;
import za.co.wethinkcode.Server.World.Robot;
import za.co.wethinkcode.Server.World.Status;

import java.util.ArrayList;
import java.util.List;

import static java.lang.String.valueOf;


public class CommandHandler {


    public static List<Robot> myRobots = new ArrayList<>();
    public static List<Robot> deadRobots = new ArrayList<>();
    LookCommandHandler lookCommandHandler;
    HandleFireCommand handleFireCommand;

    private int shotDistance;

    public CommandHandler() {
        this.myRobots = new ArrayList<>();
        this.deadRobots = new ArrayList<>();
    }


    //list of valid commands accepted from client
    public static List<String> ListOfClientCommands = new ArrayList<>(List.of("launch", "look", "forward", "back", "turn", "repair", "reload", "fire", "state"));

    static int maximumNumberOfRobots = 20;

    Robot robot;

        //create robot, and respective attributes
        public Robot generateRobot(String robotName, int shieldStrength, int maxShots, int shotDistance){
            int x = Robot.generateXAndY()[0];
            int y = Robot.generateXAndY()[1];
            Robot robot = new Robot("ROBOT", robotName, x, y, "OK", 800, 5, 8, shieldStrength, "NORTH", maxShots, valueOf(Status.NORMAL));
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
//            int index_d = 0;
//
////          find robot passing command in dead robots
//            if (deadRobots.size()>=1) {
//                for (Robot rob: deadRobots){
//                    if (rob.getRobotName().equalsIgnoreCase(robotName)){
//                        break;
//                    }else {
//                        index_d=index_d+1;
//                    }
//                }
//                robot = deadRobots.get(index_d);
//                return writeJsonFile("Responsefile",robot);
//            }

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
//            else if (deadRobots.size()>=1) {
//                for (Robot rob: deadRobots){
//                    if (rob.getRobotName().equalsIgnoreCase(robotName)){
//                        break;
//                    }else {
//                        index=index+1;
//                    }
//                }
//                robot = deadRobots.get(index);
//                return writeJsonFile("Responsefile",robot);
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

                    //args
                    //shots entered by the client
                    int maxShots = Integer.parseInt(args[2].trim());
                    //configure Gun
                    Gun gun = new Gun(maxShots, robot);

                    maxShots = gun.getNumShots();
                    //from the maximum number of shots I can get a bullet distance
                    shotDistance = gun.getShotDistance();
                    int shieldStrength = Integer.parseInt(args[1].trim());

                       this.robot = generateRobot(robotName,shieldStrength ,maxShots,shotDistance );

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
                }

                else if (robotCommand.equalsIgnoreCase("fire")) {
                    //if I call the fire command, then I need to update the bullet position from the robot gun
//                    BulletPosition bulletPosition =  new BulletPosition(shotDistance);

                    //check the final position of the bullet
                    robot = myRobots.get(index);
                    BulletPosition bulletPosition =  new BulletPosition(robot.getShotDistance(), robot, robot.getRobotX(), robot.getRobotY() );

                    // BulletPosition bulletPosition = new BulletPosition(robot.getShotDistance(), robot);
                    Position shotPosition = new Position(bulletPosition.getXf(), bulletPosition.getYf());
                    Position robPostion = new Position(robot.getRobotX(),robot.getRobotY());

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
                                Math.sqrt((handleFireCommand.getObstaclePosition().getX()-robot.getRobotX())*(handleFireCommand.getObstaclePosition().getX()-robot.getRobotX())
                                        + (handleFireCommand.getObstaclePosition().getY()-robot.getRobotY())*(handleFireCommand.getObstaclePosition().getY()-robot.getRobotY()))
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
        subJson2.put("status",rb.getRobotStatus().toString());
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
        //subJson2.put("shots",rb.getRobotShots());
        subJson2.put("shots",rb.getRobotShots()+" (shotDistance = "+rb.getShotDistance()+" steps)");
        subJson2.put("status",rb.getRobotStatus().toString());
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
        myRobots.remove(myRobots.get(index));

    }

}
