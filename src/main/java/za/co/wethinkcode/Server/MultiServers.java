package za.co.wethinkcode.Server;


import za.co.wethinkcode.Server.World.Direction;
import za.co.wethinkcode.Server.World.Robot;
import za.co.wethinkcode.Server.World.Status;

import java.net.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class MultiServers {
    public static int numberOfClients;
    public String keyboardInput;



    public static List<String> ListOfServerCommands = new ArrayList<>(List.of("quit","robots","dump"));


    public static void incrementServerUsers(){
        numberOfClients++;
    }

    public static void decrementServerUsers(){
        numberOfClients--;
    }

    public static void main(String[] args) throws ClassNotFoundException, IOException {
        numberOfClients = 0;

        ServerSocket s = new ServerSocket( 5000);
        System.out.println("Server running & waiting for client connections.");

        while(true) {
            try {
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
                System.out.println("Connection: " + socket);


                Runnable r = new SimpleServer(socket);
                Thread task = new Thread(r);
                task.start();


            } catch(IOException ex) {
                ex.printStackTrace();
            }
        }


    }
}
