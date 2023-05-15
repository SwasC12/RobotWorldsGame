package za.co.wethinkcode.Server;

import za.co.wethinkcode.Server.World.Robot;

public class Reload extends Command{
    private static final String RELOAD = "Reloading";

    public boolean execute(Robot target) throws InterruptedException {
        target.setStatus(RELOAD);
        Thread.sleep(10000);
        target.setRobotShots(5);
        return true;
    }
}
