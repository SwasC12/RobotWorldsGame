package za.co.wethinkcode.Server;
import java.io.File;

public class CurrentDirectory {
    public CurrentDirectory(){
        }

    public String getAbsolutePath(String pathToFile) {
        String baseDirectory = System.getProperty("user.dir");
        //file path relative to the base directory
        String filePath = baseDirectory + pathToFile;

        File configFile = new File(filePath);
        return configFile.getAbsolutePath();
    }
}









