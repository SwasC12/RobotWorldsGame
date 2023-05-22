package za.co.wethinkcode.ServerTest;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;


import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import za.co.wethinkcode.Server.MultiServers;
import za.co.wethinkcode.Server.ServerCommands;
import za.co.wethinkcode.Server.ServerGraphics;
import za.co.wethinkcode.Server.World.Obstacles;
import za.co.wethinkcode.Server.World.Robot;
import za.co.wethinkcode.Server.World.World;

import java.io.*;
import java.net.ServerSocket;
import java.util.concurrent.ExecutorService;

import static java.lang.String.valueOf;
import static org.junit.jupiter.api.Assertions.*;

import static za.co.wethinkcode.Server.CommandHandler.myRobots;
import static za.co.wethinkcode.Server.World.Status.NORMAL;
import static za.co.wethinkcode.Server.World.World.ListOfObstacles;

public class dumpTest {

    private static String reset = ServerGraphics.ANSI_RESET;
    private static String green = ServerGraphics.ANSI_GREEN;



    @Test
    void TestDump() {
        Robot robot = new Robot("ROBOT", "khetha", 1, 1, "OK", 10, 10, 10, 10, "UP", 9, valueOf(NORMAL));
        World wd = new World();
        ServerCommands ser = new ServerCommands();
        ListOfObstacles.clear();
        myRobots.clear();

        // Create an instance of your World class


        // Create a ByteArrayOutputStream to capture console output
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream);

        // Replace the System.out with the PrintStream
        PrintStream originalPrintStream = System.out;
        System.setOut(printStream);

        // Call the Dump() method
        ser.Dump();

        // Restore the original System.out
        System.setOut(originalPrintStream);

        // Get the captured console output
        String consoleOutput = outputStream.toString();

        // Define the expected output
        String expectedOutput = green + "world height: 400"+reset+"\n"
                + green +"world width: 400"+reset+"\n"
                + green +"World centre: [0, 0]"+reset+"\n"
                + green +"World top left: [-200, 200]"+reset+"\n"
                + green +"World bottom right: [200, -200]"+reset+"\n";


        // Compare the actual output with the expected output
        assertEquals(expectedOutput, consoleOutput);
    }


}
