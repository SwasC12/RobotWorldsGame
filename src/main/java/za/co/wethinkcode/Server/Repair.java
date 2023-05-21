package za.co.wethinkcode.Server;

import za.co.wethinkcode.Server.World.Robot;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static za.co.wethinkcode.Server.World.Status.REPAIR;


public class Repair {
    private static final String Repair = "REPAIR";
    public static ExecutorService manage;

    public void execute(Robot target) throws InterruptedException {

        CommandHandler.repairRobots.add(target);

        manage = Executors.newSingleThreadExecutor();
        manage.submit(() -> {
            target.setStatus("REPAIR");
            try {
                Thread.sleep(30000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            target.setRobotShots(5);
            target.setStatus("NORMAL");
            CommandHandler.repairRobots.remove(target);
        });
        manage.shutdown();
    }
}
