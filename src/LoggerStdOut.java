public class LoggerStdOut extends Logger {
    @Override
    public void flush() {
        for (String log : logs) {
            System.out.println(log);
        }
        clear();
    }
}