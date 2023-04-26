package za.co.wethinkcode.Server;



import za.co.wethinkcode.Server.World.World;

import java.util.concurrent.ExecutorService;

import java.net.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

public class MultiServers {

    //this attribute is used to manage tasks
    public static ExecutorService manage;
    public static int numberOfClients;

    public static ServerSocket serverSocket;

    public static List<SimpleServer> listOfClientsConnected = new ArrayList<>();

    public static boolean serverInRunningState = true;

     public static CommandHandler commandHandler;
     public static World world;



    public static void main(String[] args) throws ClassNotFoundException, IOException {
        commandHandler = new CommandHandler();
        world = new World();

        manage = Executors.newFixedThreadPool(10);

        serverSocket = new ServerSocket( 5000);
        System.out.println("Server running & waiting for client connections.");
        serverInRunningState = true;

        MultiServers.startCommandHandlerThread();


        while(serverInRunningState) {
            try {

                Socket socket = serverSocket.accept();

                SimpleServer singleServer = new SimpleServer(socket,world, commandHandler);
                listOfClientsConnected.add(singleServer);

                System.out.println("Connection: " + socket);
                manage.submit(singleServer);

            }
            catch(IOException ex) {
                serverInRunningState =false;
                stopRunning();
            }
        }


    }

    public static void startCommandHandlerThread(){
        ServerCommands listener = new ServerCommands(commandHandler);
        Thread thread = new Thread(listener);
        thread.start();
    }

    public static void stopRunning() throws IOException {
        serverInRunningState = false;
        serverSocket.close();
//        System.out.println(listOfClientsConnected.size());
        for (SimpleServer simpleServer :listOfClientsConnected) {
            simpleServer.closeQuietly();
        }
        manage.shutdownNow();
    }
}

