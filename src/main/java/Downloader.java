import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class Downloader implements Runnable {

    private String id, url;
    private static FileManager fileMgr = new FileManager("/home/melalonso/DOCS/");
    private static QueueManager queueMgr = new QueueManager();

    public Downloader() {
        this.id = fileMgr.getCurrId();
        this.url = queueMgr.getURL();
    }

    private URL getDocument() throws MalformedURLException {
        URL doc = new URL(this.url);
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
            URL document = getDocument();
            pageText = IOUtils.toString(document);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String plainText = plainText(pageText);
        String finalText = removeStopwords(plainText);
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
