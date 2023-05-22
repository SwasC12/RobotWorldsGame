package za.co.wethinkcode.Server;

import za.co.wethinkcode.Server.World.Obstacles;
import za.co.wethinkcode.Server.World.Robot;
import za.co.wethinkcode.Server.World.World;
import za.co.wethinkcode.Server.ServerGraphics;


public class dump {
    private static String cyan_bg = ServerGraphics.ANSI_CYAN_BG;
    private static String reset = ServerGraphics.ANSI_RESET;
    private static String black = ServerGraphics.ANSI_BLACK;
    private static String green = ServerGraphics.ANSI_GREEN;
    private static String cyan_bg_bright = ServerGraphics.ANSI_CYAN_BG_BRIGHT;
    private static String red = ServerGraphics.ANSI_RED;
    private static String y_bg = ServerGraphics.ANSI_YELLOW_BG;

    public static Obstacles obst;
    public static Robot rob;
    /*Dump method displays the worlds representation
     * and everything programmed within and in the 
     * world
     */
    public  void Dump(){
        System.out.println(cyan_bg + black + "Dump command from server in action" +reset);
        System.out.println(green + "world height" + getWorldHeight() + reset);
        System.out.println(green + "world width:" + getWorldWidth()+ reset);
        System.out.println(green + "robots in world: " + rob.getRobotName()+ reset);
        System.out.println(green + "Obstacle at position"+"["+ obst.getX()+", "+obst.getY()+"]"+ reset);
        System.out.println(green + "Robot at position "+"["+rob.getRobotX()+", "+rob.getRobotY()+"]"+ reset);
    }

    public int getWorldHeight(){
        int worldHeight = World.height;
        return worldHeight;
    }

    public int getWorldWidth(){
        int worldWidth = World.width;
        return worldWidth;
    }

    
}
