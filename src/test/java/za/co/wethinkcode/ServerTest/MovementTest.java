package za.co.wethinkcode.ServerTest;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
// import java.ByteArrayInputStream;
import java.io.*;

import org.w3c.dom.ls.LSOutput;
import za.co.wethinkcode.Server.*;
import za.co.wethinkcode.Server.World.Robot;
import za.co.wethinkcode.Server.World.World;

import static java.lang.String.valueOf;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static za.co.wethinkcode.Server.CommandHandler.myRobots;
import static za.co.wethinkcode.Server.World.Status.NORMAL;


public class MovementTest {
    @Test
    void TestForward(){
        World w = new World();
        Robot robot = new Robot("ROBOT", "khetha", 1, 1, "OK", 10, 10, 10, 10, "UP", 9, valueOf(NORMAL));
        myRobots.add(robot);
        Forward.execute(1,"khetha");
        assertEquals(robot.getRobotY(),2);
        myRobots.clear();
    }

    @Test
    void TestBack(){
        World w = new World();
        Robot robot = new Robot("ROBOT", "khetha", 1, 1, "OK", 10, 10, 10, 10, "UP", 9, valueOf(NORMAL));
        myRobots.add(robot);
        Back.execute(1,"khetha");
        assertEquals(robot.getRobotY(),0);
        myRobots.clear();
    }
    
    @Test
    void TestLeft(){
        World w = new World();
        Robot robot = new Robot("ROBOT", "khetha", 1, 1, "OK", 10, 10, 10, 10, "UP", 9, valueOf(NORMAL));
        myRobots.add(robot);
        Turn.execute("left","khetha");
        assertEquals(robot.getRobotDirection(),"LEFT");
        myRobots.clear();

    }

    @Test 
    void Testright(){
        World w = new World();
        Robot robot = new Robot("ROBOT", "khetha", 1, 1, "OK", 10, 10, 10, 10, "UP", 9, valueOf(NORMAL));
        myRobots.add(robot);
        Turn.execute("right","khetha");
        assertEquals(robot.getRobotDirection(),"RIGHT");
        myRobots.clear();
        
    }

    }


    
