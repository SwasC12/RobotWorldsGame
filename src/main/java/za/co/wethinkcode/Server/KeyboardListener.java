package za.co.wethinkcode.Server;

import java.util.Scanner;

public class KeyboardListener implements Runnable {
    private boolean stillRunning = true;
    private String enteredString = "";

    @Override
    public void run(){
        Scanner sc = new Scanner(System.in);
        while (stillRunning){
            enteredString = sc.nextLine();
        }
    }

    public void stopRunning(){
        stillRunning = false;
    }

    public String getEnteredString(){
        return enteredString;
    }
}
