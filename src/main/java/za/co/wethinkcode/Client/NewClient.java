package za.co.wethinkcode.Client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

public class NewClient {
    public static void main(String args[]) {
        try (
                Socket socket = new Socket("localhost", 5000);
                PrintStream out = new PrintStream(socket.getOutputStream());
                BufferedReader in = new BufferedReader(new InputStreamReader(
                        socket.getInputStream()));
        )
        {
            out.println("Another client on the line here");

            out.flush();
            String messageFromServer = in.readLine();
            System.out.println("Response: "+messageFromServer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
