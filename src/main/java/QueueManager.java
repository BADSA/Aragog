import javafx.util.Pair;

import java.util.concurrent.ConcurrentLinkedQueue;

public class QueueManager {
    private static ConcurrentLinkedQueue<Pair<Integer, String>> queue = new ConcurrentLinkedQueue<Pair<Integer, String>>();

    public Pair<Integer, String> getURL() {
        return this.queue.poll();
    }

    public void addURL(Pair<Integer, String> url) {
        this.queue.add(url);
    }


}
