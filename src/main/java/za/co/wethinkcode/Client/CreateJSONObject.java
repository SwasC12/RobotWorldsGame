package za.co.wethinkcode.Client;

import org.json.simple.JSONObject;

import java.util.Arrays;

public class CreateJSONObject {
    private String robotName;
    private String command;

    public CreateJSONObject(){
        this.command = "launch";
    }

    public void setRobotName(String robotName) {
        this.robotName = robotName;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public String getRobotName() {
        return robotName;
    }

    public String getCommand() {
        return command;
    }

    public String[] getArguments() {
        String[] arguments;
        if (this.command.split(" ").length == 1) {
            arguments = new String[]{};
        } else {
            arguments = Arrays.copyOfRange(this.command.split(" "), 1, this.command.split(" ").length);
        }
        return arguments;
    }

    public JSONObject getJsonObject() {
        JSONObject jsonRequest = new JSONObject();
        jsonRequest.put("robot", this.robotName);
        jsonRequest.put("arguments", Arrays.toString(getArguments()));
        jsonRequest.put("command", this.command.split(" ")[0]);
        return jsonRequest;
    }
}
