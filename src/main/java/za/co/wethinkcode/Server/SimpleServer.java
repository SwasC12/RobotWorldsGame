package za.co.wethinkcode.Server;


import com.fasterxml.jackson.databind.JsonNode;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.simple.JSONObject;
import za.co.wethinkcode.Server.World.Robot;
import za.co.wethinkcode.Server.World.World;

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;

import static za.co.wethinkcode.Server.World.Type.ROBOT;


public class SimpleServer implements Runnable {
    public static final int PORT = 5000;
    private final BufferedReader in;
    private final PrintStream out;
    private final String clientMachine;
    Robot robot;
    CommandHandler commandHandler;

    private Socket socket;
    public static World world;


//    CommandHandler command = new CommandHandler();

    public SimpleServer(Socket socket,World world,CommandHandler commandHandler) throws IOException {
        this.world = world;
        this.socket = socket;
        this.robot = new Robot(socket);
        this.commandHandler = commandHandler;

        clientMachine = socket.getInetAddress().getHostName();
        System.out.println("   A new Client has connected from: " + clientMachine);
        System.out.println("   Client's IP Address is: "+ socket.getInetAddress().getHostAddress());

        out = new PrintStream(socket.getOutputStream());
        out.println("Hi Client, you have successfully connected our server..");
        in = new BufferedReader(new InputStreamReader(
                socket.getInputStream()));


    }
    @Override
    public void run() {
        try {

            while (socket.isConnected()) {
                System.out.println("Waiting for the client request");


                //Read request from server
                ObjectInputStream fromClient = new ObjectInputStream(socket.getInputStream());
                Object object = fromClient.readObject();
                String jsonData = object.toString();
               // System.out.println(">>>>>>:    "+jsonData);
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode rootNode = objectMapper.readTree(jsonData);
                System.out.println("Request from " + rootNode.get("robot").asText()+ " client");
                System.out.println("   COMMAND to execute:    "+rootNode.get("command").asText());
                System.out.println("   ARGUMENTS   "+rootNode.get("arguments").asText());

                try {
                    //Process the request from the server
                    String robotCommand = rootNode.get("command").asText();
                    String robotName = rootNode.get("robot").asText();
                    robot.setRobotName(robotName);
                    if (robotCommand!=null && robotName!=null){
                        System.out.println("----------------------------------------------- ");
                        System.out.println("Processing Client request ...");
                        Thread.sleep(3000);
                        ObjectOutputStream toClient = new ObjectOutputStream(socket.getOutputStream());
                        JSONObject responseData = commandHandler.CommandCheck(robotCommand, robotName);
                        System.out.println("------------------------------------------------");
                        System.out.println("Sending results back to client as a response:>>>>");
                        System.out.println("    ---------------------------------------------------------------------");
                        System.out.println("    RESPONSE: "+responseData.toJSONString());
                        System.out.println("    ---------------------------------------------------------------------");
                        System.out.println(" ");
                    toClient.writeObject(responseData);
                    toClient.flush();}

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException ex) {
            System.out.println(robot.getRobotName()+" exited from the Server");
           // System.out.println(robot.getRobotName() +" " + robot.getType() +" "+ robot.getRobotDirection() );
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


