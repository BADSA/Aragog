import config.Settings;
import utils.Pair;
import managers.DBManager;
import managers.QueueManager;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class Scheduler {
    private QueueManager queueManager;
    private DBManager dbManager;

    public Scheduler() {
        Settings.load();
        try {
            this.dbManager = new DBManager();
            this.dbManager.setConnection(Settings.database);
            this.dbManager.loadBlackList();
            this.queueManager = new QueueManager();
        } catch (Exception e) {
            System.out.println("Error creating scheduler.");
            System.out.println(e.getMessage());
        }
    }

    /*
        Checks if the queue is empty.
     */
    public boolean isQueueEmpty() {
        return this.queueManager.isEmpty();
    }

    /*
        Loads URLs in the queue for the Downloaders.
    */
    public void loadURLS() {
        try {
            if (this.dbManager.checkEmptyDB()) {

                FileInputStream fIS = new FileInputStream("./config/INIT");
                Scanner in = new Scanner(fIS);
                while (in.hasNext()) {
                    String url = in.next();
                    this.dbManager.insertURL(url);
                }
            }

            List<Pair<Integer, String>> urlsList = dbManager.getURLs(30);

            this.queueManager.clearQueue();
            for (Pair<Integer, String> url : urlsList) {
                this.queueManager.addURL(url);
            }

        } catch (SQLException e) {
            System.out.println("Error occurred with the database.");
        } catch (FileNotFoundException e) {
            System.out.println("Error occurred while loading INIT file: INIT file not found");
        }
    }

}
