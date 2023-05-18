package za.co.wethinkcode.Server;

import za.co.wethinkcode.Server.World.Robot;

public class Repair implements Runnable{
    private static final String REPAIR = "REPAIR";

    public void execute(Robot target) throws InterruptedException {
        target.setStatus(REPAIR);
        Thread.sleep(10000);
        target.setShields(5);
//        target.setStatus("NORMAL");
    }

    @Override
    public void run() {

    }
}
