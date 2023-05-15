package za.co.wethinkcode.Server;

import za.co.wethinkcode.Server.World.Robot;

public class Repair extends Command{
    private static final String REPAIR = "Repairing";

    public boolean execute(Robot target) throws InterruptedException {
        target.setStatus(REPAIR);
        Thread.sleep(10000);
        target.setShields(5);
        return true;
    }
}
