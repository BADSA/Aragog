import javafx.util.Pair;
import java.io.FileInputStream;
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

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
