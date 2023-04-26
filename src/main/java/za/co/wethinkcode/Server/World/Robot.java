package za.co.wethinkcode.Server.World;

import za.co.wethinkcode.Server.CommandHandler;

import java.net.Socket;
import java.util.Random;

public class Robot{

    public static int [] xAndY = new int[2] ;
    public static CommandHandler commandHandler;

    public static Random random = new Random();
    private String robotName;
    int x;
    int y;
    String results;
    int visibility;
    int reload;
    int repair;
    int shields;

    String direction;
    int shots;

    String status;

    String type;
    Socket socket;

    public Robot(Socket socket){
        this.socket = socket;
    }

    public Robot(String type, String nameOfRobot,int x, int y, String results, int visibility, int reload, int repair,int shields, String direction,int shots, String status){
        this.type = type;
        this.x = x;
        this.y = y;
        this.results = results;
        this.visibility = visibility;
        this.reload = reload;
        this.repair = repair;
        this.shields = shields;
        this.direction = direction;
        this.shots = shots;
        this.status = status;
        this.robotName = nameOfRobot;
    }

    public void setRobotName(String inp){
        this.robotName = inp;
    }

    public String getRobotName(){
        return robotName;
    }

    public String getType(){
        return this.type;
    }

    public int getRobotX(){
        return this.x;
    }

    public int getRobotY(){
        return this.y;
    }

    public String getRobotResults(){
        return this.results;
    }

    public int getRobotVisibility(){
        return this.visibility;
    }

    public int getRobotReload(){
        return this.reload;
    }

    public int getRobotRepair(){
        return this.repair;
    }

    public int getRobotShields(){
        return this.shields;
    }

    public String getRobotDirection(){
        return this.direction;
    }

    public int getRobotShots(){
        return this.shots;
    }

    public String getRobotStatus(){
        return this.status;
    }

    public void setRobotX(){
        this.x = random.nextInt(801)-400;
    }
    public  void setRobotY(){
        this.y = random.nextInt(801)-400;
    }

    //Check if the point is within an obstacle
    public static boolean robotBlockedPathByObstacle(Position a) {
        boolean value = false;
        for (Obstacles obs : World.ListOfObstacles) {
            value =  obs.getX() <=a.getX() && obs.getX() +4 >= a.getX()
                    && obs.getY() <= a.getY() && obs.getY() + 4 >= a.getY();
            if (value){
                break;
            }
        }
        return value;
    }

    public static boolean robotBlockedPathByRobot(Position a){
        boolean value = false;
        for (Robot rb : commandHandler.myRobots) {
            value =  rb.getRobotX() ==a.getX() && rb.getRobotY() == a.getY();
            if (value == true){
                break;
            }
        }
        return value;
    }



    public static int [] generateXAndY(){

        int X;
        int Y;
        while(true){
            X = random.nextInt(800)-400;
            Y = random.nextInt(800)-400;
//            X = 0;
//            Y = 0;
            Position point = new Position(X,Y);
            if (!robotBlockedPathByObstacle(point) && !robotBlockedPathByRobot(point)){
                xAndY[0] = X;
                xAndY[1] = Y;
                break;
            }

        }
        return xAndY;
    }



    @Override
    public String toString() {
        return "{" +
                    "result: " + this.results +
                    "data: {" +
                    "position: [" + this.x + "," + this.y + "], " +
                    "visibility: " + this.visibility + " ," +
                    "reload: " + this.reload + "," +
                    "repair: " + this.repair + "," +
                    "shields: " + this.shields + "," +
                "}," +
                    "state: {" + " ," +
                    "position: " + "[" + this.x + " ," + this.y + "]," +
                    "direction: " + this.direction + "," +
                    "shields: " + this.shields + "," +
                    "shots: " + this.shots + "," +
                    "status: " + this.status + "," +
                    "}" +
                "}";

    }


}
