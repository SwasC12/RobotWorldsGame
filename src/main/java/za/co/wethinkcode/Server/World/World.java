package za.co.wethinkcode.Server.World;


import java.util.ArrayList;
import java.util.List;

public class World{
    public static int width = 400;
    public static int height = 400;

    public static int[] Top_Left = new int[2];
    public static int[] Bottom_Right = new int[2];

    public static List<Obstacles> ListOfObstacles = new ArrayList<>();

    public World(){
        addObstacles();
    }


    public void addObstacles(){
        this.ListOfObstacles.add(new Obstacles(10,10));
    }

    public void getObstacles(){
        addObstacles();
    }



    public int []  Top_Left_Boundary() {
        World.Top_Left[0] = -width/2;
        World.Top_Left[1] = height/2;
        return Top_Left;
    }

    public int []  Bottom_Right_Boundary() {
        World.Bottom_Right[0] = width/2;
        World.Bottom_Right[1] = -height/2;
        return Top_Left;
    }



}








