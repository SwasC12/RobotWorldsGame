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

    public static Command create(String instruction) {
        String[] args = instruction.toLowerCase().trim().split(" ");

        switch (args[0]){
            case "forward":
                return new Forward(args[1]);
            case "back":
                return new Back(args[1]);
            case "turn":
                return new Turn(args[1]);
            case "fire":
                return new Fire();

            default:
                throw new IllegalArgumentException("Unsupported command: " + instruction);
        }
    }
}
