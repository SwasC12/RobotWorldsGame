package za.co.wethinkcode.Server.World;

import java.util.ArrayList;
import java.util.List;

public class Obstacles {
    int x;
    int y;


    public Obstacles(int x,int y){
        this.x = x;
        this.y = y;
    }

    public int getX(){
        return this.x;
    }

    public int getY(){
        return this.y;
    }

//    public static void main(String[] args) {
//        Obstacles obs = new Obstacles(12,12);
//        System.out.println(obs.x);
//
//    }

}
