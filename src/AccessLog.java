import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AccessLog {
    private static List<String> logs = new ArrayList<>();
    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static void recordAccess(String action) {
        String log = "[LOG] " + sdf.format(new Date()) + " | " + action;
        logs.add(log);
        System.out.println(log);
    }

    public static List<String> getLogs() {
        return new ArrayList<>(logs);
    }
}