package za.co.wethinkcode.Server;

import za.co.wethinkcode.Server.World.Robot;

public class BulletPosition {
    private int xi;
    private int yi;
    Robot robot;
    private String direction;
    //distance a bullet can travel ((steps))
    private int distance;

    private int xf;
    private int yf;


    public BulletPosition(int distance, Robot robot, int xi, int yi){
        this.distance = distance;
        this.robot = robot;
        this.xi = xi;
        this.yi = yi;
    }

    public int getXi() {
        return robot.getRobotX();
    }
    public int getYi() {
        return robot.getRobotY();
    }

    public int getXf() {
        if (robot.getRobotDirection().equalsIgnoreCase("UP") || robot.getRobotDirection().equalsIgnoreCase("DOWN")   ) {
            this.xf = this.xi;
        } else if (robot.getRobotDirection().equalsIgnoreCase("RIGHT")){
            this.xf = this.distance+this.xi;
        } else {
            this.xf = -this.distance+this.xi;
        }
        return xf;
    }
    public int getYf() {
        if (robot.getRobotDirection().equalsIgnoreCase("LEFT") || robot.getRobotDirection().equalsIgnoreCase("RIGHT") ) {
            this.yf=this.yi;
        }
        else if (robot.getRobotDirection().equalsIgnoreCase("UP") ) {
            this.yf = this.distance+this.yi;
        }else{
            this.yf = -this.distance+this.yi;
        }
        return yf;
    }
    public String getDirection() {
        return robot.getRobotDirection();
    }

}
