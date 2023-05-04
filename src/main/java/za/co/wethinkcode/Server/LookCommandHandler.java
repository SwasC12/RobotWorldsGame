package za.co.wethinkcode.Server;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import za.co.wethinkcode.Server.World.*;

import java.util.ArrayList;
import java.util.List;


public class LookCommandHandler {

    public  CommandHandler commandHandler;
    public static List <Obstacles> northObstacles;
    public static List <Obstacles> eastObstacles;
    public static List <Obstacles> southObstacles ;
    public static List <Obstacles> westObstacles;

    public static List <Robot> northRobots;
    public static List <Robot> eastRobots;
    public static List <Robot> southRobots;
    public static List <Robot> westRobots;

    public static List <Edge> northEdge;
    public static List <Edge> eastEdge;
    public static List <Edge> southEdge;
    public static List <Edge> westEdge;



    public LookCommandHandler(CommandHandler commandHandler){
        this.commandHandler = commandHandler;
    }
    public JSONObject writeJsonFileForLook(String filename, Robot rb) throws Exception{
        northObstacles = new ArrayList<>();
        eastObstacles = new ArrayList<>();
        southObstacles = new ArrayList<>();
        westObstacles = new ArrayList<>();

        northRobots = new ArrayList<>();
        eastRobots = new ArrayList<>();
        southRobots = new ArrayList<>();
        westRobots = new ArrayList<>();

        northEdge = new ArrayList<>();
        eastEdge = new ArrayList<>();
        southEdge = new ArrayList<>();
        westEdge = new ArrayList<>();

        World.Top_Left_Boundary();
        World.Bottom_Right_Boundary();
//        commandHandler.myRobots.remove(rb);

        JSONObject fileJson = new JSONObject();

        if (commandHandler.myRobots.size()>0){
            for (int i = 0; i<commandHandler.myRobots.size();i++){
                fileJson.put("result",commandHandler.myRobots.get(i).getRobotResults());
            }
        }




        JSONArray dataArray = new JSONArray();
        JSONObject subJson3 = new JSONObject();
        JSONObject subJson4 = new JSONObject();
        JSONObject subJson5 = new JSONObject();
        JSONObject subJson6 = new JSONObject();





        for (int i = 0;i<4;i++){
            if (i == 0){
//                System.out.println(World.lookDistance);
                Position destination = new Position(rb.getRobotX(),rb.getRobotY()+ World.lookDistance);


                //NORTH EDGE
                edgeBlocksPath(rb, destination);
//                if (northEdge.size()== 1) {
                subJson3.put("direction", "NORTH");
                subJson3.put("type", "EDGE");
                subJson3.put("distance", World.Top_Left[1] - rb.getRobotY());
                dataArray.add(subJson3);
//                }

                //NORTH OBSTACLES
                LookCommandHandler.obstacleBlocksPath(rb, destination);
                JSONObject [] subJson7 = new JSONObject[northObstacles.size()];
                for (int j = 0;j <northObstacles.size();j++){
                    subJson7[j] = new  JSONObject();
                    subJson7[j].put("direction","NORTH");
                    subJson7[j].put("type","OBSTACLE");
                    subJson7[j].put("distance", northObstacles.get(j).getY()- rb.getRobotY());
                    dataArray.add(subJson7[j]);

                }

                //NORTH ROBOTS
                robotBlocksPath(rb, destination);
                JSONObject [] subJson11 = new JSONObject[northRobots.size()];
                for (int j = 0;j <northRobots.size();j++){
                    subJson11[j] = new  JSONObject();
                    subJson11[j].put("direction","NORTH");
                    subJson11[j].put("type","ROBOT");
                    subJson11[j].put("distance", northRobots.get(j).getRobotY()- rb.getRobotY());
                    dataArray.add(subJson11[j]);
                }

            }
            else if (i == 1){
                Position destination = new Position(World.lookDistance+ rb.getRobotX(),rb.getRobotY());
//                JSONObject [] subJson8 = new JSONObject[eastObstacles.size()];
                //EAST EDGE
                edgeBlocksPath(rb, destination);
//                if (eastEdge.size()== 1) {
                subJson4.put("direction", "EAST");
                subJson4.put("type", "EDGE");
                subJson4.put("distance", World.Bottom_Right[0] - rb.getRobotX());
                dataArray.add(subJson4);
//                }

                //EAST OBSTACLES
                LookCommandHandler.obstacleBlocksPath(rb, destination);
                JSONObject [] subJson9 = new JSONObject[eastObstacles.size()];
                for (int j = 0;j < eastObstacles.size();j++){
                    subJson9[j] = new  JSONObject();
                    subJson9[j].put("direction","EAST");
                    subJson9[j].put("type","OBSTACLE");
                    subJson9[j].put("distance",eastObstacles.get(j).getX() - rb.getRobotX());
                    dataArray.add(subJson9[j]);

                }

                //EAST ROBOTS
                robotBlocksPath(rb,destination);
                JSONObject [] subJson12 = new JSONObject[eastRobots.size()];
                for (int j = 0;j < eastRobots.size();j++){
                    subJson12[j] = new  JSONObject();
                    subJson12[j].put("direction","EAST");
                    subJson12[j].put("type","ROBOT");
                    subJson12[j].put("distance", eastRobots.get(j).getRobotX() - rb.getRobotX());
                    dataArray.add(subJson12[j]);
                }
            }
            else if (i == 2){
                Position destination = new Position(rb.getRobotX(), -World.lookDistance + rb.getRobotY());


                //SOUTH EDGE
                edgeBlocksPath(rb, destination);
//                if (southEdge.size()== 1) {
                subJson5.put("direction", "SOUTH");
                subJson5.put("type", "EDGE");
                subJson5.put("distance", -World.Bottom_Right[1] + rb.getRobotY());
                dataArray.add(subJson5);
//                }

                //SOUTH OBSTACLES
                LookCommandHandler.obstacleBlocksPath(rb, destination);
                JSONObject [] subJson10 = new JSONObject[southObstacles.size()];
                for (int j = 0;j <southObstacles.size();j++){
                    subJson10[j] = new  JSONObject();
                    subJson10[j].put("direction","SOUTH");
                    subJson10[j].put("type","OBSTACLE");
                    subJson10[j].put("distance", rb.getRobotY() - southObstacles.get(j).getY());
                    dataArray.add(subJson10[j]);

                }

                //SOUTH ROBOTS
                robotBlocksPath(rb,destination);
                JSONObject [] subJson13 = new JSONObject[southRobots.size()];
                for (int j = 0;j <southRobots.size();j++){
                    subJson13[j] = new  JSONObject();
                    subJson13[j].put("direction","SOUTH");
                    subJson13[j].put("type","ROBOT");
                    subJson13[j].put("distance", rb.getRobotY() - southRobots.get(j).getRobotY());
                    dataArray.add(subJson13[j]);
                }


            }
            else if (i == 3){
                Position destination = new Position(-World.lookDistance + rb.getRobotX(),rb.getRobotY());

                //WEST EDGE
                edgeBlocksPath(rb, destination);
//                if (westEdge.size()== 1) {
                subJson6.put("direction", "WEST");
                subJson6.put("type", "EDGE");
                subJson6.put("distance", -World.Top_Left[0] + rb.getRobotX());

                dataArray.add(subJson6);
//                }

                //WEST OBSTACLES
                LookCommandHandler.obstacleBlocksPath(rb, destination);
                JSONObject [] subJson11 = new JSONObject[westObstacles.size()];
                for (int j = 0;j <westObstacles.size();j++){
                    subJson11[j] = new  JSONObject();
                    subJson11[j].put("direction","WEST");
                    subJson11[j].put("type","OBSTACLE");
                    subJson11[j].put("distance", rb.getRobotX()- westObstacles.get(j).getX());
                    dataArray.add(subJson11[j]);

                }

                //WEST ROBOTS
                robotBlocksPath(rb,destination);
                JSONObject [] subJson14 = new JSONObject[westRobots.size()];
                for (int j = 0;j <westRobots.size();j++){
                    subJson14[j] = new  JSONObject();
                    subJson14[j].put("direction","WEST");
                    subJson14[j].put("type","ROBOT");
                    subJson14[j].put("distance", rb.getRobotX()- westRobots.get(j).getRobotX());
                    dataArray.add(subJson14[j]);
                }


            }
        }

        JSONObject subJson2 = new JSONObject();
        subJson2.put("position","["+rb.getRobotX()+","+rb.getRobotY()+"]");
        subJson2.put("direction",rb.getRobotDirection());
        subJson2.put("shields",rb.getRobotShields());
        subJson2.put("shots",rb.getRobotShots());
        subJson2.put("status",rb.getRobotStatus());
        fileJson.put("result",rb.getRobotResults());
        fileJson.put("objects",dataArray);
        fileJson.put("state",subJson2);

//        commandHandler.myRobots.add(rb);
        return fileJson;

    }

