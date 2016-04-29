import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;

public class Scheduler {
    private QueueManager queueManager;
    private DBManager dbManager;

    public Scheduler() {
        this.dbManager = new DBManager(CONFIG.database);
        this.queueManager = new QueueManager();
    }

    private void loadURLS() {
        List<Pair<Integer, String>> urlsList = dbManager.getURLs();

        for (Pair<Integer, String> url : urlsList) {
           queueManager.addURL(url);
        }
    }
}
