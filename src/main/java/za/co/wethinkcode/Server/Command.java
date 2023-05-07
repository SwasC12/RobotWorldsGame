package za.co.wethinkcode.Server;

import za.co.wethinkcode.Server.World.Obstacles;
import za.co.wethinkcode.Server.World.Position;
import za.co.wethinkcode.Server.World.Robot;

import java.util.List;

import static za.co.wethinkcode.Server.MultiServers.commandHandler;
import static za.co.wethinkcode.Server.World.World.getWorldObstacles;

public class Command {
    public Command() {
    }

    public boolean IsPathBlockedObstacle(Position start, Position end) {
        System.out.println("Path Obstacle");
        List<Obstacles> Path = getWorldObstacles();
        for (Obstacles blocked : Path) {
            if ((blocked.getX() >= start.getX() && blocked.getX() <= end.getX() &&
                    (blocked.getY() <= start.getY() && start.getY() <= blocked.getY() + 4) ||
                    (blocked.getX() + 4 >= end.getX() && blocked.getX() + 4 <= start.getX()) &&
                            (blocked.getY() <= start.getY() && blocked.getY() + 4 >= start.getY()) ||
                    (blocked.getY() >= start.getY() && blocked.getY() <= end.getY() &&
                            (blocked.getX() <= start.getX() && blocked.getX() + 4 >= start.getX()) ||
                            (blocked.getY() + 4 >= end.getY() && blocked.getY() + 4 <= start.getY() &&
                                    (blocked.getX() <= start.getX() && blocked.getX() + 4 >= start.getX()))))) {
                return true;
            }
        }
        return false;
    }

    public boolean IsRobotPathBlocked(Position start,Position end) {
        System.out.println("Path Robot");
        List<Robot> Robots = commandHandler.myRobots;
        for (Robot blocked : Robots) {
            if (blocked.getRobotX() == start.getX() && blocked.getRobotY() == start.getY()) {
                continue;
            }
            if (blocked.getX() >= start.getX() && blocked.getX() <= end.getX() &&
                    (blocked.getY() <= start.getY() && blocked.getY() >= end.getY() ||
                            (blocked.getX() <= start.getX() && blocked.getX() <= end.getX() &&
                                    (blocked.getY() >= start.getY() && blocked.getY() >= end.getY())))){
                System.out.println(blocked.getX()+" "+ blocked.getY());
                return true;
            }
        }
        return false;
    }
}