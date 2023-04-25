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
<<<<<<< src/main/java/za/co/wethinkcode/Client/SimpleClient.java
            System.out.println("ROBOT SUCCESSFULLY LAUNCHED");
            scanner = new Scanner(System.in);
=======

            //scanner = new Scanner(System.in);
>>>>>>> src/main/java/za/co/wethinkcode/Client/SimpleClient.java

            SimpleClient simpleClient = new SimpleClient();
            simpleClient.requestMessage(name,command,socket);
            JsonNode obj =  responseMessage(socket);
            displayResponse(obj);



        } catch (IOException e) {
            e.printStackTrace();

    }}

<<<<<<< src/main/java/za/co/wethinkcode/Client/SimpleClient.java
    public JSONObject requestMessage(String robotName, String commandName, String arguments)  {
=======
    private void requestMessage(String robotName, String command, Socket socket) throws IOException {
        String[] arrayArgs;
        if (command.split(" ").length==1){
            arrayArgs = new String[]{};
        }else{
            arrayArgs = Arrays.copyOfRange(command.split(" "), 1, command.split(" ").length);}
>>>>>>> src/main/java/za/co/wethinkcode/Client/SimpleClient.java
        JSONObject jsonRequest = new JSONObject();
        jsonRequest.put("robot", robotName);
        jsonRequest.put("arguments", Arrays.toString(arrayArgs));
        jsonRequest.put("command", command.split(" ")[0]);

        byte[] requestData = jsonRequest.toString().getBytes(StandardCharsets.UTF_8);
        //SEND REQUEST
        OutputStream outputStream = socket.getOutputStream();
        outputStream.write(requestData);
        outputStream.flush();

    }

    public static JsonNode responseMessage(Socket socket) throws IOException {
        byte[] data = new byte[1024];
        // receiving message request from client
        InputStream inputStream = socket.getInputStream();
        // reading message response from server
        int bytesRead = inputStream.read(data);
        String jsonData = new String(data, 0, bytesRead);
        System.out.println(jsonData.getClass());
        ObjectMapper objectMapper = new ObjectMapper();
        //ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(jsonData);
        System.out.println(jsonNode);
        return jsonNode;
    }

    public static void displayResponse(JsonNode jsonNode){
        JsonNode result =  jsonNode.get("Status");
        JsonNode data = jsonNode.get("data");
        JsonNode state = jsonNode.get("state");
        System.out.println(result.toString().trim());

        if (result.toString().trim().equalsIgnoreCase("OK")){
            System.out.println("ROBOT SUCCESSFULLY LAUNCHED");
            System.out.println( data.get("position") + "HAL" );
        }
        else if (result.toString().equals("ERROR")){
            System.out.println(data.get("message"));
        }
//        System.out.println(result.toString());
//        System.out.println(data.toString());
//        System.out.println(state.toString());
    }

}