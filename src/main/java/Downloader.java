import Extractor.Extractor;
import Extractor.implementation.ExtractorManager;
import Extractor.implementation.HTMLExtractor;
import javafx.util.Pair;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class Downloader implements Runnable {

    private String id;
    private Pair<Integer, String> urlInfo;
    private Extractor fileExtractor;
    private static FileManager fileMgr = new FileManager(Settings.downloadFolder);
    private static QueueManager queueMgr = new QueueManager();
    private static TextProcessor tp = new TextProcessor();
    public static LogEntity le = new LogEntity();
    public static DBManager dbMan = new DBManager();

    public Downloader() {
        Settings.load();
        this.id = fileMgr.getCurrId();
        this.urlInfo = queueMgr.getURL();
        System.out.println(this.urlInfo.getValue());
        this.fileExtractor = new ExtractorManager(this.urlInfo.getValue()).getExtractor();
    }


    /*
        Does the whole process for each of the
        downloaders.
     */
    public void run() {
        String url = this.urlInfo.getValue();
        le.write("Extraido: " + url);

        try {
            String filePath = fileMgr.getDocument(url, this.id);

            System.out.println("FILE PATH ==================================================");
            System.out.println(filePath);
            System.out.println("FILE PATH ==================================================");


            fileMgr.addUrl(this.id, url); // Save url

            String document  = this.fileExtractor.getText(filePath);
            fileMgr.addText(this.id, document); // Save document text

            List<String> links = this.fileExtractor.extractUrls(document);
            fileMgr.addLinks(this.id, links); // Save links
            dbMan.inserURLList(links);

            String plainText = this.tp.plainText(document);
            String finalText = this.tp.removeStopWords(plainText);
            fileMgr.addProcessedText(this.id, finalText); // Save processed text
            le.write("Visitado: " + url);
        } catch (IOException e) {
            le.write("Error: " + url + " no disponible.");
        }



        dbMan.updateURL(this.urlInfo.getKey());
    }


}
