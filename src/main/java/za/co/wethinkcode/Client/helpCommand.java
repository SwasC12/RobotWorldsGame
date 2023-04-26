package za.co.wethinkcode.Client;

public class helpCommand {

    public helpCommand(){
    }

    @Override
    public String toString() {
        return
         "I can understand these commands:\n"+
        "   quit - Shut down robot.\n"+
        "   HELP - provide information about commands.\n"+
        "   LOOK - Look around in the world.\n"+
        "   FORWARD - move forward by specified number of steps, e.g. 'FORWARD 10'.\n"+
        "   BACK - move backward by specified number of steps, e.g. 'BACK 10'.\n"+
        "   TURN - turn right/left by 90 degrees. \n"+
        "   REPAIR - Instruct the robot to repair its shields.\n"+
        "   RELOAD - Instruct the robot to reload its weapons.\n"+
        "   FIRE - Instruct the robot to fire its gun.\n"+
        "   STATE - Ask the world for the state of the robot.\n";
    }
}
