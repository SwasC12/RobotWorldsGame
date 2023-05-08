package za.co.wethinkcode.Client;

import com.fasterxml.jackson.databind.JsonNode;

import java.io.*;
import java.net.ConnectException;
import java.net.Socket;
import java.net.SocketException;
import java.util.Random;

public class SimpleClient extends StoreClientDetails  implements Serializable {
    //    public static String name;
    static String command;
    private static BufferedReader in;
    private static PrintStream out;

    public static void main(String[] args) throws IOException {


        try (
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
                //    Thread.sleep(75);
            }

            while (true){
                String userInput = getInput("Do you want to launch a robot? (yes/no): ");

                if (userInput.equalsIgnoreCase("yes")) {
                    CreateJSONObject createJSONObject = new CreateJSONObject();
                    createJSONObject.setRobotName(getInput("Please enter the name of the robot:"));

                    createJSONObject.setCommand(getInput(createJSONObject.getRobotName() + "> Please enter the launch command: <launch> <kind> <shieldStrength int> <maxShots int> ? "));

                    System.out.println("Client launching " + createJSONObject.getRobotName() + "...");
                    //  Thread.sleep(3000);

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

                            createJSONObject.setCommand(command);
                            if (createJSONObject.getCommand().equalsIgnoreCase("help")){
                                helpCommand helpCommand = new helpCommand();
                                System.out.println(helpCommand);

                            } else if (!createJSONObject.getCommand().equalsIgnoreCase("launch")){
                                //send request to server and receive response
                                response = client.sendRequestToServer(createJSONObject.getJsonObject(),socket);
                                //display response on console
                                System.out.println("Waiting for the response from the server");
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


        } catch (InterruptedException| ClassNotFoundException | ConnectException e) {
            System.err.println("Failed to connect to server: " + e.getMessage());
            //throw new RuntimeException(e);
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



