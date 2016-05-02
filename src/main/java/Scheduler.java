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

    public void loadURLS() {
        /*
        List<Pair<Integer, String>> urlsList = dbManager.getURLs();

        for (Pair<Integer, String> url : urlsList) {
           queueManager.addURL(url);
        }
        */
    }

}
