package za.co.wethinkcode.Server;

import za.co.wethinkcode.Server.World.Robot;

public class Gun {
    private int numShots;

    Robot robot;
    private int shotDistance;
    private int shotMaxDistance=10;

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

        if (numShots <=0){
            this.shotDistance = 0;
        }
        else if (numShots >=shotMaxDistance){
            this.shotDistance = 1;
        }
        else{
            this.shotDistance = shotMaxDistance - this.numShots + 1;
        }
        return shotDistance;
    }
}
