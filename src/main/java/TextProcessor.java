import java.io.File;
import java.io.FileNotFoundException;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

/*
    Class that handles the operations in
    the document's text.
 */
public class TextProcessor {

    public static LanguageDetector ld = new LanguageDetector();

    public TextProcessor() {
        ld.init();
    }

    /*
        Method that removes accents, uppercase
        letters, numbers and dates.
    */
    public String plainText(String content) {
        content = stripAccents(content); // Remove accents
        content = content.toLowerCase(); // Removing uppercase
        content = content.replaceAll("[^a-z' ]", ""); // Leave only letters and apostrophes
        content = content.trim().replaceAll("\\s+", " ");
        return content;
    }

    /*
        Removes the stop words based on what are
        the detected languages in the text.
     */
    public String removeStopWords(String content) {
        List<String> langs = ld.detect(content);
        HashMap<String, Boolean> stopWords = getStopWords(langs);

        content = content.trim().replaceAll("\\s+", " ");
        String[] words = content.split(" ");
        String finalContent = "";

        for (String word : words) {
            if (!stopWords.containsKey(word)) {
                finalContent += word + " ";
            }
        }

        return finalContent;
    }


    private static String stripAccents(String s) {
        s = Normalizer.normalize(s, Normalizer.Form.NFD);
        s = s.replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");
        return s;
    }

    private HashMap getStopWords(List<String> langs) {
        String w;
        Scanner inFile = null;
        HashMap<String, Boolean> stopWords = new HashMap<String, Boolean>();

        for (String l : langs) {
            // String path = Settings.stopwordsPath + l + ".words";
            String path = "./config/stopwords/" + l + ".words";

            try {
                inFile = new Scanner(new File(path)).useDelimiter("\n");
            } catch (FileNotFoundException e) {
                System.out.println("Could not find stop words for " + l);
            }

            while (inFile.hasNext()) {
                w = inFile.next();
                stopWords.put(w, true);
            }
            inFile.close();
        }

        return stopWords;
    }

}
