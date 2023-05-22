package za.co.wethinkcode.ServerTest;

import org.junit.jupiter.api.Test;
import za.co.wethinkcode.Server.World.Direction;
import za.co.wethinkcode.Server.World.Robot;
import za.co.wethinkcode.Server.World.UpdateResponse;

import static org.junit.jupiter.api.Assertions.*;

public class RobotTest {

    @Test
    public void testUpdatePosition() {
        Robot robot = new Robot("type", "Robot1", 0, 0, "", 0, 0, 0, 0, "", 0, "");
        robot.setMaxShield(5);
        robot.setMaxShots(10);

    }

    @Test
    public void testUpdateDirection() {
        Robot robot = new Robot("type", "Robot1", 0, 0, "", 0, 0, 0, 0, "", 0, "");
        robot.setCurrentDirection(Direction.UP);

        // Test turning the robot right
        robot.updateDirection(true);
        assertEquals(Direction.RIGHT.toString(), robot.getRobotDirection());

        // Test turning the robot left
        robot.updateDirection(false);
        assertEquals(Direction.UP.toString(), robot.getRobotDirection());
    }

    @Test
    public void testEquals() {
        Robot robot1 = new Robot("type", "Robot1", 0, 0, "", 0, 0, 0, 0, "", 0, "");
        Robot robot2 = new Robot("type", "Robot2", 0, 0, "", 0, 0, 0, 0, "", 0, "");

        // Test equality between two robots with the same name
        assertEquals(robot1, robot1);
        assertNotEquals(robot1, robot2);

        // Test equality between a robot and a non-robot object
        assertNotEquals(robot1, "Not a robot");
    }
}