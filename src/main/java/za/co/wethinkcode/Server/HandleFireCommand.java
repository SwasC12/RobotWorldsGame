package za.co.wethinkcode.Server;

import org.json.simple.JSONObject;
import za.co.wethinkcode.Server.World.*;

import java.io.IOException;
import java.net.Socket;

import static java.lang.String.valueOf;

public class HandleFireCommand {
    Robot robot;

    Socket socket;

    private Position obstaclePosition;
    Position robotPosition;

    private Robot robotHit;
    public static CommandHandler commandHandler;
    public HandleFireCommand(CommandHandler commandHandler){
        this.commandHandler = commandHandler;
    }

    //determine if the Hit or Miss
//    public boolean Hit() {
//        BulletPosition bulletPosition = new BulletPosition(robot.getShotDistance(), robot);
//        Position position = new Position(bulletPosition.getXf(), bulletPosition.getYf());
//        return !shotBlockedPathByObstacle(robot, position) && shotBlockedPathByRobot(position);
//
//
//    }

    public boolean shotBlockedPathByObstacle(Position a, Position b) {

        for (Obstacles obt: World.ListOfObstacles)
            if (a.getX()==b.getX() && obt.getX() <= a.getX() && obt.getX() + 5 >= a.getX()
                    && (
                    (a.getY()<obt.getY() && b.getY()> obt.getY()+5)
                            ||
                            (a.getY()> obt.getY()+5 && b.getY() < obt.getY()))
            )
            {
                setObstaclePosition(new Position(obt.getX(), obt.getY()));
                return true;

            } else if(a.getY() == b.getY() && obt.getY() <= a.getY() && obt.getY() + 5 >= b.getY()
                    && (
                    (a.getX()  > obt.getX() + 5 && b.getX() < obt.getX())
                            ||
                            (a.getX() < obt.getX() && b.getX() > obt.getX()+ 5))){
                setObstaclePosition(new Position(obt.getX(), obt.getY()));
                return true;
            }
        return false;
    }

    public boolean shotBlockedPathByRobot(Position a, Position b) {

        for (Robot obt: CommandHandler.myRobots)

            if (a.getX()==b.getX() && obt.getRobotX() == a.getX()
                    && (
                    (a.getY()<obt.getRobotY() && b.getY()> obt.getRobotY())
                            ||
                            (a.getY()> obt.getRobotY() && b.getY() < obt.getRobotY()))

            )
            {
               // obt.setShields(obt.getRobotShields()-1);
                setRobotPosition(new Position(obt.getRobotX(),obt.getRobotY()));
                return true;

            } else if ( a.getY() == b.getY() && obt.getRobotY()== a.getY()
                    && (
                    (a.getX()  > obt.getRobotX() && b.getX() < obt.getRobotX())
                            ||
                            (a.getX() < obt.getRobotX() && b.getX() > obt.getRobotX()))){
                setRobotPosition(new Position(obt.getRobotX(),obt.getRobotY()));
                return true;
            }

        return false;

    }

    public boolean shotBlockedPositionByRobot(Position b){
        boolean correct = false;
        for (Robot rob : CommandHandler.myRobots) {
            correct = b.getX()== rob.getRobotX() && b.getY()==rob.getRobotY();
            System.out.println(correct);
            System.out.println("X" + b.getX()+", "+ rob.getRobotX() );
            System.out.println("Y" + b.getY()+", "+ rob.getRobotY() );
            if (correct){
               // System.out.println(correct);
                setRobotPosition(new Position(rob.getRobotX(),rob.getRobotY()));
                System.out.println("CORRECT");
               // rob.setShields(rob.getRobotShields()-1);
                break;
            }
        }
        System.out.println(correct);
        return correct;
    }

    public Position getObstaclePosition() {
        return obstaclePosition;
    }
    public Position getRobotPosition() {
        return robotPosition;
    }

    public void setObstaclePosition(Position obstaclePosition) {
        this.obstaclePosition = obstaclePosition;
    }

    public void setRobotPosition(Position robotPosition) {
        this.robotPosition = robotPosition;
    }

    public Robot getRobotHit() {
        return robotHit;
    }

    public void setRobotHit(Robot robotHit) {
        this.robotHit = robotHit;
    }

    public JSONObject createJSONResponseMiss(Robot rb){
        rb.setRobotShots(rb.getRobotShots()-1);
        //subJSON for data and state
        JSONObject dataJson1 = new JSONObject();
        dataJson1.put("message","Miss");
        JSONObject stateJson1 = new JSONObject();
        stateJson1.put("shots", rb.getRobotShots());

        JSONObject missResponse = new JSONObject();
        missResponse.put("result", "OK");
        missResponse.put("data", dataJson1);
        missResponse.put("state", stateJson1);
        //missResponse.put("data", "NJABULO");
        //missResponse.put("state", "MBANJWA");
        return missResponse;
    }

    public JSONObject createJSONResponseSuccess(Robot rb) throws IOException {
        rb.setRobotShots(rb.getRobotShots()-1);
//        if (getRobotHit().getRobotShields()==-1){
//              getRobotHit().setStatus("DEAD");
//        }

        double distance = Math.sqrt((getRobotPosition().getX()-rb.getRobotX())*(getRobotPosition().getX()-rb.getRobotX()) +(getRobotPosition().getY()-rb.getRobotY())*(getRobotPosition().getY()-rb.getRobotY()));
        //subJSON for data and state

        //Update robot shields

        for (Robot robot1: CommandHandler.myRobots){
            if (robot1.getRobotX()==getRobotPosition().getX() && robot1.getRobotY()==getRobotPosition().getY()){
                setRobotHit(robot1);
                getRobotHit().setShields(robot1.getRobotShields()-1);
                if (getRobotHit().getRobotShields()==-1){
                    getRobotHit().setStatus(valueOf(Status.DEAD));
                    CommandHandler.myRobots.remove(robot1);
                }
                //if (robot1.getRobotShields()<0){
                 //   getRobotHit().setStatus("DEAD");
                   // CommandHandler.myRobots.remove(robot1);
                   // socket.close();

                //}
            }
        }

        //Update hit rob state
        JSONObject subJson2 = new JSONObject();
        subJson2.put("position","["+getRobotPosition().getX()+","+getRobotPosition().getY()+"]");
        subJson2.put("direction",getRobotHit().getRobotDirection());
        subJson2.put("shields",getRobotHit().getRobotShields());
        subJson2.put("shots",getRobotHit().getRobotShots());
        subJson2.put("status",rb.getRobotStatus());



        JSONObject dataJson = new JSONObject();
        dataJson.put("message", "Hit");
        dataJson.put("distance", distance);//distance to the robot hit
        dataJson.put("robot", getRobotHit().getRobotName()); //getRobotName
        dataJson.put("state", subJson2.toJSONString()); //state info of the robot that was hot


        JSONObject stateJson = new JSONObject();
        stateJson.put("shots",rb.getRobotShots());
        JSONObject successResponse = new JSONObject();
        successResponse.put("result", "OK");
        successResponse.put("data", dataJson.toJSONString());
        successResponse.put("state", stateJson.toJSONString());
        return successResponse;
    }
}
