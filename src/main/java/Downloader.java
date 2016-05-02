import javafx.util.Pair;
import java.util.*;

public class Downloader implements Runnable {

    private String id;
    private Pair<Integer, String> urlInfo;
    private Crawler crawler;
    private static FileManager fileMgr = new FileManager(Settings.downloadFolder);
    private static QueueManager queueMgr = new QueueManager();
    private static TextProcessor tp = new TextProcessor();
    public static LogEntity le = new LogEntity();
    public static DBManager dbMan = new DBManager();

    public Downloader() {
        Settings.load();
        this.id = fileMgr.getCurrId();
        this.urlInfo = queueMgr.getURL();
        this.crawler = new Crawler(this.urlInfo.getValue());
    }

    /* Returns the text of the document */
    private String getDocument() {
        return this.crawler.getDocument();
    }

    /*
        Does the whole process for each of the
        downloaders.
     */
    public void run() {
        String url = this.urlInfo.getValue();
        le.write("Extraido: " + url);

        if (this.crawler.status()) {
            fileMgr.addUrl(this.id, url); // Save url

            List<String> links = this.crawler.getLinks();
            fileMgr.addLinks(this.id, links); // Save links

            String document  = this.getDocument();
            fileMgr.addText(this.id, document); // Save document text

            String plainText = this.tp.plainText(document);
            String finalText = this.tp.removeStopWords(plainText);
            fileMgr.addProcessedText(this.id, finalText); // Save processed text
            le.write("Visitado: " + url);
        } else {
            le.write("Error: " + url + " no disponible.");
        }

        dbMan.updateURL(this.urlInfo.getKey());
    }


}
