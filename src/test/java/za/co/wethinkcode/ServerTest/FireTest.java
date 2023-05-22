package za.co.wethinkcode.ServerTest;


import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.Assertions;
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

import org.json.simple.JSONObject;
import za.co.wethinkcode.Server.World.Position;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
public class FireTest {

    CommandHandler commandHandler = new CommandHandler();
    HandleFireCommand handleFireCommand = new HandleFireCommand(commandHandler);
    JSONParser parser = new JSONParser();

    @Test
    void testShieldDecrement() throws Exception {
        Robot robot = new Robot("ROBOT", "HAL1", 0, 0, "OK", 10, 10, 10, 10, "UP", 5, valueOf(NORMAL));
        myRobots.add(robot);
        String robotCommand = "fire";
        int count = 0;
        for (int i = 0; i <= 2; i++) {
            commandHandler.CommandCheck(robotCommand, robot.getRobotName(), new String[]{"[]"});
            count++;
        }
        int expectedShots = 5 - count;
        assertEquals(expectedShots, robot.getRobotShots());
    }

    @Test
    void testShotInitialPosition() throws Exception {
        Robot robot = new Robot("ROBOT", "HAL1", 0, 1, "OK", 10, 10, 10, 10, "UP", 5, valueOf(NORMAL));
        myRobots.add(robot);

        BulletPosition bulletPosition = new BulletPosition(14, robot, robot.getRobotX(), robot.getRobotY());

        String expectedXandY = "[0,1]";
        String actualXandY = "[" + bulletPosition.getXi() + "," + bulletPosition.getYi() + "]";
        assertEquals(expectedXandY, actualXandY);
    }

    @Test
    void testShotDirection() {
        Robot robot = new Robot("ROBOT", "HAL1", 0, 1, "OK", 10, 10, 10, 10, "UP", 5, valueOf(NORMAL));
        myRobots.add(robot);
        BulletPosition bulletPosition = new BulletPosition(14, robot, robot.getRobotX(), robot.getRobotY());

        boolean shotDirection = bulletPosition.getDirection().equalsIgnoreCase(robot.getRobotDirection());
        assertTrue(shotDirection);
    }

    @Test
    public void testCreateJSONResponseMiss() throws Exception {
        Robot robot = new Robot("ROBOT", "HAL1", 0, 0, "OK", 10, 10, 10, 10, "UP", 5, valueOf(NORMAL));
        List<Robot> myRobots = new ArrayList<>();
        myRobots.add(robot);
        CommandHandler.myRobots = myRobots;

        Position obstaclePosition = new Position(0, 1);
        handleFireCommand.setObstaclePosition(obstaclePosition);
        Position robotPosition = new Position(0, 6);
        handleFireCommand.setRobotPosition(robotPosition);
        //TODO

    }


    @Test
    public void testCreateJSONResponseSuccess() throws IOException, ParseException {

        Robot robot = new Robot("ROBOT", "HAL1", 0, 0, "OK", 10, 10, 10, 10, "UP", 5, valueOf(NORMAL));
        List<Robot> myRobots = new ArrayList<>();
        myRobots.add(robot);
        CommandHandler.myRobots = myRobots;

        Position obstaclePosition = new Position(1, 1);
        handleFireCommand.setObstaclePosition(obstaclePosition);

        Position robotPosition = new Position(0, 1);
        handleFireCommand.setRobotPosition(robotPosition);
        handleFireCommand.setRobotHit(robot);

        JSONObject jsonResponse = handleFireCommand.createJSONResponseSuccess(robot);

        assertEquals("OK", jsonResponse.get("result"));
        Object obj = parser.parse((String) jsonResponse.get("data"));
        JSONObject jsonData = (JSONObject) obj;

        assertEquals("Hit", jsonData.get("message"));
        assertEquals(1.0, jsonData.get("distance"));
        assertEquals("HAL1", jsonData.get("robot"));

    }
    @Test
    public void testCreateJSONResponseNoShots() throws Exception {
        Robot robot = new Robot("ROBOT", "TestRobot", 0, 0, "OK", 10, 10, 10, 10, "UP", 5, valueOf(NORMAL));

        myRobots.add(robot);
        String robotCommand = "fire";
        for (int i = 0; i <= 5; i++) {
            commandHandler.CommandCheck(robotCommand, robot.getRobotName(), new String[]{"[]"});
            System.out.println(robot.getRobotShots());
        }
        JSONObject jsonResponse = handleFireCommand.createJSONResponseNoShots(robot);
        JSONObject dataJson = (JSONObject) jsonResponse.get("data");
        JSONObject stateJson = (JSONObject) jsonResponse.get("state");
        assertEquals(0, stateJson.get("shots"));
        assertEquals("No bullets left!!!", dataJson.get("message"));

    }
}