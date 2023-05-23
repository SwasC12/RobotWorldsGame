package za.co.wethinkcode.ClientTest;


import org.json.simple.JSONObject;
import org.junit.jupiter.api.Test;
import za.co.wethinkcode.Client.CreateJSONObject;

import static org.junit.jupiter.api.Assertions.*;

public class SimpleClientTest {
    @Test
    void testRequestMessage() {


        CreateJSONObject obj = new CreateJSONObject();
        obj.setRobotName("robot1");
        obj.setCommand("move 10");
        JSONObject expected;
        expected = new JSONObject();
        expected.put("robot", "robot1");
        expected.put("command", "move");
        expected.put("arguments", "[10]");
        JSONObject actual = obj.getJsonObject();
        assertEquals(expected.toString(), actual.toString());
    }

}

