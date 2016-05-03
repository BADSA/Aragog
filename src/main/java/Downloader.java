import config.Settings;
import extractor.Extractor;
import extractor.implementation.ExtractorManager;
import utils.Pair;
import log.LogEntity;
import managers.DBManager;
import managers.FileManager;
import managers.QueueManager;
import utils.TextProcessor;

import java.io.IOException;
import java.util.List;

/*
    One of the main classes for downloading the
    documents and saving them in the user directory.
*/
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
    }


    /*
        Does the whole process for each of the
        downloaders.
     */
    public void run() {
        if (this.urlInfo != null) {
            String url = this.urlInfo.getValue();
            this.fileExtractor = new ExtractorManager(url).getExtractor();
            le.write("Extracted: " + url);
            System.out.println("Visiting " + this.urlInfo.getValue());
            try {
                String filePath = fileMgr.getDocument(url, this.id);
                fileMgr.addUrl(this.id, url); // Save url

                String document = this.fileExtractor.getText(filePath);
                fileMgr.addText(this.id, document); // Save document text

                List<String> links = this.fileExtractor.extractUrls(document);
                fileMgr.addLinks(this.id, links); // Save links
                dbMan.inserURLList(links);

                String plainText = this.tp.plainText(document);
                String finalText = this.tp.removeStopWords(plainText);
                fileMgr.addProcessedText(this.id, finalText); // Save processed text
                le.write("Visited: " + url);
                fileMgr.removeDocument(url, this.id);
            } catch (IOException e) {
                le.write("Error: " + url + " not available.");
            }

            System.out.println("Marking as visited " + this.urlInfo.getValue());
            dbMan.updateURL(this.urlInfo.getKey());
        }

    }


}
