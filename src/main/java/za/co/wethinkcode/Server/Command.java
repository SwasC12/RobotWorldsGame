package za.co.wethinkcode.Server;

import za.co.wethinkcode.Server.World.Robot;

public abstract class Command {
    private String name;
    private String argument;

    public Command(String name){
        this.name = name.trim().toLowerCase();
        this.argument="";
    }

    public abstract boolean execute(Robot target);

    public Command(String name, String argument){
        this.name=name;
        this.argument=argument.trim().toLowerCase();
    }

    public String getName() {
        return name;
    }

    public String getArgument() {
        return argument;
    }

//    public static Command create(String instruction) {

}
