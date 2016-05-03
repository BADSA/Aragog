import com.optimaize.langdetect.DetectedLanguage;
import com.optimaize.langdetect.LanguageDetectorBuilder;
import com.optimaize.langdetect.ngram.NgramExtractors;
import com.optimaize.langdetect.profiles.LanguageProfile;
import com.optimaize.langdetect.profiles.LanguageProfileReader;
import com.optimaize.langdetect.text.CommonTextObjectFactories;
import com.optimaize.langdetect.text.TextObject;
import com.optimaize.langdetect.text.TextObjectFactory;

import java.util.ArrayList;
import java.util.List;

/*
    Does the language recognition
    for each of the documents.
*/
public class LanguageDetector {
    //load all languages:
    public static  List<LanguageProfile> languageProfiles = null;
    public static  com.optimaize.langdetect.LanguageDetector  languageDetector = null;
    public static TextObjectFactory textObjectFactory = null;

    public void init() {
        try {
            languageProfiles = new LanguageProfileReader().readAllBuiltIn();

        } catch (Exception e) {
            System.out.println("Error recognizing languages");
            System.out.println(e.getMessage());
        }

        //build language detector:
        languageDetector = LanguageDetectorBuilder.create(NgramExtractors.standard())
                .withProfiles(languageProfiles)
                .build();

        //create a text object factory
         textObjectFactory = CommonTextObjectFactories.forDetectingOnLargeText();
    }

    /*
        Receives a string with the document text
        and performs the recognition.
    */
    public List<String> detect(String text) {
        List<String> languages = new ArrayList<String>();

        TextObject textObject = textObjectFactory.forText(text);
        List<DetectedLanguage> detected = languageDetector.getProbabilities(textObject);

        for(DetectedLanguage dl: detected) {
            languages.add(dl.getLocale().getLanguage());
        }

        return languages;
    }


}
