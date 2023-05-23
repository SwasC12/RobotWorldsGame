package za.co.wethinkcode.Server;


import com.fasterxml.jackson.databind.JsonNode;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.simple.JSONObject;
import za.co.wethinkcode.Server.World.Robot;
import za.co.wethinkcode.Server.World.World;

import java.io.*;
import java.net.*;
import java.util.ArrayList;

import static za.co.wethinkcode.Server.CommandHandler.*;
import static za.co.wethinkcode.Server.World.Status.NORMAL;



public class SimpleServer implements Runnable {
    public static final int PORT = 5000;
    private final BufferedReader in;
    private final PrintStream out;
    private final String clientMachine;
    private static String cyan_bg = ServerGraphics.ANSI_CYAN_BG;
    private static String reset = ServerGraphics.ANSI_RESET;
    private static String black = ServerGraphics.ANSI_BLACK;
    private static String green = ServerGraphics.ANSI_GREEN;
    private static String cyan_bg_bright = ServerGraphics.ANSI_CYAN_BG_BRIGHT;
    private static String red = ServerGraphics.ANSI_RED;
    private static String y_bg = ServerGraphics.ANSI_YELLOW_BG;
    Robot robot;
    CommandHandler commandHandler;

    private Socket socket;
    public static World textWorld;

    JSONObject responseData;


    public SimpleServer(Socket socket, World textWorld, CommandHandler commandHandler) throws IOException {
        this.textWorld = textWorld;
        this.socket = socket;
        this.robot = new Robot(socket);
        this.commandHandler = commandHandler;

        clientMachine = socket.getInetAddress().getHostName();
        System.out.println(green + "   A new Client has connected from: " + clientMachine);
        System.out.println(green + "   Client's IP Address is: " + cyan_bg + black + socket.getInetAddress().getHostAddress() + reset);

        out = new PrintStream(socket.getOutputStream());
        out.println(green + "connected to server" + reset);
        out.flush();
        in = new BufferedReader(new InputStreamReader(
                socket.getInputStream()));


    }

