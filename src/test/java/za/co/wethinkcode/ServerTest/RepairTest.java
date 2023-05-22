package za.co.wethinkcode.ServerTest;

import org.junit.jupiter.api.Test;
import za.co.wethinkcode.Server.Repair;
import za.co.wethinkcode.Server.World.Robot;
import za.co.wethinkcode.Server.World.World;

import java.net.Socket;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RepairTest {

    private Socket target;
    private Repair repair;

    @Test
    public void testExecute() throws InterruptedException {
        // Create a sample robot
        Robot robot = new Robot(target);

        // Set initial robot status and shots
        robot.setStatus("NORMAL");
        robot.setMaxShield(robot.getMaxShield());

        // Create an instance of the Repair class
        Repair repair = new Repair();

        // Execute the repair process for the robot
        repair.execute(robot);

        // Wait for the repair process to complete
        Thread.sleep(World.repairTime + 100); // Add some buffer time

        // Verify the robot's status and shots after repair
        assertEquals("NORMAL", robot.getStatus());
        assertEquals(robot.getMaxShield(), robot.getRobotShields());
    }
}


