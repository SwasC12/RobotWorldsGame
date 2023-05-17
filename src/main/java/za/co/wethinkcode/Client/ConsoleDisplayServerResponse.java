package za.co.wethinkcode.Client;
import za.co.wethinkcode.Server.ServerGraphics;

import com.fasterxml.jackson.databind.JsonNode;

public class ConsoleDisplayServerResponse {
    private static String cyan_bg = ServerGraphics.ANSI_CYAN_BG;
    private static String reset = ServerGraphics.ANSI_RESET;
    private static String black = ServerGraphics.ANSI_BLACK;
    private static String green = ServerGraphics.ANSI_GREEN;
    private static String cyan_bg_bright = ServerGraphics.ANSI_CYAN_BG_BRIGHT;
    private static String red = ServerGraphics.ANSI_RED;
    private static String y_bg = ServerGraphics.ANSI_YELLOW_BG;


    public static void displayResponse(JsonNode jsonResponse, String command) throws NullPointerException, InterruptedException {
    

        if (!command.equalsIgnoreCase("launch")) {
            System.out.println(green + "CLIENT REQUESTS: " + "COMMAND: " + command.split(" ")[0] + " ----->> SERVER ");
        }
        System.out.println(green + "-------------------------------------------------" + reset);
        System.out.println(green + "-------------------------------------------------" + reset);
        System.out.println(green + "SERVER RESPONSE:" + reset);

         if (command.equalsIgnoreCase("state")) {
            System.out.println(red + y_bg + "Current state of robot :\n" + reset);
            stateRobot(jsonResponse);
        }


         else if (jsonResponse.get("result").asText().equalsIgnoreCase("OK")){

                 if (command.split(" ")[0].equalsIgnoreCase("launch")
                          ) {
                                System.out.println(green + "Launch Success!\nHere is the current status of your robot:" + reset);
                                System.out.println(green + "   *   Repair: " + jsonResponse.get("data").get("repair").asText() + reset);
                                System.out.println(green + "   *   Shields: " + jsonResponse.get("data").get("shields").asText()+ reset);
                                System.out.println(green + "   *   Reload: " + jsonResponse.get("data").get("reload").asText()+ reset);
                                System.out.println(green + "   *   Visibility: " + jsonResponse.get("data").get("visibility").asText()+ reset);
                                System.out.println(green + "   *   Position: " + jsonResponse.get("data").get("position").asText() + "\n"+ reset);
                }
                 else if (command.equalsIgnoreCase("look"))
                       {        asciiArt rwLooked = new asciiArt();
                                rwLooked.rwLook();//
                                System.out.println(green +  "Robot looking around the world>>>>>>>>>>>>>>:" + reset);
                                System.out.println(green + "   *   Objects:" +reset  );
                                int obj_num = 1;
                                for (JsonNode obj : jsonResponse.get("objects")) {
                                    System.out.print(green + "      [" + obj_num + "]");
                                    System.out.println(green + "  Direction: " + obj.get("direction").asText()+ reset);
                                    System.out.println(green + "           Type: " + obj.get("type").asText()+ reset);
                                    System.out.println(green + "           Distance: " + obj.get("distance").asText()+ reset);
                                    System.out.println(" ");
                                    obj_num++;
                                }
                                System.out.println(green + "    State of robot: " + reset);
                                stateRobot(jsonResponse);
                       }
                 else if (command.equalsIgnoreCase("fire") ) {
                       if (jsonResponse.get("result").asText().equalsIgnoreCase("OK")) {
                            System.out.println(red + "Fire response:  \n" + "    data:  " + jsonResponse.get("data") + reset);
                            System.out.println(red + "    My Robot state: " + jsonResponse.get("state")+ reset);
                        }

                        else if (jsonResponse.get("result").asText().equalsIgnoreCase("No bullets")){
//                            System.out.println(jsonResponse.get("message"));
                            System.out.println(red + "You have run out of bullets!!!, try reload option" + reset);

                       }

                 }
                 else if (command.split(" ").length > 1) {

                       if (command.split(" ")[0].equalsIgnoreCase("forward") && jsonResponse.get("data").get("message").asText().equalsIgnoreCase("Done")) {
                                System.out.println(green + StoreClientDetails.name + " moved forward by " + command.split(" ")[1] + " steps" + reset);
                                System.out.println(green + StoreClientDetails.name + " is now at position " + jsonResponse.get("state").get("position").asText()+ reset);

                       }
                       else if (command.split(" ")[0].equalsIgnoreCase("back") && jsonResponse.get("data").get("message").asText().equalsIgnoreCase("Done")) {
                                System.out.println(green + StoreClientDetails.name + " moved backwards by " + command.split(" ")[1] + " steps"+ reset);
                                System.out.println(green + StoreClientDetails.name + " is now at position " + jsonResponse.get("state").get("position").asText()+ reset);

                       }
                       else if (command.split(" ")[1].equalsIgnoreCase("right") && jsonResponse.get("data").get("message").asText().equalsIgnoreCase("Done")) {
                                System.out.println(green + StoreClientDetails.name + " turned right"+ reset);
                                System.out.println(green + StoreClientDetails.name + " is now at position " + jsonResponse.get("state").get("position").asText()+ reset);

                       }
                       else if (command.split(" ")[1].equalsIgnoreCase("left") && jsonResponse.get("data").get("message").asText().equalsIgnoreCase("Done")) {
                                System.out.println(green + StoreClientDetails.name + " turned left"+ reset);
                                System.out.println(green + StoreClientDetails.name + " is now at position " + jsonResponse.get("state").get("position").asText()+ reset);

                       }
                       else if (command.split(" ")[0].equalsIgnoreCase("forward") && jsonResponse.get("data").get("message").asText().equalsIgnoreCase("Obstructed")) {
                                System.out.println(red + "Sorry the path you attempting to move to is obstructed"+ reset);
                                System.out.println(green + StoreClientDetails.name + " is now at position " + jsonResponse.get("state").get("position").asText()+ reset);

                       }
                       else if (command.split(" ")[0].equalsIgnoreCase("back") && jsonResponse.get("data").get("message").asText().equalsIgnoreCase("Obstructed")) {
                                System.out.println(red + "Sorry the path you attempting to move to is obstructed"+ reset);
                                System.out.println(green + StoreClientDetails.name + " is now at position " + jsonResponse.get("state").get("position").asText()+ reset);

                       }
                 } else if (command.equalsIgnoreCase("reload")){
                     System.out.println("RELOADING");

                 } else if (command.equalsIgnoreCase("repair")) {
                     System.out.println("REPAIRING");
                 }
         }
        else if (jsonResponse.get("result").asText().equalsIgnoreCase("ERROR")) {
            System.out.println(red + "Oops, there seems to be an error:" + reset);
            System.out.println(red + "*   Error Message: " + y_bg + red +  jsonResponse.get("data").get("message").asText()+ reset);
        }

        else if(jsonResponse.get("result").asText().equalsIgnoreCase("DEAD")) {
            System.out.println(red + "Your Robot is DEAD!!!" + reset);
            asciiArt dead = new asciiArt();
            dead.rwDead();
            asciiArt gameOver = new asciiArt();
            gameOver.rwGameOver();
            stateRobot(jsonResponse);
         }
    }

    public static void stateRobot(JsonNode jsonResponse) {
        System.out.println(green + "   *   Shields: " + jsonResponse.get("state").get("shields").asText()+ reset);
        System.out.println(green + "   *   Position: " + jsonResponse.get("state").get("position").asText()+ reset);
        System.out.println(green + "   *   Shots remaining: " + jsonResponse.get("state").get("shots").asText()+ reset);
        System.out.println(green + "   *   Direction: " + jsonResponse.get("state").get("direction").asText()+ reset);
        System.out.println(green + "   *   Status: " + jsonResponse.get("state").get("status").asText()+ reset);
    }
}
