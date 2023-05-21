package za.co.wethinkcode.Client;
import com.fasterxml.jackson.databind.JsonNode;
import za.co.wethinkcode.Server.ServerGraphics;

import java.io.*;
import java.net.ConnectException;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static java.lang.Character.isDigit;
import static java.lang.Integer.parseInt;

    public class ClientMainOne extends StoreClientDetails  implements Serializable {
        //    public static String name;
        static String command;
        private static final String red = ServerGraphics.ANSI_RED;
        private static final String reset = ServerGraphics.ANSI_RESET;
        private static final String y_bg = ServerGraphics.ANSI_YELLOW_BG;

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
                asciiArt load = new asciiArt();
                load.rwLoadBar("loading",50,30);
                System.out.println(" ");
                String green = ServerGraphics.ANSI_GREEN;
                System.out.println(green + "Waiting for connection response from server: " + reset);


                Thread.sleep(2000);
                ClientRequestandResponse client = new ClientRequestandResponse();

                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);


                out.flush();
                System.out.println(green + ">>> Response from server: " + reset + in.readLine());


                String[] rwArts = {asciiArt.rw,asciiArt.rw2,asciiArt.rw3,
                        asciiArt.rw4,asciiArt.rw5,asciiArt.rw6, asciiArt.rw7,
                        asciiArt.rw9,asciiArt.rw11, asciiArt.rw12};

                Random random = new Random();
                String asciiText = rwArts[random.nextInt(rwArts.length)];
                String[] lines = asciiText.split("\n");
                for (String line : lines){
                    System.out.println(line + "\r");
                }

                while (true){
                    String userInput = getInput(green + "Do you want to launch a robot? (yes/no): " + reset);

                    if (userInput.equalsIgnoreCase("yes")) {
                        CreateJSONObject createJSONObject = new CreateJSONObject();
                        createJSONObject.setRobotName(getInput(green + "Please enter the name of the robot:" + reset));

                        String launchInput;
                        while (true){
                            asciiArt sniper = new asciiArt();
                            asciiArt baz = new asciiArt();
                            asciiArt sg = new asciiArt();
                            System.out.println("----------------------------------------------------");
                            System.out.println("----------------------------------------------------");
                            sniper.rwSniper();
                            System.out.println("");
                            System.out.println("");
                            baz.rwBazooka();
                            System.out.println("");
                            sg.rwShotgun();
                            System.out.println("");
                            System.out.println("----------------------------------------------------");
                            System.out.println("----------------------------------------------------");


                            launchInput = getInput(createJSONObject.getRobotName() + "> Please enter the launch command: <launch> <kind> ? \n " +
                                    "There are three robot kinds you can choose from:    \n" +
                                    "              Kind          Shields         shots   \n" +
                                    "----------------------------------------------------\n"+
                                    "      (I)    : SNIPER           5               6   \n" +
                                    "      (II)   : BAZOOKA          10              15   \n "+
                                    "     (III)  : SHOTGUN          10              20   \n" +
                                    " -------------#######################---------------\n"  +
                                    " Your can also specify your own kind with maxShields and maxShots as follows:\n" +
                                    "       : <launch> <kind> <maxShots int>");



                            String[] launchInputs = launchInput.split(" ");

                            if (launchInputs.length==2) {
                                String robotKind = launchInputs[1].toLowerCase();
                                if (launchInputs[0].equalsIgnoreCase("launch") &&
                                        robotKind.equals("sniper") || robotKind.equals("bazooka") || robotKind.equals("shotgun")) {
                                    createJSONObject.setCommand(launchInput);
                                    break;
                                } else {
                                    System.out.println(red + "Please type launch command as instructed!" + reset);
                                }

                            } else if (launchInputs.length==3 && launchInputs[0].equalsIgnoreCase("launch") &&
                                    !launchInputs[1].equalsIgnoreCase("") && isDigit(launchInputs[2])) {
                                createJSONObject.setCommand(launchInput);
                                break;
                            }
                            else {
                                System.out.println(red + "Please type launch command as instructed!" + reset);

                            }

                        }

                        createJSONObject.setCommand(launchInput);

                        System.out.println(green + "Client launching " + createJSONObject.getRobotName() + "..." + reset);

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
                                        System.out.println(red + "Please specify the correct argument format for "+ y_bg + red + command.split(" ")[0] + reset);
                                        command = getInput(createJSONObject.getRobotName() + "> What must I do next?");
                                        while (isLenghtOne(command)){
                                            command = getInput(createJSONObject.getRobotName() + green + "> What must I do next?" + reset);
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
                                    System.out.println(green + "Waiting for the response from the server" + reset);
                                    //  Thread.sleep(3000);
                                    ConsoleDisplayServerResponse.displayResponse(response, createJSONObject.getCommand());
                                }
                                else  {
                                    System.out.println(red + "You already launched your robot!!!" + reset);
                                }
                            }
                        } else {
                            continue;
                        }


                    } else if (userInput.equalsIgnoreCase("no")) {
                        System.out.println(green + "Okay, bye..." + reset);
                        return;
                    }else {
                        System.out.println(red + "Invalid input, please enter yes/no" + reset);

                        continue;
                    }
                    break;
                }


            } catch (InterruptedException| ClassNotFoundException | ConnectException e) {
                System.err.println(red + "Failed to connect to server: "+ y_bg+ e.getMessage() + reset);
                //throw new RuntimeException(e);
            }catch (IOException e){
                System.err.println(red + "Error : "+ y_bg+ e.getMessage()+reset);
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

        public static boolean isDigit(String user_input) {
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
                System.out.println(red + "Please enter arguments for "+ y_bg + red +command.split(" ")[0] + reset);
                return true;
            }
            return false;
        }


    }







