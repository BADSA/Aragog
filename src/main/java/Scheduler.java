import javafx.util.Pair;

import java.sql.SQLException;
import java.util.List;

public class Scheduler {
    private QueueManager queueManager;
    private DBManager dbManager;

    public Scheduler() {
        Settings.load();
        this.dbManager = new DBManager(Settings.database);
        this.queueManager = new QueueManager();
    }

    private void loadURLS() throws SQLException {
        List<Pair<Integer, String>> urlsList = dbManager.getURLs();

        for (Pair<Integer, String> url : urlsList) {
           queueManager.addURL(url);
        }
    }

    public static void main (String args[]) {
        Scheduler sc = new Scheduler();
        try {
            sc.loadURLS();
        }catch (Exception e) {
            System.out.println(e.getMessage());
            // TODO log exception
        }

    }

}
