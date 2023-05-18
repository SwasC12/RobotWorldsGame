package za.co.wethinkcode.Server;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
public class CurrentDirectory {
    private final String fileName;
    public CurrentDirectory(String fileName){
        this.fileName=fileName;
    }
    public String getFilePath() throws IOException {
        return new File(this.fileName).getAbsolutePath();  //  toString();//   toAbsolutePath().toString();
    }
}
