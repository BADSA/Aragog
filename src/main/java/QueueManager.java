import javafx.util.Pair;

import java.util.concurrent.ConcurrentLinkedQueue;

public class QueueManager {
    private ConcurrentLinkedQueue<Pair<Integer, String>> queue;

    public Pair<Integer, String> getUrl() {
        return this.queue.poll();
    }

    public void addURL(Pair<Integer, String> url) {
        this.queue.add(url);
    }

    public String getURL() {
        return "www.google.com";
    }

}
