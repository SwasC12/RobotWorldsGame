package za.co.wethinkcode.Server.World;


public class Obstacles {
    int x;
    int y;

    String direction;

    String type;


    public Obstacles(String type,int x,int y){
        this.x = x;
        this.y = y;
        this.type = type;
        this.direction = direction;
    }

    public String getType(){
        return this.type;
    }

    public String getDirection(){
        return this.direction;
    }

    public int getX(){
        return this.x;
    }

    public int getY(){
        return this.y;
    }


}
