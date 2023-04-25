package za.co.wethinkcode.Server;

import za.co.wethinkcode.Server.World.Obstacles;
import za.co.wethinkcode.Server.World.Robot;
import za.co.wethinkcode.Server.World.World;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import static za.co.wethinkcode.Server.World.World.ListOfObstacles;
//import static za.co.wethinkcode.Server.World.World.listOfRobots;

public class ServerCommands implements Runnable {



    CommandHandler commandHandler;

    public static Obstacles obst;
    public static Robot rob;
    public boolean finishRun;

    public ServerCommands(CommandHandler commandHandler){
        this.commandHandler = commandHandler;
    }


    public  void Dump(){
        /*Dump method displays the worlds representation
     * and everything programmed within and in the 
     * world
     */
        System.out.println("world height: " + getWorldHeight());
        System.out.println("world width: " + getWorldWidth());
        System.out.println("World centre: "+"["+World.CenterCoord()[0]+", "+World.CenterCoord()[1]+"]");
        System.out.println("World top left: "+"["+World.Top_Left_Boundary()[0]+", "+World.Top_Left_Boundary()[1]+"]");
        System.out.println("World bottom right: "+"["+World.Bottom_Right_Boundary()[0]+", "+World.Bottom_Right_Boundary()[1]+"]");

        for (int i = 0; i<ListOfObstacles.size();i++) {
            System.out.println("Obstacle at position" + "[" + ListOfObstacles.get(i).getX() + ", " + ListOfObstacles.get(i).getY() + "]");
        }
        for(int i = 0; i<commandHandler.myRobots.size();i++) {
            System.out.println("robot in world: " + commandHandler.myRobots.get(i).getRobotName());
            System.out.println("Robot at position " + "[" + commandHandler.myRobots.get(i).getRobotX() + ", " + commandHandler.myRobots.get(i).getRobotY() + "]");
        }
    }

    public int getWorldHeight(){
        int worldHeight = World.height;
        return worldHeight;
    }

    public int getWorldWidth(){
        int worldWidth = World.width;
        return worldWidth;
    }

    public void robots() {
        for (Robot robot : commandHandler.getRobots()) {
            System.out.println(robot.getRobotName());
        }
    }

    public static void quit() throws IOException {
        //stops multiServer --> world
        MultiServers.stopRunning();
    }

    @Override
    public void run() {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            finishRun = false;
            String line;
            while (!finishRun) {
                line = reader.readLine();
                if (line.contains("quit")) {
                    // perform quit action
                    ServerCommands.quit();
                    //server shutdown for individual client
                    System.out.println("Server is shutting down...");
                    finishRun = true;
                    break;
                }
                else if (line.contains("dump")) {
                    // perform dump action
                    ServerCommands obj = new ServerCommands(commandHandler);
                    obj.Dump();

                }
                else if (line.contains("robots")) {
                    // perform robots action

                    System.out.println("Number of robots in the world: " + commandHandler.getRobots().size());
                    for (Robot robot : commandHandler.getRobots()) {
                        System.out.println(robot.getRobotName());
                    }
                }

            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
