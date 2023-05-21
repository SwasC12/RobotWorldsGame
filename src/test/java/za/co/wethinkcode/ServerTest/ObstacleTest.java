package za.co.wethinkcode.ServerTest;

import org.junit.jupiter.api.Test;
import za.co.wethinkcode.Server.World.Obstacles;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ObstacleTest {
    @Test
    public void testGetType() {
        String type = "OBSTACLE";
        int x = 10;
        int y = 20;
        Obstacles obstacle = new Obstacles(type, x, y);

        assertEquals(type, obstacle.getType());
    }

    @Test
    public void testGetDirection() {
        String type = "OBSTACLE";
        int x = 10;
        int y = 20;
        Obstacles obstacle = new Obstacles(type, x, y);

        // Assuming the direction is not set in the constructor
        assertEquals(null, obstacle.getDirection());
    }

    @Test
    public void testGetX() {
        String type = "OBSTACLE";
        int x = 10;
        int y = 20;
        Obstacles obstacle = new Obstacles(type, x, y);

        assertEquals(x, obstacle.getX());
    }

    @Test
    public void testGetY() {
        String type = "OBSTACLE";
        int x = 10;
        int y = 20;
        Obstacles obstacle = new Obstacles(type, x, y);

        assertEquals(y, obstacle.getY());
    }
}