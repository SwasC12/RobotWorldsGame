package za.co.wethinkcode.Client;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.*;
import java.net.ConnectException;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static java.lang.Character.isDigit;
import static java.lang.Integer.parseInt;

public class ClientMainTwo extends StoreClientDetails  implements Serializable {
    //    public static String name;
    static String command;
    private static BufferedReader in;
    private static PrintStream out;
    public static List<String> forTurn= new ArrayList<>(List.of("right","left"));
    public static List<String> validCom= new ArrayList<>(List.of("forward","back","turn"));
    public static List<String> other = new ArrayList<>(List.of("launch", "look", "repair", "reload", "fire", "state"));

    public static List<String> forMovem= new ArrayList<>(List.of("forward","back"));



    public static void main(String[] args) throws IOException {

//        if (args.length != 2) {
//            System.out.println("java ClientExample <ipAddress> <port>");
//            return;
//        }
//        String ipAddress = args[0];
//        int port = Integer.parseInt(args[1]);

        try (
                // Socket socket = new Socket(ipAddress,port))
                //Socket socket = new Socket("20.20.15.174", 5000))
                Socket socket = new Socket("localhost", 5000))
        {
            System.out.println("Waiting for connection response from server: ");
            Thread.sleep(2000);
            ClientRequestandResponse client = new ClientRequestandResponse();

            in = new BufferedReader(new InputStreamReader(
                    socket.getInputStream()));
            out = new PrintStream(String.valueOf(new OutputStreamWriter(socket.getOutputStream())));
            out.flush();
            System.out.println(">>> Response from server: "+ in.readLine());

            String[] rwArts = {asciiArt.rw,asciiArt.rw2,asciiArt.rw3,
                    asciiArt.rw4,asciiArt.rw5,asciiArt.rw6, asciiArt.rw7,
                    asciiArt.rw8, asciiArt.rw9, asciiArt.rw10, asciiArt.rw11,
                    asciiArt.rw12};

            Random random = new Random();
            String asciiText = rwArts[random.nextInt(rwArts.length)];
            String[] lines = asciiText.split("\n");
            for (String line : lines){
                System.out.println(line + "\r");
            }

            while (true){
                String userInput = getInput("Do you want to launch a robot? (yes/no): ");

                if (userInput.equalsIgnoreCase("yes")) {
                    CreateJSONObject createJSONObject = new CreateJSONObject();
                    createJSONObject.setRobotName(getInput("Please enter the name of the robot:"));
                    String launchInput;
                    while (true){
                        launchInput = getInput(createJSONObject.getRobotName() + "> Please enter the launch command: <launch> <kind> <shieldStrength int> <maxShots int> ? ");
                        String[] launchInputs = launchInput.split(" ");

                        if (launchInputs.length!=4){
                            System.out.println("Please type launch command as instructed!!");
                            continue;
                        }

                        if (!launchInputs[0].equalsIgnoreCase("launch")){
                            System.out.println("Please type launch command as instructed!!");
                            continue;
                        }

                        if (launchInputs[1].equalsIgnoreCase("")){
                            System.out.println("Please type launch command as instructed!!");
                            continue;
                        }
                        if (!isDigitAndRangeOneToEight(launchInputs[2])) {
                            System.out.println("Please type launch command as instructed!!");
                            continue;
                        }
                        if (!isDigitAndRangeOneToEight(launchInputs[3])){
                            System.out.println("Please type launch command as instructed!!");

                        }
                        else{
                            break;}
                    }

                    createJSONObject.setCommand(launchInput);

                    System.out.println("Client launching " + createJSONObject.getRobotName() + "...");

                    // code to launch the robot
                    JsonNode response = client.sendRequestToServer(createJSONObject.getJsonObject() ,socket);
                    //display response on console
                    ConsoleDisplayServerResponse.displayResponse(response, createJSONObject.getCommand());



                    //check if the launch was successfully
                    if (response.get("result").asText().equalsIgnoreCase("OK")){
                        while (socket.isConnected()) {
//                        System.out.println(createJSONObject.getRobotName() + "> What must I do next?");
                            String command = getInput(createJSONObject.getRobotName() + "> What must I do next?");
                            name = createJSONObject.getRobotName();
                            if (command.equalsIgnoreCase("quit")){
                                out.println("Shutting down "+createJSONObject.getRobotName()+"...");
                                socket.close();
                                break;
                            }
                            while (isLenghtOne(command)){
                                command = getInput(createJSONObject.getRobotName() + "> What must I do next?");
                            }



                            if (command.split(" ").length ==2&& validCom.contains(command.split(" ")[0]) ){

                                boolean isTurn = command.split(" ")[0].equalsIgnoreCase("turn") && forTurn.contains(command.split(" ")[1]);
                                boolean isMove = forMovem.contains(command.split(" ")[0]) && isNumber(command.split(" ")[1]);

                                while (((!isTurn) && (!isMove))&& !other.contains(command.split(" ")[0])){
                                    System.out.println("Please specify the correct argument format for "+command.split(" ")[0]);
                                    command = getInput(createJSONObject.getRobotName() + "> What must I do next?");
                                    while (isLenghtOne(command)){
                                        command = getInput(createJSONObject.getRobotName() + "> What must I do next?");
                                    }
                                    isTurn = command.split(" ")[0].equalsIgnoreCase("turn") && forTurn.contains(command.split(" ")[1]);
                                    isMove = forMovem.contains(command.split(" ")[0]) && isNumber(command.split(" ")[1]);

                                }
                            }
                            createJSONObject.setCommand(command);
                            if (createJSONObject.getCommand().equalsIgnoreCase("help")){
                                helpCommand helpCommand = new helpCommand();
                                System.out.println(helpCommand);

                            } else if (!createJSONObject.getCommand().equalsIgnoreCase("launch")){
                                //send request to server and receive response
                                response = client.sendRequestToServer(createJSONObject.getJsonObject(),socket);
                                //display response on console
                                System.out.println("Waiting for the response from the server");
                                //  Thread.sleep(3000);
                                ConsoleDisplayServerResponse.displayResponse(response, createJSONObject.getCommand());
                            }
                            else  {
                                System.out.println("You already launched your robot!!!");
                            }
                        }
                    } else {
                        continue;
                    }


                } else if (userInput.equalsIgnoreCase("no")) {
                    System.out.println("Okay, bye...");
                    return;
                }else {
                    System.out.println("Invalid input, please enter yes/no");
                    continue;
                }
                break;
            }


        } catch (InterruptedException | ClassNotFoundException | ConnectException e) {
            System.err.println("Failed to connect to server: " + e.getMessage());
            //throw new RuntimeException(e);
        }catch (IOException e){
            System.err.println("Error : "+ e.getMessage());
        }
    }

    public static String getInput(String prompt) throws IOException {
        System.out.println(prompt);
        BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));
        String input = userInput.readLine();
        while (input.isBlank()) {
            System.out.println(prompt);
            input = userInput.readLine();
        }
        return input.strip().toLowerCase();
    }

    public static boolean isDigitAndRangeOneToEight(String user_input) {
        return user_input.matches("[0-9]+");
    }
    public static boolean isNumber(String expression) {
        try {
            Integer.parseInt(expression);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    public static boolean isLenghtOne(String command){
        if (command.split(" ").length == 1 && validCom.contains(command) && !other.contains(command)){
            System.out.println("Please enter arguments for "+command.split(" ")[0]);
            return true;
        }
        return false;
    }

}



