package za.co.wethinkcode.Client;

import com.fasterxml.jackson.databind.JsonNode;

public class ConsoleDisplayServerResponse {
    public static void displayResponse(JsonNode jsonResponse, String command) throws NullPointerException {
//        System.out.println(" ");
        if (!command.equalsIgnoreCase("launch")) {
            System.out.println("CLIENT REQUESTS: " + "COMMAND: " + command.split(" ")[0] + " ----->> SERVER ");
        }
        System.out.println("-------------------------------------------------");
        System.out.println("-------------------------------------------------");
        System.out.println("SERVER RESPONSE:");

        if (command.equalsIgnoreCase("state")) {
            System.out.println("Current state of robot :\n");
            stateRobot(jsonResponse);
        }
        else if (command.split(" ")[0].equalsIgnoreCase("launch") && jsonResponse.get("result").asText().equalsIgnoreCase("OK")) {
            System.out.println("Congratulations! You have successfully launched your robot.\nHere is the current status of your robot:");
            System.out.println("   *   Repair: " + jsonResponse.get("data").get("repair").asText());
            System.out.println("   *   Shields: " + jsonResponse.get("data").get("shields").asText());
            System.out.println("   *   Reload: " + jsonResponse.get("data").get("reload").asText());
            System.out.println("   *   Visibility: " + jsonResponse.get("data").get("visibility").asText());
            System.out.println("   *   Position: " + jsonResponse.get("data").get("position").asText() + "\n");
            //  System.out.println(jsonResponse.getClass());
        }
        else if (command.equalsIgnoreCase("look") && jsonResponse.get("result").asText().equalsIgnoreCase("OK")) {

            System.out.println("Robot looking around the world>>>>>>>>>>>>>>:");
            System.out.println("   *   Objects:");
            int obj_num=1;
            for (JsonNode obj: jsonResponse.get("objects")){
                System.out.print("      ["+obj_num+"]");
                System.out.println("  Direction: "+ obj.get("direction").asText());
                System.out.println("           Type: "+obj.get("type").asText());
                System.out.println("           Distance: "+obj.get("distance").asText());
                System.out.println(" ");
                obj_num++;
            }
            System.out.println("    State of robot: ");
            stateRobot(jsonResponse);
        }else if (command.equalsIgnoreCase("fire")) {
            if (jsonResponse.get("result").asText().equalsIgnoreCase("OK")){
                System.out.println("Fire response:  \n" + "    data:  "+ jsonResponse.get("data"));
                System.out.println("    My Robot state: "+jsonResponse.get("state"));
            } else if (jsonResponse.get("result").asText().equalsIgnoreCase("No bullets")) {
                System.out.println("You have run out of bullets!!!, try reload option");
            }
        }

//        /*
//         *TODO
//         */

        else if (command.split(" ").length>1) {
//            System.out.println("entered in movements..");
            if (command.split(" ")[0].equalsIgnoreCase("forward") && jsonResponse.get("data").get("message").asText().equalsIgnoreCase("Done")) {
                System.out.println(StoreClientDetails.name+" moved forward by " + command.split(" ")[1] + " steps");
                System.out.println(StoreClientDetails.name+" is now at position " + jsonResponse.get("state").get("position").asText());
            } else if (command.split(" ")[0].equalsIgnoreCase("back") && jsonResponse.get("data").get("message").asText().equalsIgnoreCase("Done")) {
                System.out.println(StoreClientDetails.name+" moved backwards by " + command.split(" ")[1] + " steps");
                System.out.println(StoreClientDetails.name+" is now at position " + jsonResponse.get("state").get("position").asText());
            } else if (command.split(" ")[1].equalsIgnoreCase("right") && jsonResponse.get("data").get("message").asText().equalsIgnoreCase("Done")) {
                System.out.println(StoreClientDetails.name+" turned right");
                System.out.println(StoreClientDetails.name+" is now at position " + jsonResponse.get("state").get("position").asText());
            } else if (command.split(" ")[1].equalsIgnoreCase("left") && jsonResponse.get("data").get("message").asText().equalsIgnoreCase("Done")) {
                System.out.println(StoreClientDetails.name+" turned left");
                System.out.println(StoreClientDetails.name+" is now at position " + jsonResponse.get("state").get("position").asText());
            }
            else{
                System.out.println("Sorry the path you attempting to move to is obstructed");
                System.out.println(StoreClientDetails.name+" is now at position " + jsonResponse.get("state").get("position").asText());
            }
//        else if (command.equalsIgnoreCase("fire") && jsonResponse.get("result").asText().equalsIgnoreCase("OK")) {
//            System.out.println("HAL fired with 2 bullets");
//            System.out.println("HAL is left with 7 bullets");
//        }
        }
        else if (jsonResponse.get("result").asText().equalsIgnoreCase("ERROR")) {
            System.out.println("Oops, there seems to be an error:");
            System.out.println("*   Error Message: "+ jsonResponse.get("data").get("message").asText());
        }

    }
    public static void stateRobot(JsonNode jsonResponse){
        System.out.println("   *   Shields: " + jsonResponse.get("state").get("shields").asText());
        System.out.println("   *   Position: " + jsonResponse.get("state").get("position").asText());
        System.out.println("   *   Shots remaining: " + jsonResponse.get("state").get("shots").asText());
        System.out.println("   *   Direction: " + jsonResponse.get("state").get("direction").asText());
        System.out.println("   *   Status: " + jsonResponse.get("state").get("status").asText());
    }
}
