package za.co.wethinkcode.Client;

import com.fasterxml.jackson.databind.JsonNode;

import java.io.*;
import java.net.ConnectException;
import java.net.Socket;
import java.net.SocketException;

public class SimpleClient implements Serializable {
    static String name;
    static String command;
    private static BufferedReader in;
    private static PrintStream out;

    public static void main(String[] args) throws IOException {

//        if (args.length != 2) {
//            System.out.println("java ClientExample <ipAddress> <port>");
//            return;
//        }
//        String ipAddress = args[0];
//        int port = Integer.parseInt(args[1]);

        try (
                // Socket socket = new Socket(ipAddress,port))
                // Socket socket = new Socket("10.3.16.115", 5000))
                Socket socket = new Socket("localhost", 5000))
        {
            System.out.println("Waiting for connection response from server: ");
            Thread.sleep(4000);
            ClientRequestandResponse client = new ClientRequestandResponse();

//            BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));
            in = new BufferedReader(new InputStreamReader(
                    socket.getInputStream()));
            out = new PrintStream(String.valueOf(new OutputStreamWriter(socket.getOutputStream())));
            out.flush();
            System.out.println(">>> Response from server: "+ in.readLine());

            // Welcome to robot world
            System.out.println("--------------------------------------------------------");
            System.out.println("-----------------WELCOME TO ROBOT WORLD-----------------");
            System.out.println("--------------------------------------------------------");
            //  System.out.println("Do you want to launch a robot? (yes/no): ");
            // Getting response from user
//            String launchRobot = userInput.readLine();

            while (true){
                String userInput = getInput("Do you want to launch a robot? (yes/no): ");

                if (userInput.equalsIgnoreCase("yes")) {
                    CreateJSONObject createJSONObject = new CreateJSONObject();
                    //  System.out.println("Please enter the name of the robot:");
                    createJSONObject.setRobotName(getInput("Please enter the name of the robot:"));

                    System.out.println("Client launching " + createJSONObject.getRobotName() + "...");
                    Thread.sleep(3000);

                    // code to launch the robot
                    JsonNode response = client.sendRequestToServer(createJSONObject.getJsonObject() ,socket);
                    //display response on console
                    ConsoleDisplayServerResponse.displayResponse(response, createJSONObject.getCommand());

                    //check if the launch was successfully
                    if (response.get("result").asText().equalsIgnoreCase("OK")){
                        while (socket.isConnected()) {
//                        System.out.println(createJSONObject.getRobotName() + "> What must I do next?");
                            String command = getInput(createJSONObject.getRobotName() + "> What must I do next?");

                            if (command.equalsIgnoreCase("quit")){
                                out.println("Shutting down "+createJSONObject.getRobotName()+"...");
                                socket.close();
                                break;
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
                                Thread.sleep(3000);
                                ConsoleDisplayServerResponse.displayResponse(response, createJSONObject.getCommand());
                            }
                            else  {
                                System.out.println("You already launched your robot!!!");
                            }
                        }
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
//            throw new RuntimeException(e);
        }catch (IOException e){
            System.err.println("Error : "+ e.getMessage());
        }
    }

    public static String getInput(String prompt) throws IOException {
        System.out.println(prompt);
//        String input = scanner.nextLine();
        BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));
        String input = userInput.readLine();
        while (input.isBlank()) {
            System.out.println(prompt);
            input = userInput.readLine();
        }
        return input.strip().toLowerCase();
    }

}



