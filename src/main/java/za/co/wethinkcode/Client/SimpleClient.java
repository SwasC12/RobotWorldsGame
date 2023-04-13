package za.co.wethinkcode.Client;

/*

import org.json.simple.JSONObject;

import java.net.*;
import java.io.*;
import java.util.Scanner;

public class SimpleClient {
    public static void main(String args[]) {
        try (
                Socket socket = new Socket("localhost", 5000);
                PrintStream out = new PrintStream(socket.getOutputStream());
                BufferedReader in = new BufferedReader(new InputStreamReader(
                        socket.getInputStream()));
        )
        {
            out.println("Hello WeThinkCode");

            out.flush();
            String messageFromServer = in.readLine();
            System.out.println("Response: "+messageFromServer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
 */

//package za.co.wethinkcode.examples.sever;

import java.net.*;
import java.io.*;
import java.util.Scanner;
//import org.json.simple.JSONObject;
public class SimpleClient {
    private static DataOutputStream toServer;
    private static DataInputStream fromServer;
    private String robotName;
    private String command;
    static Scanner scanner;
//
//    public SimpleClient(String robotName, String command, String ipAddress, int portnumber) {
//        this.robotName = robotName;
//        this.command = command;
//    }

    public SimpleClient() {

    }

    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("java ClientExample <ipAddress> <port>");
            return;
        }

        String ipAddress = args[0];
        int port = Integer.parseInt(args[1]);
        try (
                //"20.20.15.160"
                //"192.168.1.35"
                //20.20.15.149
                //Socket socket = new Socket("localhost", 5000);
                Socket socket = new Socket(ipAddress, port);
                PrintStream out = new PrintStream(socket.getOutputStream());
                BufferedReader in = new BufferedReader(new InputStreamReader(
                        socket.getInputStream()));
        )
        {
            toServer = new DataOutputStream(socket.getOutputStream());
            fromServer = new DataInputStream(socket.getInputStream());
            out.println("Hello From Client:");
            out.flush();

            scanner = new Scanner(System.in);
            String name = getInput("What do you want to name your robot?");
            String command = getInput(  "> What must I do next?").strip().toLowerCase();

//            SimpleClient simpleClient = new SimpleClient();
//            String[] arrayArgs;
//            if (command.split(" ").length==1){
//                 arrayArgs = new String[]{};
//            }else{
//                 arrayArgs =  command.split(" ")[1].split(" ");}
//
//            Object msg_toServer = simpleClient.requestMessage(name,command.split(" ")[0], arrayArgs);
//            System.out.println(msg_toServer);
//            System.out.println(arrayArgs.length);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String getInput(String prompt) {
        System.out.println(prompt);
        String input = scanner.nextLine();
        while (input.isBlank()) {
            System.out.println(prompt);
            input = scanner.nextLine();
        }
        return input;
    }
//
//    private Object requestMessage(String robotName, String commandName, Object[] arguments)  {
//        JSONObject jsonRequest = new JSONObject();
//        jsonRequest.put("robot", robotName);
//        jsonRequest.put("arguments", arguments);
//        jsonRequest.put("command", commandName);
//        return jsonRequest;
//}
}