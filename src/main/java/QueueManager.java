import javafx.util.Pair;

import java.util.concurrent.ConcurrentLinkedQueue;

public class QueueManager {
    private static ConcurrentLinkedQueue<Pair<Integer, String>> queue = new ConcurrentLinkedQueue<Pair<Integer, String>>();

    public Pair<Integer, String> getURL() {
        return queue.poll();
    }

    public void clearQueue() {
        queue.clear();
    }

    public boolean isEmpty() {
        return queue.isEmpty();
    }

    public void addURL(Pair<Integer, String> url) {
        queue.add(url);
    }


}