    /*takes  Robot object and  Position object as parameters.
     loops through a list of Obstacles, and checks if each obstacle
     intersects the path between the Robot's current position and the given Position. */
    public static void obstacleBlocksPath(Robot a, Position b) {
        for (Obstacles obs : World.ListOfObstacles) {

            if (a.getRobotY() == b.getY()) {
                // West to east
                if (0 <= -obs.getY() + b.getY() && -obs.getY() + b.getY() <= 4 && obs.getX() - a.getRobotX() > 0 && obs.getX() - b.getX() <= 0) {
                    eastObstacles.add(obs);

                }
                // East to west
                else if (0 <= -obs.getY() + b.getY() && -obs.getY() + b.getY() <= 4 && obs.getX() - a.getRobotX() < 0 && obs.getX() + 4 - b.getX() <= 0) {
                    westObstacles.add(obs);
                }
            }
            else if (a.getRobotX() == b.getX()) {
                // North to south
                if (0 <= -obs.getX() + b.getX() && -obs.getX() + b.getX() <= 4 && obs.getY() - a.getRobotY() < 0 && obs.getY() + 4 - b.getY() >= 0) {
                    southObstacles.add(obs);
                }
                // South to north
                else if (0 <= -obs.getX() + b.getX() && -obs.getX() + b.getX() <= 4 && obs.getY() - a.getRobotY() > 0 && obs.getY() - b.getY() <= 0) {
                    northObstacles.add(obs);
                }
            }
        }
    }


