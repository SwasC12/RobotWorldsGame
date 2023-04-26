package za.co.wethinkcode.Server;

import za.co.wethinkcode.Server.World.Robot;

public class Fire extends Command{
    public Fire() {
        super("fire");
    }

    @Override
    public boolean execute(Robot target) {
        return false;
    }
}
