package za.co.wethinkcode.Server;

import za.co.wethinkcode.Server.World.Robot;

public class Reload{
    private static final String RELOAD = "RELOAD";

    public void execute(Robot target) throws InterruptedException {
        target.setStatus(RELOAD);
        Thread.sleep(10000);
        target.setRobotShots(5);

    }
}
