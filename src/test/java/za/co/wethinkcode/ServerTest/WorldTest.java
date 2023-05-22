package za.co.wethinkcode.ServerTest;

import org.junit.jupiter.api.Test;
import za.co.wethinkcode.Server.World.World;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class WorldTest {
    @Test
    public void testAddObstacles() {
        World wd = new World();
        assertEquals(20, World.ListOfObstacles.size());
    }

    @Test
    public void testTopLeftBoundary() {
        int width = 100;
        int height = 100;
        World.width = width;
        World.height = height;

        int[] topLeft = World.Top_Left_Boundary();

        assertEquals(-width / 2, topLeft[0]);
        assertEquals(height / 2, topLeft[1]);
    }

    @Test
    public void testBottomRightBoundary() {
        int width = 100;
        int height = 100;
        World.width = width;
        World.height = height;

        int[] bottomRight = World.Bottom_Right_Boundary();

        assertEquals(width / 2, bottomRight[0]);
        assertEquals(-height / 2, bottomRight[1]);
    }

    @Test
    public void testCenterCoord() {
        int[] center = World.CenterCoord();

        assertEquals(0, center[0]);
        assertEquals(0, center[1]);
    }

    @Test
    public void testReadConfigFile() throws Exception {

        World world = new World();


        assertEquals(400, World.height);
        assertEquals(400, World.width);
        assertEquals(5, World.lookDistance);
        assertEquals(30000, World.repairTime);
        assertEquals(30000, World.reloadTime);
        assertEquals(5, World.maxShieldStrength);
    }
}