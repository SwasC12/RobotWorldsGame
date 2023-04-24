package za.co.wethinkcode.Server;

import za.co.wethinkcode.Server.World.Obstacles;
import za.co.wethinkcode.Server.World.Robot;
import za.co.wethinkcode.Server.World.World;


public class dump {

    public static Obstacles obst;
    public static Robot rob;
    /*Dump method displays the worlds representation
     * and everything programmed within and in the 
     * world
     */
    public  void Dump(){
        System.out.println("Dump command from server in action");
        System.out.println("world height" + getWorldHeight());
        System.out.println("world width:" + getWorldWidth());
        System.out.println("robots in world: " + rob.getRobotName());
        System.out.println("Obstacle at position"+"["+ obst.getX()+", "+obst.getY()+"]");
        System.out.println("Robot at position "+"["+rob.getRobotX()+", "+rob.getRobotY()+"]");
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
