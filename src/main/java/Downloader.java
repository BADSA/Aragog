import javafx.util.Pair;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class Downloader implements Runnable {

    private String id;
    private Pair<Integer, String> urlInfo;
    private static FileManager fileMgr = new FileManager(Settings.downloadFolder);
    private static QueueManager queueMgr = new QueueManager();

    private Crawler crawler;

    public Downloader() {
        this.id = fileMgr.getCurrId();
        this.urlInfo = queueMgr.getURL();
    }

    private URL getDocument() throws MalformedURLException {
        /*
        String url = this.urlInfo.getValue();

        this.crawler = new Crawler(url);
        return this.crawler.getDocument();
        */
        URL doc = new URL(this.urlInfo.getValue());
        return doc;
    }

    private String plainText(String content) {
        return "";
    }

    private String removeStopwords(String content) {
        return "";
    }

    public void run() {
        String pageText = null;
        try {
            URL document = this.getDocument();
            pageText = IOUtils.toString(document);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String plainText = this.plainText(pageText);
        String finalText = this.removeStopwords(plainText);


    }

}

/*
        DescargadorSeguro d1 = new DescargadorSeguro(1, cont);
        DescargadorSeguro d2 = new DescargadorSeguro(2, cont);
        DescargadorSeguro d3 = new DescargadorSeguro(3, cont);

        // Pone en ejecución varios hilos
        Thread t1 = new Thread(d1);
        Thread t2 = new Thread(d2);
        Thread t3 = new Thread(d3);

        t1.start();
        t2.start();
        t3.start();
 */
