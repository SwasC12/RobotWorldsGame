package za.co.wethinkcode.Server.World;

public class Robot{
    String robotName;
    int x;
    int y;
    String results;
    int visibility;
    int reload;
    int repair;
    int shields;
    Direction direction;
    int shots;
    Status status;


    public Robot(String nameOfRobot,int x, int y, String results, int visibility, int reload, int repair,int shields, Direction direction,int shots, Status status){
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

    public Direction getRobotDirection(){
        return this.direction;
    }

    public int getRobotShots(){
        return this.shots;
    }

    public Status getRobotStatus(){
        return this.status;
    }

    public String getRobotName(){
        return this.robotName;
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
                    "status: {" + " ," +
                    "position: " + "[" + this.x + " ," + this.y + "]," +
                    "direction: " + this.direction + "," +
                    "shields: " + this.shields + "," +
                    "shots: " + this.shots + "," +
                    "status: " + this.status + "," +
                    "}" +
                "}";

    }


}
