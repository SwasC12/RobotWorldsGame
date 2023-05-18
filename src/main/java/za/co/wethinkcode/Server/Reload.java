package za.co.wethinkcode.Server;

import za.co.wethinkcode.Server.World.Robot;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class Reload {
    private static final String RELOAD = "RELOAD";
    public static ExecutorService manage;

    public void execute(Robot target) throws InterruptedException {

        manage = Executors.newSingleThreadExecutor();
        manage.submit(()->{
            target.setStatus(RELOAD);
            try {
                Thread.sleep(30000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            target.setRobotShots(5);
            target.setStatus("NORMAL");

        });
        manage.shutdown();
//        target.setStatus(RELOAD);
//        Thread.sleep(30000);
//        target.setRobotShots(5);
////        target.setStatus("NORMAL");
    }

//    @Override
//    public void run() {
//
//    }
}
