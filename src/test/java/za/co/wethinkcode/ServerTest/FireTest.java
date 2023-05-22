package za.co.wethinkcode.ServerTest;


import org.junit.jupiter.api.Test;
import za.co.wethinkcode.Server.BulletPosition;
import za.co.wethinkcode.Server.CommandHandler;
import za.co.wethinkcode.Server.HandleFireCommand;
import za.co.wethinkcode.Server.World.Robot;

import static java.lang.String.valueOf;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static za.co.wethinkcode.Server.CommandHandler.myRobots;
import static za.co.wethinkcode.Server.World.Status.NORMAL;

public class FireTest {

    CommandHandler commandHandler = new CommandHandler();
    @Test
    void testShieldDecrement() throws Exception {
        Robot robot = new Robot("ROBOT", "HAL1", 0, 0, "OK", 10, 10, 10, 10, "UP", 5, valueOf(NORMAL));
        myRobots.add(robot);
        String robotCommand = "fire";
        int count=0;
        for (int i=0;i<=2; i++){
               commandHandler.CommandCheck(robotCommand, robot.getRobotName(), new String[]{"[]"});
               count++;
        }
        int expectedShots = 5-count;
        assertEquals(expectedShots,robot.getRobotShots());
    }

    @Test
    void testShotInitialPosition() throws Exception {
        Robot robot = new Robot("ROBOT", "HAL1", 0, 1, "OK", 10, 10, 10, 10, "UP", 5, valueOf(NORMAL));
        myRobots.add(robot);

        BulletPosition bulletPosition = new BulletPosition(14,robot,robot.getRobotX(),robot.getRobotY());

        String expectedXandY = "[0,1]";
        String actualXandY = "["+bulletPosition.getXi()+","+bulletPosition.getYi()+"]";
        assertEquals(expectedXandY,actualXandY);
    }

    @Test
    void testShotDirection(){
        Robot robot = new Robot("ROBOT", "HAL1", 0, 1, "OK", 10, 10, 10, 10, "UP", 5, valueOf(NORMAL));
        myRobots.add(robot);
        BulletPosition bulletPosition = new BulletPosition(14,robot,robot.getRobotX(),robot.getRobotY());

        boolean shotDirection = bulletPosition.getDirection().equalsIgnoreCase(robot.getRobotDirection());
        assertTrue(shotDirection);
    }


}
