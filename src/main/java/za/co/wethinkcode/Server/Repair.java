package za.co.wethinkcode.Server;

import za.co.wethinkcode.Server.World.Robot;

public class Repair{
    private static final String REPAIR = "Repairing";

    public void execute(Robot target) throws InterruptedException {
        target.setStatus(REPAIR);
        Thread.sleep(10000);
        target.setShields(5);
    }
}
