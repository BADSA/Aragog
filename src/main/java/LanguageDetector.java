import com.optimaize.langdetect.DetectedLanguage;
import com.optimaize.langdetect.LanguageDetectorBuilder;
import com.optimaize.langdetect.ngram.NgramExtractors;
import com.optimaize.langdetect.profiles.LanguageProfile;
import com.optimaize.langdetect.profiles.LanguageProfileReader;
import com.optimaize.langdetect.text.CommonTextObjectFactories;
import com.optimaize.langdetect.text.TextObject;
import com.optimaize.langdetect.text.TextObjectFactory;

import java.util.List;

/**
 * Created by sd on 5/1/16.
 */
public class LanguageDetector {
    //load all languages:

    public String detect(String text) {

        List<LanguageProfile> languageProfiles = null;

        try {
            languageProfiles = new LanguageProfileReader().readAllBuiltIn();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        //build language detector:
        com.optimaize.langdetect.LanguageDetector languageDetector = LanguageDetectorBuilder.create(NgramExtractors.standard())
                .withProfiles(languageProfiles)
                .build();

        //create a text object factory
        TextObjectFactory textObjectFactory = CommonTextObjectFactories.forDetectingOnLargeText();

        //query:
        TextObject textObject = textObjectFactory.forText(text);
        List<DetectedLanguage> detected = languageDetector.getProbabilities(textObject);
        for(DetectedLanguage dl: detected) {
            System.out.println(dl.toString());
        }
        return "";
    }


    public static void main(String args[]) {
        LanguageDetector ld = new LanguageDetector();

        System.out.println(ld.detect("Hola, esto es una prueba. I love this place because I love it"));


    }
}
