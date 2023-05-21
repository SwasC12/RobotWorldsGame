package za.co.wethinkcode.ServerTest;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import za.co.wethinkcode.Server.CommandHandler;
import za.co.wethinkcode.Server.LookCommandHandler;
import za.co.wethinkcode.Server.World.Robot;
import za.co.wethinkcode.Server.World.World;

import static java.lang.String.valueOf;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static za.co.wethinkcode.Server.CommandHandler.myRobots;
import static za.co.wethinkcode.Server.World.Status.NORMAL;

public class CommandHandlerTest {

    private CommandHandler commandHandler;

    @Test
    public void testGenerateRobot() {
        commandHandler = new CommandHandler();
        World w = new World();
        Robot robot = commandHandler.generateRobot("Robot1", 100, 10, 5);

        assertEquals("Robot1", robot.getRobotName());
        assertEquals(100, robot.getRobotShields());
        assertEquals(10, robot.getRobotShots());
        assertEquals(5, robot.getShotDistance());
    }

    @Test
    public void testAddToList() {
        commandHandler = new CommandHandler();
        Robot robot = mock(Robot.class);
        commandHandler.addToList(robot);
        assertTrue(commandHandler.getRobots().contains(robot));
    }



    @Test
    public void testCommandCheckInvalidCommand() throws Exception {
        commandHandler = new CommandHandler();
        JSONObject result = commandHandler.CommandCheck("invalid", "Robot1", new String[]{});
        assertNotNull(result);
        assertEquals("ERROR", result.get("result"));

        JSONObject subJson1 = (JSONObject) result.get("data");
        assertNotNull(subJson1);
        assertEquals("Unsupported command", subJson1.get("message"));
    }



}
