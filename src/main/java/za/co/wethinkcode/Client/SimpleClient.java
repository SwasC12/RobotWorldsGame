package za.co.wethinkcode.Client;

import java.net.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Scanner;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.simple.JSONObject;
public class SimpleClient implements Serializable{
    private static DataOutputStream toServer;
    private static DataInputStream fromServer;
    static Scanner scanner;

    public static void main(String[] args) {

        try (

                Socket socket = new Socket("localhost", 5000);
                //Socket socket = new Socket(ipAddress, port);
                PrintStream out = new PrintStream(socket.getOutputStream());
                OutputStream outputStream = socket.getOutputStream();
                BufferedReader in = new BufferedReader(new InputStreamReader(
                        socket.getInputStream()));
                BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));
        )
        {
            //get the user input
            System.out.println("Greeting");
              String message = userInput.readLine();
            System.out.println("Name of the Robot");
              String name = userInput.readLine();
            System.out.println("give command");
              String command = userInput.readLine();

            scanner = new Scanner(System.in);

            SimpleClient simpleClient = new SimpleClient();
            String[] arrayArgs;
            if (command.split(" ").length==1){
                arrayArgs = new String[]{};
            }else{
                arrayArgs = Arrays.copyOfRange(command.split(" "), 1, command.split(" ").length);}

            JSONObject msg_toServer = simpleClient.requestMessage(name,command.split(" ")[0], Arrays.toString(arrayArgs));
            byte[] requestData = msg_toServer.toString().getBytes(StandardCharsets.UTF_8);

            //SEND REQUEST
            outputStream.write(requestData);
            outputStream.flush();
            out.println(message);
            byte[] data = new byte[1024];
            // receiving message request from client
            InputStream inputStream = socket.getInputStream();
            // reading message request from server
            int bytesRead = inputStream.read(data);
            String jsonData = new String(data, 0, bytesRead);
            System.out.println(jsonData.getClass());
            ObjectMapper objectMapper = new ObjectMapper();
            //ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(jsonData);
            System.out.println(jsonNode);
            String response = in.readLine();
            System.out.println("Server response: " + response);

        } catch (IOException e) {
            e.printStackTrace();

    }}

    private JSONObject requestMessage(String robotName, String commandName, String arguments)  {
        JSONObject jsonRequest = new JSONObject();
        jsonRequest.put("robot", robotName);
        jsonRequest.put("arguments", arguments);
        jsonRequest.put("command", commandName);
        return jsonRequest;
    }
}