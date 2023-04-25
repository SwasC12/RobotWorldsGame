package za.co.wethinkcode.ClientTest;


import org.json.simple.JSONObject;
import org.junit.jupiter.api.Test;
import za.co.wethinkcode.Client.CreateJSONObject;

import static org.junit.jupiter.api.Assertions.*;

    class CreateJSONObjectTest {

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


        @Test
        void getJsonObjectLaunchCommand() {
            CreateJSONObject createJSONObject = new CreateJSONObject();
            createJSONObject.setRobotName("KWANELE");
            createJSONObject.setCommand("launch ai 4 3");

            JSONObject jsonObject = createJSONObject.getJsonObject();

            assertEquals("KWANELE", jsonObject.get("robot"));
            assertEquals("[ai, 4, 3]", jsonObject.get("arguments"));
            assertEquals("launch", jsonObject.get("command"));
        }


        @Test
        void getJsonObjectLookCommand() {
            CreateJSONObject createJSONObject = new CreateJSONObject();
            createJSONObject.setRobotName("KWANELE");
            createJSONObject.setCommand("look");

            JSONObject jsonObject = createJSONObject.getJsonObject();

            assertEquals("KWANELE", jsonObject.get("robot"));
            assertEquals("[]", jsonObject.get("arguments"));
            assertEquals("look", jsonObject.get("command"));
        }

        @Test
        void getJsonObjectStateCommand() {
            CreateJSONObject createJSONObject = new CreateJSONObject();
            createJSONObject.setRobotName("KWANELE");
            createJSONObject.setCommand("state");

            JSONObject jsonObject = createJSONObject.getJsonObject();

            assertEquals("KWANELE", jsonObject.get("robot"));
            assertEquals("[]", jsonObject.get("arguments"));
            assertEquals("state", jsonObject.get("command"));
        }

    }

