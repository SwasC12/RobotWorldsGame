package za.co.wethinkcode.Server;


import org.json.simple.JSONObject;

import java.net.*;
import java.io.*;

public class MultiServers implements Serializable{
    public static int numberOfClients;

    public static void incrementServerUsers(){
        numberOfClients++;
    }

    public void decrementServerUsers(){
        numberOfClients--;
    }




    public static void main(String[] args) throws ClassNotFoundException, IOException {
        numberOfClients = 0;

        ServerSocket s = new ServerSocket( 5000);
        System.out.println("Server running & waiting for client connections.");

        while(true) {
            try {
                Socket socket = s.accept();
                incrementServerUsers();
                System.out.println("Connection: " + socket);

                Runnable r = new SimpleServer(socket);
                Thread task = new Thread(r);
                task.start();
                System.out.println(numberOfClients);
            } catch(IOException ex) {
                ex.printStackTrace();
            }
        }


    }

}
