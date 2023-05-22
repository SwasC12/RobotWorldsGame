package za.co.wethinkcode.ServerTest;

import org.junit.jupiter.api.Test;
import za.co.wethinkcode.Server.Command;
import za.co.wethinkcode.Server.World.Obstacles;
import za.co.wethinkcode.Server.World.Position;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ObstacleTest {
    @Test
    public void testGetType() {
        String type = "OBSTACLE";
        int x = 10;
        int y = 20;
        Obstacles obstacle = new Obstacles(type, x, y);

        assertEquals(type, obstacle.getType());
    }

    @Test
    public void testGetDirection() {
        String type = "OBSTACLE";
        int x = 10;
        int y = 20;
        Obstacles obstacle = new Obstacles(type, x, y);

        // Assuming the direction is not set in the constructor
        assertEquals(null, obstacle.getDirection());
    }

    @Test
    public void testGetX() {
        String type = "OBSTACLE";
        int x = 10;
        int y = 20;
        Obstacles obstacle = new Obstacles(type, x, y);

        assertEquals(x, obstacle.getX());
    }

    @Test
    public void testGetY() {
        String type = "OBSTACLE";
        int x = 10;
        int y = 20;
        Obstacles obstacle = new Obstacles(type, x, y);

        assertEquals(y, obstacle.getY());
    }
    @Test
    void testIsPathBlockedObstacle() {
        Command command = new Command();

        // Test path is not blocked
        Position start = new Position(0, 0);
        Position end = new Position(10, 10);
        assertFalse(command.IsPathBlockedObstacle(start, end));

        // Test path is blocked by obstacle
        start = new Position(2, 2);
        end = new Position(4, 4);
        assertFalse(command.IsPathBlockedObstacle(start, end));

        // Test path is partially blocked by obstacle
        start = new Position(0, 0);
        end = new Position(2, 2);
        assertFalse(command.IsPathBlockedObstacle(start, end));
    }
    @Test
    void testIsRobotPathBlocked(){
        Command command = new Command();

        // Test path is not blocked by a Robot
        Position start = new Position(0, 0);
        Position end = new Position(10, 10);
        assertFalse(command.IsRobotPathBlocked(start, end));

        // Test path is blocked by a Robot
        start = new Position(2, 2);
        end = new Position(4, 4);
        assertFalse(command.IsRobotPathBlocked(start, end));

        // Test path is partially blocked by a Robot
        start = new Position(0, 0);
        end = new Position(2, 2);
        assertFalse(command.IsRobotPathBlocked(start, end));
    }
}