    @Override
    public void run() {
        try {

            while (socket.isConnected()) {
                System.out.println(green + "Waiting for the client request" + reset);


                //Read request from server
                ObjectInputStream fromClient = new ObjectInputStream(socket.getInputStream());
                Object object = fromClient.readObject();
                String jsonData = object.toString();
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode rootNode = objectMapper.readTree(jsonData);
                System.out.println("Request from " + rootNode.get("robot").asText() + " client");
                System.out.println("   COMMAND to execute:    " + rootNode.get("command").asText());
                System.out.println("   ARGUMENTS   " + rootNode.get("arguments").asText());

                try {
                    //Process the request from the server
                    String robotCommand = rootNode.get("command").asText();
                    String robotName = rootNode.get("robot").asText();
                    String[] args = rootNode.get("arguments").asText().replace("[","").replace("]","").split(",");
                    robot.setRobotName(robotName);

                    int index_dead = 0;
                    ArrayList<String> robotList = new ArrayList<>();

                    for (Robot rob: CommandHandler.deadRobots){
                        if (rob.getRobotName().equalsIgnoreCase(robotName)) {
                            robotList.add(rob.getRobotName());
                            break;
                        }
                        else{
                            index_dead=index_dead+1;
                        }
                    }
                    System.out.println(red + y_bg + "DEAD robots:" + reset + red + CommandHandler.deadRobots.size() + reset);
                    System.out.println("index:  "+ index_dead);
                    if (reloadRobots.size()>= 1 && reloadRobots.contains(robot)){
                        int realRobotIndex = CommandHandler.myRobots.indexOf(robot);
                        Robot realRealRobot = CommandHandler.myRobots.get(realRobotIndex);

                        ObjectOutputStream ToClient = new ObjectOutputStream(socket.getOutputStream());
                        JSONObject fileJson = new JSONObject();
                        JSONObject subJson1 = new JSONObject();
                        JSONObject subJson3 = new JSONObject();

                        subJson3.put("position","["+ realRealRobot.getRobotX() + "," + realRealRobot.getRobotY() + "]");
                        subJson3.put("direction", realRealRobot.getRobotDirection());
                        subJson3.put("shields", realRealRobot.getRobotShields());
                        subJson3.put("shots", realRealRobot.getRobotShots()+" (shotDistance = "+ realRealRobot.getShotDistance()+" steps)");
                        subJson3.put("status", realRealRobot.getRobotStatus());

                        fileJson.put("result", "RELOAD");
                        subJson1.put("message","Done");
                        fileJson.put("state",subJson3);
                        ToClient.writeObject(fileJson);
                        ToClient.flush();


                    }
                    else if (repairRobots.size()>= 1 && repairRobots.contains(robot)) {
                        int realRobotIndex = CommandHandler.myRobots.indexOf(robot);
                        Robot realRealRobot = CommandHandler.myRobots.get(realRobotIndex);
                        ObjectOutputStream TOClient = new ObjectOutputStream(socket.getOutputStream());
                        JSONObject fileJson = new JSONObject();
                        JSONObject subJson1 = new JSONObject();
                        JSONObject subJson4 = new JSONObject();

                        subJson4.put("position", "[" + realRealRobot.getRobotX() + "," + realRealRobot.getRobotY() + "]");
                        subJson4.put("direction", realRealRobot.getRobotDirection());
                        subJson4.put("shields", realRealRobot.getRobotShields());
                        subJson4.put("shots", realRealRobot.getRobotShots() + " (shotDistance = " + realRealRobot.getShotDistance() + " steps)");
                        subJson4.put("status", realRealRobot.getRobotStatus());

                        fileJson.put("result", "REPAIR");
                        subJson1.put("message", "Done");
                        fileJson.put("state", subJson4);
                        TOClient.writeObject(fileJson);
                        TOClient.flush();
                    }

                    else if(CommandHandler.deadRobots.size()>=1 && robotList.contains(robotName))  {
                        ObjectOutputStream toClient = new ObjectOutputStream(socket.getOutputStream());
                        JSONObject subJson2 = new JSONObject();
                        JSONObject fileJson = new JSONObject();

                        subJson2.put("position","["+CommandHandler.deadRobots.get(index_dead).getRobotX()+","+CommandHandler.deadRobots.get(index_dead).getRobotY()+"]");
                        subJson2.put("direction",CommandHandler.deadRobots.get(index_dead).getRobotDirection());
                        subJson2.put("shields",CommandHandler.deadRobots.get(index_dead).getRobotShields());
                        subJson2.put("shots",CommandHandler.deadRobots.get(index_dead).getRobotShots()+" (shotDistance = "+CommandHandler.deadRobots.get(index_dead).getShotDistance()+" steps)");
                        subJson2.put("status",CommandHandler.deadRobots.get(index_dead).getRobotStatus().toString());
                        fileJson.put("result", "DEAD");
                        fileJson.put("state",subJson2);
                        toClient.writeObject(fileJson);
                        toClient.flush();
                    }
                    else if (robotCommand != null && robotName != null ) {
                        for (Robot rob : myRobots){
                            System.out.println(rob.getRobotName() +"  "+ rob.getRobotStatus());
                        }

                        System.out.println(green + "----------------------------------------------- " + reset);
                        System.out.println(cyan_bg_bright + black + "Processing Client request ..." + reset);
                        ObjectOutputStream toClient = new ObjectOutputStream(socket.getOutputStream());
                        if (rootNode.get("command").asText().contains("forward")) {
                            responseData = Forward.execute(Integer.parseInt(rootNode.get("arguments").asText().replace("]", "").replace("[", "")), robotName);

                        } else if (rootNode.get("command").asText().contains("back")) {
                            responseData = Back.execute(Integer.parseInt(rootNode.get("arguments").asText().replace("]", "").replace("[", "")), robotName);
                        } else if (rootNode.get("command").asText().contains("turn")) {
                            responseData = Turn.execute(rootNode.get("arguments").asText().replace("]", "").replace("[", ""), robotName);
                        } else {
                            responseData = commandHandler.CommandCheck(robotCommand, robotName,args);
                        }

                        System.out.println(green + "------------------------------------------------" + reset);
                        System.out.println(green + "Sending results back to client as a response:>>>>" + reset);
                        System.out.println(green + "    ---------------------------------------------------------------------" + reset);
                        System.out.println(green + "    RESPONSE: " + responseData.toJSONString() + reset);
                        System.out.println(green + "    ---------------------------------------------------------------------" + reset);
                        System.out.println(" ");
                        toClient.writeObject(responseData);
                        toClient.flush();
                        for (Robot rob : myRobots){
                            System.out.println(rob.getRobotName() +"  "+ rob.getRobotStatus());
                        }
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException ex) {
            System.out.println(red + robot.getRobotName() + " exited from the Server" + reset);
            commandHandler.removeRobot(robot);


        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } finally {
            closeQuietly();
        }
    }

    public void closeQuietly() {
        try {
            socket.close();
        } catch (IOException ignore) {

        }

    }
}
