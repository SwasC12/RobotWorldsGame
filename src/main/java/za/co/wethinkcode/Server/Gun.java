package za.co.wethinkcode.Server;

import za.co.wethinkcode.Server.World.Robot;

public class Gun {
    private int numShots;

    Robot robot;
    private final int shotMaxDistance=50;

    public Gun (int numShots, Robot robot){
        this.numShots=numShots;
        this.robot=robot;
    }

    public Gun (){

    }
    public int getNumShots() {
        if (this.numShots <=0){
            return 0;
        }
        else if (numShots >= shotMaxDistance ) {
            return shotMaxDistance;
        }else{
            return numShots;
        }
    }

    public int getShotDistance() {

        int shotDistance;
        if (numShots <=0){
            shotDistance = 0;
        }
        else if (numShots >=shotMaxDistance){
            shotDistance = 1;
        }
        else{
            shotDistance = shotMaxDistance - this.numShots + 1;
        }
        return shotDistance;
    }
}
