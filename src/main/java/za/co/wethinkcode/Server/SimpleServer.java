package za.co.wethinkcode.Server;


import com.fasterxml.jackson.databind.JsonNode;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import za.co.wethinkcode.Server.World.Direction;
import za.co.wethinkcode.Server.World.Robot;
import za.co.wethinkcode.Server.World.Status;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class SimpleServer implements Runnable {

    public static final int PORT = 5000;
    private final BufferedReader in;
    private final PrintStream out;
    private final String clientMachine;



    public static List<Robot> myRobots = new ArrayList<>();
    CommandHandler command = new CommandHandler();

    public SimpleServer(Socket socket) throws IOException {
        clientMachine = socket.getInetAddress().getHostName();
        System.out.println("Connection from " + clientMachine);

        out = new PrintStream(socket.getOutputStream());
        in = new BufferedReader(new InputStreamReader(
                socket.getInputStream()));

        System.out.println("Waiting for client...");
        byte[] data = new byte[1024];
        InputStream inputStream = socket.getInputStream();
        int bytesRead = inputStream.read(data);
        String jsonData = new String(data, 0, bytesRead);
        ObjectMapper objectMapper = new ObjectMapper();


        JsonNode rootNode = objectMapper.readTree(jsonData);

        String robotCommand = rootNode.get("command").asText();
        String robotName = rootNode.get("robot").asText();
        try {
            OutputStream outputStream  = socket.getOutputStream();
            byte[] responseData = command.CommandCheck(robotCommand,robotName).toString().getBytes(StandardCharsets.UTF_8);

            outputStream.write(responseData);
            outputStream.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void run() {
        try {
            Object messageFromClient;
            while ((messageFromClient = in.readLine()) != null) {

            }
        } catch (IOException ex) {
            System.out.println("Shutting down single client server");
        } finally {
            closeQuietly();
        }
    }

    private void closeQuietly() {
        try {
            in.close();
            out.close();
        } catch (IOException ex) {
        }
    }



    

}


