import java.io.FileNotFoundException;
import java.io.PrintWriter;
public class LoggerFile extends Logger {
    private static String LOGS_PATH= "logs/";
    private String fileName;

    public LoggerFile(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public void flush() {
        try (PrintWriter out = new PrintWriter(LOGS_PATH+this.fileName)) {
            for (String log : logs) {
                out.println(log);
            }
            clear();
        } catch (FileNotFoundException e) {
            System.out.println("Can’t flush the log. Please check the filename: " + fileName);
        }
    }
}