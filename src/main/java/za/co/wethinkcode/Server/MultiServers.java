package za.co.wethinkcode.Server;



import org.json.simple.JSONObject;

import za.co.wethinkcode.Server.World.Status;
import za.co.wethinkcode.Server.World.World;

import java.util.concurrent.ExecutorService;

import java.net.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.jar.JarException;

public class MultiServers {
    private static FileWriter file;
    public static int Width;
    public static int Height;


    //this attribute is used to manage tasks
    public static ExecutorService manage;
    public static int numberOfClients;

    public static ServerSocket serverSocket;

    public static List<SimpleServer> listOfClientsConnected = new ArrayList<>();

    public static boolean serverInRunningState = true;

    public static CommandHandler commandHandler;
    public static World world;

    public static void main(String[] args) throws ClassNotFoundException, IOException {

        JSONObject json = new JSONObject();
        if (args.length <3 ){
            System.out.println("You did not enter any configuration parameters thus we will use default values.");

        }

        else if (args.length == 3) {
            json.put("width", args[0]);
            json.put("height", args[1]);
            json.put("lookDistance", args[2]);
        }


        try{
            file = new FileWriter("src/main/java/za/co/wethinkcode/Server/World/config.json");
//            file = new FileWriter("/home/khetha/student_work/dbn11_robot_worlds/src/main/java/za/co/wethinkcode/Server/World/config.json");
            file.write(json.toString());
        }catch (Exception e){
            e.printStackTrace();
        }finally{
            try{
                file.flush();
                file.close();
            }catch (IOException e){
                e.printStackTrace();
            }
        }


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
                stopRunning();

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
        manage.shutdownNow();
    }
}

