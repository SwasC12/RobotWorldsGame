package za.co.wethinkcode.Server;

import za.co.wethinkcode.Server.World.Robot;

public class Forward extends Command {
    public Forward(String argument) {
        super("forward", argument);
    }

    @Override
    public boolean execute(Robot target) {
        return false;
    }
}
