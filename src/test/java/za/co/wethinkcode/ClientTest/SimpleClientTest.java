package za.co.wethinkcode.ClientTest;


import org.json.simple.JSONObject;
import org.junit.jupiter.api.Test;
import za.co.wethinkcode.Client.SimpleClient;

import static org.junit.jupiter.api.Assertions.*;

public class SimpleClientTest {
    @Test
    void testRequestMessage() {
        SimpleClient simpleClient = new SimpleClient();
        String robotName = "robot1";
        String commandName = "move";
        String arguments = "1,1";
        JSONObject expected;
        expected = new JSONObject();
        expected.put("robot", robotName);
        expected.put("arguments", arguments);
        expected.put("command", commandName);
        JSONObject actual = simpleClient.requestMessage(robotName, commandName, arguments);
        assertEquals(expected.toString(), actual.toString());
    }
}