    public  void robotBlocksPath(Robot a, Position b) {

        for (Robot rb : commandHandler.myRobots) {
            if (rb.getRobotName().equalsIgnoreCase(a.getRobotName())){

                continue;
            }

            if (a.getRobotY() == b.getY()) {
                // West to east
                if (rb.getRobotY() == b.getY() && rb.getRobotX() - a.getRobotX() > 0 && rb.getRobotX() - b.getX() <= 0) {
                    eastRobots.add(rb);
                }
                // East to west
                else if (-rb.getRobotY() + b.getY() == 0 && rb.getRobotX() - a.getRobotX() < 0 && rb.getRobotX()  - b.getX() >= 0) {
                    westRobots.add(rb);
                }
            }
            else if (a.getRobotX() == b.getX()) {
                // North to south
                if (-rb.getRobotX() + b.getX() == 0 && rb.getRobotY() - a.getRobotY() < 0 && rb.getRobotY()  - b.getY() >= 0) {
                    southRobots.add(rb);
                }
                // South to north
                else if (-rb.getRobotX() + b.getX() == 0 && rb.getRobotY() - a.getRobotY() > 0 && rb.getRobotY() - b.getY() <= 0) {
                    northRobots.add(rb);
                }
            }
        }
    }

    public void edgeBlocksPath(Robot a, Position b) {


        if (a.getRobotY() == b.getY()) {
            // West to east
            if ( b.getX() >= World.Bottom_Right_Boundary()[0]) {
                eastEdge.add(new Edge(World.Bottom_Right_Boundary()[0],b.getY()));
            }
            // East to west
            if (b.getX() <= World.Top_Left_Boundary()[0]) {
                westEdge.add(new Edge(World.Top_Left_Boundary()[0],b.getY()));
            }
        }
        if (a.getRobotX() == b.getX()) {
            // North to south
            if ( b.getY() <= World.Bottom_Right_Boundary()[1]) {
                southEdge.add(new Edge(b.getX(),World.Bottom_Right_Boundary()[1]));
            }
            // South to north
            if (b.getY() >= World.Top_Left_Boundary()[1]) {
                northEdge.add(new Edge(b.getX(),World.Top_Left_Boundary()[1]));
            }
        }
    }
}
