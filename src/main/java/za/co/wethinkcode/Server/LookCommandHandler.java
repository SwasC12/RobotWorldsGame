package za.co.wethinkcode.Server;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import za.co.wethinkcode.Server.World.Obstacles;
import za.co.wethinkcode.Server.World.Position;
import za.co.wethinkcode.Server.World.Robot;
import za.co.wethinkcode.Server.World.World;

import java.util.ArrayList;
import java.util.List;


public class LookCommandHandler {

    public  CommandHandler commandHandler;
    public static List <Obstacles> northObstacles = new ArrayList<>();
    public static List <Obstacles> eastObstacles = new ArrayList<>();
    public static List <Obstacles> southObstacles = new ArrayList<>();
    public static List <Obstacles> westObstacles = new ArrayList<>();

    public static List <Robot> northRobots = new ArrayList<>();
    public static List <Robot> eastRobots = new ArrayList<>();
    public static List <Robot> southRobots = new ArrayList<>();
    public static List <Robot> westRobots = new ArrayList<>();

    public List<String> lookDirections = new ArrayList<>(List.of("NORTH","EAST","SOUTH","WEST"));

    public LookCommandHandler(CommandHandler commandHandler){
        this.commandHandler = commandHandler;
    }
    public JSONObject writeJsonFileForLook(String filename, Robot rb) throws Exception{

        World.Top_Left_Boundary();
        World.Bottom_Right_Boundary();

        JSONObject fileJson = new JSONObject();

        if (commandHandler.myRobots.size()>0){
            for (int i = 0; i<commandHandler.myRobots.size();i++){
                fileJson.put("result",commandHandler.myRobots.get(i).getRobotResults());
            }
        }
//        Obstacles ob = new Obstacles("OBSTACLE",10,10);



        JSONArray dataArray = new JSONArray();
        JSONObject subJson3 = new JSONObject();
        JSONObject subJson4 = new JSONObject();
        JSONObject subJson5 = new JSONObject();
        JSONObject subJson6 = new JSONObject();





        for (int i = 0;i<4;i++){
            if (i == 0){
                Position destination = new Position(rb.getRobotX(), World.Top_Left[1]);


                //NORTH EDGE
                subJson3.put("direction","NORTH");
                subJson3.put("type","EDGE");
                subJson3.put("distance", World.Top_Left[1]- rb.getRobotY());
                dataArray.add(subJson3);

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
                for (int j = 0;j <northObstacles.size();j++){
                    subJson11[j] = new  JSONObject();
                    subJson11[j].put("direction","NORTH");
                    subJson11[j].put("type","ROBOT");
                    subJson11[j].put("distance", northRobots.get(j).getRobotY()- rb.getRobotY());
                    dataArray.add(subJson11[j]);
                }

            }
            else if (i == 1){
                Position destination = new Position(World.Bottom_Right[0],rb.getRobotY());
//                JSONObject [] subJson8 = new JSONObject[eastObstacles.size()];
                //EAST EDGE
                subJson4.put("direction","EAST");
                subJson4.put("type","EDGE");
                subJson4.put("distance", World.Bottom_Right[0]- rb.getRobotX());
                dataArray.add(subJson4);

                //EAST OBSTACLES
                LookCommandHandler.obstacleBlocksPath(rb, destination);
                JSONObject [] subJson9 = new JSONObject[eastObstacles.size()];
                System.out.println("about to enter the south obstacles for loop and size of east list is: ");
                System.out.println(eastObstacles.size());
                System.out.println("size of data array before the loop is ");
                System.out.println(dataArray.size());
                for (int j = 0;j < eastObstacles.size();j++){
                    subJson9[j] = new  JSONObject();
                    subJson9[j].put("direction","EAST");
                    subJson9[j].put("type","OBSTACLE");
                    subJson9[j].put("distance",eastObstacles.get(j).getX() - rb.getRobotX());
                    dataArray.add(subJson9[j]);

                }
                System.out.println("size of data array after the loop is ");
                System.out.println(dataArray.size());

                //EAST ROBOTS
                robotBlocksPath(rb,destination);
                JSONObject [] subJson12 = new JSONObject[eastRobots.size()];
                System.out.println("about to enter the south robots for loop and size of east lis is: ");
                System.out.println(eastRobots.size());
                System.out.println("size of data array before the loop is ");
                System.out.println(dataArray.size());
                for (int j = 0;j < eastRobots.size();j++){
                    subJson12[j] = new  JSONObject();
                    subJson12[j].put("direction","EAST");
                    subJson12[j].put("type","ROBOT");
                    subJson12[j].put("distance", eastRobots.get(j).getRobotX() - rb.getRobotX());
                    dataArray.add(subJson12[j]);
                }
                System.out.println("size of data array after the loop is ");
                System.out.println(dataArray.size());
            }
            else if (i == 2){
                Position destination = new Position(rb.getRobotX(), World.Bottom_Right[1]);


                //SOUTH EDGE
                subJson5.put("direction","SOUTH");
                subJson5.put("type","EDGE");
                subJson5.put("distance", -World.Bottom_Right[1]+ rb.getRobotY());
                dataArray.add(subJson5);

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
                Position destination = new Position(World.Top_Left[0],rb.getRobotY());

                //WEST EDGE
                subJson6.put("direction","WEST");
                subJson6.put("type","EDGE");
                subJson6.put("distance", -World.Top_Left[0]+ rb.getRobotX());

                dataArray.add(subJson6);

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

        return fileJson;

    }
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
                    System.out.println("the conditions entered forced me here!!");
                    eastRobots.add(rb);
                    System.out.println("number of east robots");
                    System.out.println(eastRobots.size());

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
}
