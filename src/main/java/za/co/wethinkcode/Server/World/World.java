package za.co.wethinkcode.Server.World;


import java.util.ArrayList;
import java.util.List;

public class World{
    public static int width = 800;
    public static int height = 800;
    public   static int[] Centre = new int[2];

    public static int[] Top_Left = new int[2];
    public static int[] Bottom_Right = new int[2];

    public static List<Obstacles> ListOfObstacles = new ArrayList<>();

    public  static List <Robot> listOfRobots = new ArrayList<>();
    public Robot rb;

    public World(){
        addObstacles();
    }



    public static void addObstacles(){

        ListOfObstacles.add(new Obstacles("OBSTACLE",10,10));
    }

    public static int []  Top_Left_Boundary() {
        World.Top_Left[0] = -width/2;
        World.Top_Left[1] = height/2;
        return Top_Left;
    }

    public static int []  Bottom_Right_Boundary() {
        World.Bottom_Right[0] = width/2;
        World.Bottom_Right[1] = -height/2;
        return Bottom_Right;
    }

    public static int [] CenterCoord(){
        World.Centre[0] = 0;
        World.Centre[1] = 0;
        return Centre;
    }

    public static List<Obstacles> getWorldObstacles(){
        return ListOfObstacles;
    }


}








