package za.co.wethinkcode.Server.World;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static za.co.wethinkcode.Server.World.Robot.robotBlockedPathByObstacle;

public class World {
    public static Random random = new Random();
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
        int X;
        int Y;
        while(ListOfObstacles.size()<20){
            X = random.nextInt(800)-400;
            Y = random.nextInt(800)-400;
//            X = 0;
//            Y = 0;
            Position point = new Position(X,Y);
            if (!robotBlockedPathByObstacle(point)){
                ListOfObstacles.add(new Obstacles("OBSTACLE",X,Y));
            }
        }
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








