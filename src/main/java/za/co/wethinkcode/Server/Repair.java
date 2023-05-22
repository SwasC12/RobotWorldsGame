package za.co.wethinkcode.Server;

import za.co.wethinkcode.Server.World.Robot;
import za.co.wethinkcode.Server.World.World;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class Repair {
    private static final String Repair = "REPAIR";
    public static ExecutorService manage;

    public void execute(Robot target) throws InterruptedException {
        CommandHandler.repairRobots.add(target);
        manage = Executors.newSingleThreadExecutor();
        manage.submit(() -> {
            target.setStatus("REPAIR");
            try {

                Thread.sleep(World.repairTime);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            target.setRobotShots(target.getMaxShield());
            target.setStatus("NORMAL");
            CommandHandler.repairRobots.remove(target);
        });
        manage.shutdown();
    }
}
