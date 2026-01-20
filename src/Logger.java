import java.util.ArrayList;
public abstract class Logger {
    public enum Level {
        INFO, ERROR, CENTRAL_ELEM, MAX_ELEM, MIN_ELEM
    }

    protected ArrayList<String> logs = new ArrayList<>();

    public void log(Level level, String info) {
        logs.add(level + ": " + info);
    }
    protected void clear() { //clears the log
        logs.clear();
    }

    abstract public void flush();
}