package za.co.wethinkcode.Server.World;



import za.co.wethinkcode.Server.CommandHandler;
import za.co.wethinkcode.Server.Command;
import java.net.Socket;
import java.util.Random;

import static java.lang.String.valueOf;

public class Robot{

    public static int width = World.width;
    public static int height = World.height;
    public static Position TOP_LEFT = new Position(-width/2,height/2);
    public static Position BOTTOM_RIGHT = new Position(width/2,-height/2);
    public  Position position = new Position(getRobotX(),getRobotY());
//    public AbstractWorld world;

    public static int [] xAndY = new int[2];
    public static CommandHandler commandHandler;
    Command command = new Command();

    public static Random random = new Random();
    private String robotName;
    int x;
    int y;
    String results;
    int visibility;
    int reload;
    int repair;
    int shields;
    int maxShield;
    int maxShots;

    String currentDirection;
    int shots;
    int shotDistance;


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
        this.currentDirection = valueOf(Direction.UP);
        this.shots = shots;
        this.status = valueOf(Status.NORMAL);
        this.robotName = nameOfRobot;
    }

    public void setMaxShield(int maxShield) {
        this.maxShield = maxShield;
    }

    public void setMaxShots(int maxShots) {
        this.maxShots = maxShots;
    }

    public int getMaxShield() {
        return maxShield;
    }
    public int getMaxShots() {
        return maxShots;
    }

    public void setRobotName(String inp){
        this.robotName = inp;
    }

    public void setRobotX(int x){
        this.x = x;
    }

    public void setRobotY(int y){
        this.y = y;
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
    public void setShields(int shields) {
        this.shields = shields;
    }


    public String getRobotDirection(){
        return this.currentDirection;
    }
    public void setCurrentDirection(Direction direction){
        this.currentDirection = valueOf(direction);
    }

    public int getRobotShots(){
        return this.shots;
    }

    public void setRobotShots(int shots) {
        this.shots = shots;
    }

    public int getShotDistance() {
        return shotDistance;
    }

    public void setShotDistance(int shotDistance) {
        this.shotDistance = shotDistance;
    }


    public String getRobotStatus(){
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
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
            X = random.nextInt(width)-width/2;
            Y = random.nextInt(height)-height/2;

            Position point = new Position(X,Y);
            if (!robotBlockedPathByObstacle(point) && !robotBlockedPathByRobot(point)){
                xAndY[0] = X;
                xAndY[1] = Y;
                break;
            }

        }
        return xAndY;
    }

    public UpdateResponse updatePosition(int nrSteps) {
        int newX = this.getRobotX();
        int newY = this.getRobotY();
        Position previousPosition = new Position(newX, newY);

        if (valueOf(Direction.UP).equals(this.currentDirection)) {
            newY = newY + nrSteps;

        } else if (valueOf(Direction.RIGHT).equals(this.currentDirection)) {
            newX = newX + nrSteps;

        } else if (valueOf(Direction.DOWN).equals(this.currentDirection)) {
            newY = newY - nrSteps;

        } else if (valueOf(Direction.LEFT).equals(this.currentDirection)) {
            newX = newX - nrSteps;

        }
        Position start = new Position(getRobotX(),getRobotY());
        Position end = new Position(newX,newY);



        Position newPosition = new Position(newX, newY);
        if (newPosition.isIn(TOP_LEFT, BOTTOM_RIGHT) && !command.IsPathBlockedObstacle(start,end)  && !command.IsRobotPathBlocked(start, end) ) {
            this.position = newPosition;
            setRobotX(newX);
            setRobotY(newY);
            return UpdateResponse.Done;

        }else{

            return UpdateResponse.Obstructed;
        }
    }


    public void updateDirection(boolean turnRight) {
        if (turnRight){
            if (getRobotDirection().equals(valueOf(Direction.UP))){
                setCurrentDirection(Direction.RIGHT);
            }
            else if(getRobotDirection().equals(valueOf(Direction.RIGHT))){
                setCurrentDirection(Direction.DOWN);
            }
            else if(getRobotDirection().equals(valueOf(Direction.DOWN))){
                setCurrentDirection(Direction.LEFT);
            }
            else if(getRobotDirection().equals(valueOf(Direction.LEFT))){
                setCurrentDirection(Direction.UP);
            }
        }else{
            if (getRobotDirection().equals(valueOf(Direction.UP))){
                setCurrentDirection(Direction.LEFT);
            }
            else if(getRobotDirection().equals(valueOf(Direction.RIGHT))){
                setCurrentDirection(Direction.UP);
            }
            else if(getRobotDirection().equals(valueOf(Direction.DOWN))){
                setCurrentDirection(Direction.RIGHT);
            }
            else if(getRobotDirection().equals(valueOf(Direction.LEFT))){
                setCurrentDirection(Direction.DOWN);
            }
        }
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
                "direction: " + this.currentDirection + "," +
                "shields: " + this.shields + "," +
                "shots: " + this.shots + "," +
                "status: " + this.status + "," +
                "}" +
                "}";

    }
    public int getX() {
        return x;
    }


    public int getY() {
        return y;
    }

    @Override
    public boolean equals(Object o){
        if (!(o instanceof Robot)){
            return false;
        }
        Robot otherRobot = (Robot) o;
        return this.getRobotName().equals(otherRobot.getRobotName());
    }

}





