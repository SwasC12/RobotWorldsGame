package za.co.wethinkcode.Server;
import java.nio.file.Paths;
public class CurrentDirectory {
    private final String fileName;
    public CurrentDirectory(String fileName){
        this.fileName=fileName;
    }
    public String getFilePath() {
        return Paths.get(this.fileName).toAbsolutePath().toString();
    }
}
