package za.co.wethinkcode.Server.World;


import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static za.co.wethinkcode.Server.World.Robot.robotBlockedPathByObstacle;

public class World {
    FileReader reader;
    JSONObject data;
    public static List<Edge> listOfEdges;
    public static Random random = new Random();
    public static int width ;
    public static int height;
    public static int lookDistance;
    public   static int[] Centre = new int[2];

    public static int[] Top_Left = new int[2];
    public static int[] Bottom_Right = new int[2];

    public static List<Obstacles> ListOfObstacles = new ArrayList<>();

    public  static List <Robot> listOfRobots = new ArrayList<>();
    public Robot rb;

    public World(){
        ReadConfigFile();
        addObstacles();
    }



    public static void addObstacles(){
        int X;
        int Y;
        while(ListOfObstacles.size()<20){
            X = random.nextInt(width)-width/2;
            Y = random.nextInt(height)-height/2;
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


    public void ReadConfigFile(){
        JSONParser parser = new JSONParser();
        try{

            reader = new FileReader("src/main/java/za/co/wethinkcode/Server/World/config.json");
          //  reader = new FileReader("/home/wtc/student_work/dbn11_robot_worlds/src/main/java/za/co/wethinkcode/Server/World/config.json");
            data = (JSONObject) parser.parse(reader);
            if (data.size() == 0){
                reader = new FileReader("src/main/java/za/co/wethinkcode/Server/World/default.json");
//                reader = new FileReader("/home/wtc/student_work/dbn11_robot_worlds/src/main/java/za/co/wethinkcode/Server/World/default.json");
                data = (JSONObject) parser.parse(reader);
            }

            height = Integer.parseInt(data.get("height").toString());
            width = Integer.parseInt(data.get("width").toString());
            lookDistance =  Integer.parseInt(data.get("lookDistance").toString());

            reader.close();
        }catch (IOException | ParseException e){
            e.printStackTrace();
        }

    }

}








