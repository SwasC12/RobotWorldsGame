package za.co.wethinkcode.Server;

import za.co.wethinkcode.Server.World.Robot;

public class Back extends Command {

    public Back(String argument) {
        super("back", argument);
    }

    @Override
    public boolean execute(Robot target) {
        return false;
    }

}
