package za.co.wethinkcode.Server;


import org.json.simple.JSONObject;

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;


public class SimpleServer implements Runnable {

    public static final int PORT = 5000;
    private final BufferedReader in;
    private final PrintStream out;

    private static DataInputStream fromClient;
    private final String clientMachine;

    public SimpleServer(Socket socket) throws IOException, ClassNotFoundException {
        clientMachine = socket.getInetAddress().getHostName();
        System.out.println("Connection from " + clientMachine);

        out = new PrintStream(socket.getOutputStream());
        in = new BufferedReader(new InputStreamReader(
                socket.getInputStream()));
        System.out.println("Waiting for client...");

        fromClient = new DataInputStream(socket.getInputStream());

        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        System.out.println("Received data: " );

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

        JsonNode rootNode = objectMapper.readTree(jsonData);
        String robotName = rootNode.get("robot").asText();
        String command = rootNode.get("command").asText();
        String arguments = rootNode.get("arguments").asText();
        System.out.println("RobotNAME: "+ robotName);
        System.out.println("Command: "+ command);
        System.out.println("Arguments: "+ arguments);

        // get the output stream from the socket.
        OutputStream outputStream  = socket.getOutputStream();
        JSONObject jsonResponse = new JSONObject();
        jsonResponse.put("Status", "HAL launched successfully");
        jsonResponse.put("position", "[x,y]");

        //output to send to client
        byte[] responseData = jsonResponse.toString().getBytes(StandardCharsets.UTF_8);

        //SEND REQUEST
        outputStream.write(responseData);
        outputStream.flush();

    }

    public void run() {
        try {
            String messageFromClient;
            while((messageFromClient = in.readLine()) != null) {
                //System.out.println("Message \"" + messageFromClient + "\" from " + clientMachine);
               // out.println("Thanks for this message: "+messageFromClient);
            }
        } catch(IOException ex) {
            System.out.println("Shutting down single client server");
        } finally {
            closeQuietly();
        }
    }

    private void closeQuietly() {
        try { in.close(); out.close();
        } catch(IOException ex) {}
    }
}
