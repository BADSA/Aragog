import org.apache.commons.io.FileUtils;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.text.MessageFormat;
import java.util.List;
import java.io.File;


/*
    Class that handles all the operations
    related to the File System.
*/
public class FileManager {

    private String savePath;
    public static final String folderName = "2013099997";
    public static final String currIdFilename = "/CURRENT";
    public static final String linksFilename = "/links";
    public static final String processedFilename = "/processed";
    public static final String textFileName = "/text";
    public static final String urlFileName = "/url";


    public FileManager(String savePath) {
        this.savePath = savePath + folderName;
        try {
            this.init();
        } catch (IOException e) {
            System.out.println("Could not create initial folder structure");
        }
    }

    private void init() throws IOException {
        File mainDir = new File(savePath);
        mainDir.mkdir();
        File currIdFile = new File(savePath + currIdFilename);
        FileUtils.writeStringToFile(currIdFile, "1");
    }

    public void addLinks(String folderID, List<String> links) {
        String folderPath = MessageFormat.format("{0}/{1}/", savePath, folderID);
        File linksFile = new File(folderPath + linksFilename);
        for (String l : links) {
            try {
                FileUtils.writeStringToFile(linksFile, l + "\n", true);
            } catch (IOException e) {
                System.out.println("Could not write links to folder "+folderID);
            }
        }
    }

    public void addProcessedText(String folderID, String text) {
        String folderPath = MessageFormat.format("{0}/{1}/", savePath, folderID);
        File processedTextFile = new File(folderPath + processedFilename);
        try {
            FileUtils.writeStringToFile(processedTextFile, text);
        } catch (IOException e) {
            System.out.println("Could not write processed text to folder "+folderID);
        }
    }

    public void addText(String folderID, String doc) {
        String folderPath = MessageFormat.format("{0}/{1}/", savePath, folderID);
        File textFile = new File(folderPath + textFileName);
        try {
            FileUtils.writeStringToFile(textFile, doc, true);
        } catch (IOException e) {
            System.out.println("Could not write document text to folder "+folderID);
        }
    }

    public void addUrl(String folderID, String url) {
        String folderPath = MessageFormat.format("{0}/{1}/", savePath, folderID);
        File urlFile = new File(folderPath + urlFileName);
        try {
            FileUtils.writeStringToFile(urlFile, url, true);
        } catch (IOException e) {
            System.out.println("Could not write URL to folder "+folderID);
        }
    }

    public String getCurrId() {
        String currId = "", nextId;
        try {
            File file = new File(savePath + currIdFilename);
            FileChannel channel = new RandomAccessFile(file, "rw").getChannel();

            FileLock lock = channel.lock();

            try {
                currId = FileUtils.readFileToString(file);
                nextId = "" + (Integer.parseInt(currId) + 1);
                FileUtils.writeStringToFile(file, nextId);
            } finally {
                lock.release();
            }
            channel.close();
        } catch (Exception e) {
            e.getStackTrace();
        }
        return currId;
    }


}
