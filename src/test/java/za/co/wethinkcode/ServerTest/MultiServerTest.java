package za.co.wethinkcode.ServerTest;
import org.junit.Test;

import za.co.wethinkcode.Server.MultiServers;

public class MultiServerTest {//broken test
    @Test
    public void testWorldValues(){
        String args = "";//get user input from multiServers
        if (args.length() == 0 ){
            System.out.println("You did not enter any configuration parameters thus we will use default values.");
        }
        else if (args.length() < 6 && args.length() >0 ){
            System.out.println("You entered less than the required number of configuration parameters thus we will use default values." );
    
        }

    }
    



}
