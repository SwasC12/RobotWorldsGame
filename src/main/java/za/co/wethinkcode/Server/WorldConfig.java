package za.co.wethinkcode.Server;
import java.io.*;
import java.util.Properties;

public class WorldConfig{
    //configuration file for world
    public void MakeConfigFile(){

        Properties prop = new Properties();
        OutputStream output = null;

        try {
            //hidden config file
            output = new FileOutputStream(".config.properties");

            //set the properties value
            //height and width

            prop.setProperty("height","500");
            prop.setProperty("width","500");

            //save the properties to the file
            prop.store(output,null);

        }catch (IOException io){
            io.printStackTrace();
        }finally {
            if (output != null){
                try{
                    output.close();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        }

    }



    public void readConfigFile(){
        //load and read config file at server start
        Properties prop = new Properties();
        InputStream input = null;

        try{
            input = new FileInputStream("config.properties");

            //load the properties from the file
            prop.load(input);

            //Get the property values
            String height = prop.getProperty("height");
            String width = prop.getProperty("width");


            //make use of the height and width
            //height and width load in server
            //to implement here
        }catch (IOException io){
            io.printStackTrace();
        } finally {
            if (input != null){
                try{
                    input.close();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        }

    }

}