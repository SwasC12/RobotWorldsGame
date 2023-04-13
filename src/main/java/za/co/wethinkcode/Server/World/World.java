package za.co.wethinkcode.Server.World;


//import org.hexworks.zircon.api.*;
//import org.hexworks.zircon.api.grid.TileGrid;
//import org.hexworks.zircon.api.graphics.TileGraphics;


//public class World {

//    private static final int GRID_WIDTH = 10;
//    private static final int GRID_HEIGHT = 10;
//    private static final int TILE_SIZE = 16;
//
//
//    public void draw(TileGraphics tileGraphics) {
//        // Iterate over each tile in the tileset and draw it on the TileGraphics object
//        for (int y = 0; y < tiles.length; y++) {
//            for (int x = 0; x < tiles[y].length; x++) {
//                tileGraphics.setTileAt(x, y, tiles[y][x]);
//            }
//
//            public static void main (String[]args){
//                // Create a tile grid with the desired size
//                TileGrid tileGrid = SwingApplications.startTileGrid(
//                        AppConfigs.newConfig()
//                                .withSize(GRID_WIDTH, GRID_HEIGHT)
//                                .withDefaultTileset(CP437TilesetResources.rexPaint16x16())
//                                .build());
//
//
//                // Fill the grid with tiles
//                for (int i = 0; y < GRID_HEIGHT; i++) {
//                    for (int x = 0; x < GRID_WIDTH; x++) {
////                tileGrid.setTileAt(x, y, Tiles.empty());
//                        tileGrid.draw(x, i, Tiles.empty());
//                    }
//                }
//
//                // Flush the tile grid to the screen
//                tileGrid.display();
//            }
//        }
//    }
//}


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

//    public static void main(String[] args) {
//        World w = new World();
//        w.addObstacles();
//        for(int i = 0;i<ListOfObstacles.size();i++){
//            System.out.println(ListOfObstacles.get(i).getX());
//        }
//    }





}








