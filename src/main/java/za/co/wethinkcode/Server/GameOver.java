package za.co.wethinkcode.Server;

import ch.qos.logback.core.status.Status;
import za.co.wethinkcode.Server.World.Robot;

import static za.co.wethinkcode.Server.World.Status.NORMAL;

public class GameOver {
    public CommandHandler commandHandler;
    Robot deadRobot;

    public Robot removeRobot(Status status){
        for (Robot deadRob: commandHandler.getRobots()){
            if (deadRob.getRobotStatus(NORMAL).equals(za.co.wethinkcode.Server.World.Status.DEAD)){
                deadRobot = deadRob;
                break;
            }
        }
        return deadRobot;
    }
}
