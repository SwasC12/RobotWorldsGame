package za.co.wethinkcode.Server;

import za.co.wethinkcode.Server.World.Robot;
import za.co.wethinkcode.Server.World.World;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class Reload {
    private static final String RELOAD = "RELOAD";
    public static ExecutorService manage;
    public static CommandHandler commandHandler;

    public void execute(Robot target) throws InterruptedException {



        manage = Executors.newSingleThreadExecutor();
        manage.submit(()->{
            target.setStatus("RELOAD");
            try {
                CommandHandler.reloadRobots.add(target);
                Thread.sleep(World.reloadTime);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            target.setRobotShots(target.getMaxShots());
            target.setStatus("NORMAL");
            CommandHandler.reloadRobots.remove(target);
        });
        manage.shutdown();

    }
}
