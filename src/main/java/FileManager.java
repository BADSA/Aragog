import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.URL;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.text.MessageFormat;
import java.util.List;

import static java.util.Arrays.asList;


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
    public static final List<String> extensions =  asList("doc", "docx", "pdf", "txt", "odt");


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

    /*
        Adds list of links to the corresponding directory.
     */
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

    /*
        Adds processed text to the corresponding directory.
    */
    public void addProcessedText(String folderID, String text) {
        String folderPath = MessageFormat.format("{0}/{1}/", savePath, folderID);
        File processedTextFile = new File(folderPath + processedFilename);
        try {
            FileUtils.writeStringToFile(processedTextFile, text);
        } catch (IOException e) {
            System.out.println("Could not write processed text to folder "+folderID);
        }
    }

    /*
        Adds NON processed text to the corresponding directory.
     */
    public void addText(String folderID, String doc) {
        String folderPath = MessageFormat.format("{0}/{1}/", savePath, folderID);
        File textFile = new File(folderPath + textFileName);
        try {
            FileUtils.writeStringToFile(textFile, doc, true);
        } catch (IOException e) {
            System.out.println("Could not write document text to folder "+folderID);
        }
    }

    /*
        Adds document URL to the corresponding directory.
    */
    public void addUrl(String folderID, String url) {
        String folderPath = MessageFormat.format("{0}/{1}/", savePath, folderID);
        File urlFile = new File(folderPath + urlFileName);
        try {
            FileUtils.writeStringToFile(urlFile, url, true);
        } catch (IOException e) {
            System.out.println("Could not write URL to folder "+folderID);
        }
    }

    /*
        Returns the current ID for next
        document.
    */
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

    /*
        Downloads document and returns its path.
    */
    public String getDocument(String url, String id) throws IOException{
        String ext = FilenameUtils.getExtension(url);
        File file;
        if (extensions.contains(ext)) {
            file = new File(savePath +"/"+ id +"/raw."+ext);
        }else {
            file = new File(savePath +"/"+ id +"/raw.html");
        }

        FileUtils.copyURLToFile(new URL(url),file);

        return file.getPath();
    }

    /*
        Removes the downloaded document by its id.
    */
    public void removeDocument(String url, String id) throws IOException{
        File file;
        String ext = FilenameUtils.getExtension(url);
        if (extensions.contains(ext)) {
            file = new File(savePath +"/"+ id +"/raw."+ext);
        } else {
            file = new File(savePath +"/"+ id +"/raw.html");
        }

        FileUtils.deleteQuietly(file);
    }


}
