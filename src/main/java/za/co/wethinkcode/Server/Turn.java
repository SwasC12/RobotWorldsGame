package za.co.wethinkcode.Server;

import za.co.wethinkcode.Server.World.Robot;

public class Turn extends Command {

    public Turn(String argument) {
        super("turn",argument) ;
    }

    @Override
    public boolean execute(Robot target) {
        return false;
    }
}
