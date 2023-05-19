package za.co.wethinkcode.Server;

import za.co.wethinkcode.Server.World.Obstacles;
import za.co.wethinkcode.Server.World.Robot;
import za.co.wethinkcode.Server.World.World;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import static za.co.wethinkcode.Server.World.Status.NORMAL;
import static za.co.wethinkcode.Server.World.World.ListOfObstacles;


public class ServerCommands implements Runnable {
    private static String cyan_bg = ServerGraphics.ANSI_CYAN_BG;
    private static String reset = ServerGraphics.ANSI_RESET;
    private static String black = ServerGraphics.ANSI_BLACK;
    private static String green = ServerGraphics.ANSI_GREEN;
    private static String cyan_bg_bright = ServerGraphics.ANSI_CYAN_BG_BRIGHT;
    private static String red = ServerGraphics.ANSI_RED;
    private static String y_bg = ServerGraphics.ANSI_YELLOW_BG;



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
        System.out.println(green + "world height: " + getWorldHeight() + reset);
        System.out.println(green + "world width: " + getWorldWidth()+ reset);
        System.out.println(green + "World centre: "+"["+World.CenterCoord()[0]+", "+World.CenterCoord()[1]+"]"+ reset);
        System.out.println(green + "World top left: "+"["+World.Top_Left_Boundary()[0]+", "+World.Top_Left_Boundary()[1]+"]"+ reset);
        System.out.println(green + "World bottom right: "+"["+World.Bottom_Right_Boundary()[0]+", "+World.Bottom_Right_Boundary()[1]+"]"+ reset);

        for (int i = 0; i<ListOfObstacles.size();i++) {
            System.out.println(green + "Obstacle at position" + "[" + ListOfObstacles.get(i).getX() + ", " + ListOfObstacles.get(i).getY() + "]" + reset);
        }
        for(int i = 0; i<commandHandler.myRobots.size();i++) {
            System.out.println(green + "robot in world: " + commandHandler.myRobots.get(i).getRobotName() + reset);
            System.out.println(green + "Robot at position " + "[" + commandHandler.myRobots.get(i).getRobotX() + ", " + commandHandler.myRobots.get(i).getRobotY() + "]" + reset);
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
                    System.out.println(red + "Server is shutting down..." + reset);
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

                    System.out.println(cyan_bg + green + "Number of robots in the world: " + commandHandler.getRobots().size() + reset);
                    for (Robot robot : commandHandler.getRobots()) {
                        System.out.println(robot.getRobotName());
                        System.out.println(red + "The state of this robot is: " + reset);
                        System.out.println(red + "   *   Shields: " + robot.getRobotShields()+" hits"+ reset);
                        System.out.println(red +"   *   Position: " + "["+robot.getRobotX()+","+robot.getRobotY()+"]"+ reset);
                        System.out.println(red + "   *   Shots remaining: " + robot.getRobotShots()+" shots"+ reset);
                        System.out.println(red + "   *   Direction: " + robot.getRobotDirection()+ reset);
                        System.out.println(red + "   *   Status: " + robot.getRobotStatus()+ reset);

                    }
                }


            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
