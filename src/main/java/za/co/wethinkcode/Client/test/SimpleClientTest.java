package za.co.wethinkcode.Client.test;
import za.co.wethinkcode.Client.SimpleClient;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class SimpleClientTest {
    @Test
    void testRequestMessage() {
        SimpleClient simpleClient = new SimpleClient();
        String robotName = "robot1";
        String commandName = "move";
        String arguments = "[10]";
        JSONObject expected;
        expected = new JSONObject();
        expected.put("robot", robotName);
        expected.put("arguments", arguments);
        expected.put("command", commandName);
        JSONObject actual = simpleClient.requestMessage(robotName, commandName, arguments);
        assertEquals(expected.toString(), actual.toString());
    }
}

