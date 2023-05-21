package za.co.wethinkcode.Server;

import za.co.wethinkcode.Server.World.Robot;
import za.co.wethinkcode.Server.World.Status;

import java.util.ArrayList;
import java.util.concurrent.Executor;
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
                Thread.sleep(30000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            target.setRobotShots(5);
            target.setStatus("NORMAL");
            CommandHandler.reloadRobots.remove(target);
        });
        manage.shutdown();

    }
}
