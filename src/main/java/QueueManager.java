import javafx.util.Pair;

import java.util.concurrent.ConcurrentLinkedQueue;

/*
    Manages the concurrent queue that holds
    the links for the Downloaders.
*/
public class QueueManager {
    private static ConcurrentLinkedQueue<Pair<Integer, String>> queue = new ConcurrentLinkedQueue<Pair<Integer, String>>();

    /* Gets next URL to process */
    public Pair<Integer, String> getURL() {
        return queue.poll();
    }

    /* Clears the queue */
    public void clearQueue() {
        queue.clear();
    }

    /* Returns true is the queue is empty, false if not */
    public boolean isEmpty() {
        return queue.isEmpty();
    }

    /* Adds a new URL to the queue */
    public void addURL(Pair<Integer, String> url) {
        queue.add(url);
    }


}
