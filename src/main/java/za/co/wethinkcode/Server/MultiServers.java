package za.co.wethinkcode.Server;


<<<<<<< src/main/java/za/co/wethinkcode/Server/MultiServers.java

import za.co.wethinkcode.Server.World.World;

import java.util.concurrent.ExecutorService;
=======
import za.co.wethinkcode.Server.World.Direction;
import za.co.wethinkcode.Server.World.Robot;
import za.co.wethinkcode.Server.World.Status;
>>>>>>> src/main/java/za/co/wethinkcode/Server/MultiServers.java

import java.net.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
<<<<<<< src/main/java/za/co/wethinkcode/Server/MultiServers.java
import java.util.concurrent.Executors;

public class MultiServers {

    //this attribute is used to manage tasks
    public static ExecutorService manage;
=======

public class MultiServers {
>>>>>>> src/main/java/za/co/wethinkcode/Server/MultiServers.java
    public static int numberOfClients;
    public String keyboardInput;



    public static List<String> ListOfServerCommands = new ArrayList<>(List.of("quit","robots","dump"));


    public static ServerSocket serverSocket;

<<<<<<< src/main/java/za/co/wethinkcode/Server/MultiServers.java
    public static List<SimpleServer> listOfClientsConnected = new ArrayList<>();

    public static boolean serverInRunningState = true;

     public static CommandHandler commandHandler;
     public static World world;



=======
    public static void decrementServerUsers(){
        numberOfClients--;
    }

>>>>>>> src/main/java/za/co/wethinkcode/Server/MultiServers.java
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
<<<<<<< src/main/java/za/co/wethinkcode/Server/MultiServers.java

                Socket socket = serverSocket.accept();

                SimpleServer singleServer = new SimpleServer(socket,world, commandHandler);
                listOfClientsConnected.add(singleServer);

=======
                KeyboardListener listener = new KeyboardListener();
                Thread thread = new Thread(listener);
                thread.start();

                if(listener.getEnteredString().contains("quit")){
                    if (numberOfClients ==1){
                        break;
                    }
                    decrementServerUsers();

                }
                else if (listener.getEnteredString().contains("dump")){
                  dump obj = new dump();
                  obj.Dump();
                  continue;
                }
                else if (listener.getEnteredString().contains("robots")){
                    if (SimpleServer.myRobots.size()>0){
                        System.out.println("Robots in the world are: ");
                        for(int i =0; i<SimpleServer.myRobots.size();i++){
                            System.out.println(SimpleServer.myRobots.get(i).getRobotName());
                        }
                    }
                    else if (SimpleServer.myRobots.size() == 0){
                        System.out.println("There are no robots in the world currently");
                    }
                    continue;
                }


                Socket socket = s.accept();
                incrementServerUsers();
>>>>>>> src/main/java/za/co/wethinkcode/Server/MultiServers.java
                System.out.println("Connection: " + socket);
                manage.submit(singleServer);

<<<<<<< src/main/java/za/co/wethinkcode/Server/MultiServers.java
            }
            catch(IOException ex) {
                serverInRunningState =false;
                stopRunning();
=======

                Runnable r = new SimpleServer(socket);
                Thread task = new Thread(r);
                task.start();


            } catch(IOException ex) {
                ex.printStackTrace();
>>>>>>> src/main/java/za/co/wethinkcode/Server/MultiServers.java
            }
        }


    }
<<<<<<< src/main/java/za/co/wethinkcode/Server/MultiServers.java

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
=======
>>>>>>> src/main/java/za/co/wethinkcode/Server/MultiServers.java
}

