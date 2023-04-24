package za.co.wethinkcode.Client;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.simple.JSONObject;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class ClientRequestandResponse implements Serializable {
    public ClientRequestandResponse(){
    }
    public JsonNode sendRequestToServer(JSONObject jsonRequest, Socket socket) throws IOException, ClassNotFoundException {

             //Send request to server
             ObjectOutputStream toServer = new ObjectOutputStream(socket.getOutputStream());
             toServer.writeObject(jsonRequest);
             toServer.flush();

             //Read response message from the server
             ObjectInputStream fromServer = new ObjectInputStream(socket.getInputStream());
             Object response =  fromServer.readObject();
             String jsonData = response.toString();
             ObjectMapper objectMapper = new ObjectMapper();
             return objectMapper.readTree(jsonData);

    }
}

