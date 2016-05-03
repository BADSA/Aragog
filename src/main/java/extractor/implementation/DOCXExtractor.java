package extractor.implementation;

import extractor.Extractor;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class DOCXExtractor extends Extractor{


    private String ext;

    public DOCXExtractor(String ext) {
        this.ext = ext;
    }


    @Override
    public String getText(String filename) {
        if (this.ext.equals("doc")) {
            return this.extractFromDoc(filename);
        }else{
            return this.extracFromDocx(filename);
        }
    }

    private String extractFromDoc(String filename) {
        File file = null;
        WordExtractor extractor = null;
        String text = "";
        try {

            file = new File(filename);
            FileInputStream fis = new FileInputStream(file.getAbsolutePath());
            HWPFDocument document = new HWPFDocument(fis);
            extractor = new WordExtractor(document);
            String[] fileData = extractor.getParagraphText();
            for (int i = 0; i < fileData.length; i++) {
                if (fileData[i] != null)
                    text += fileData[i] + " ";
            }

            return text;
        } catch (Exception exep) {
            exep.printStackTrace();
        }

        return text;
    }

    private String extracFromDocx(String filename) {
        XWPFDocument docx = null;
        try {
            docx = new XWPFDocument(
                    new FileInputStream(filename));

            //using XWPFWordExtractor Class
            XWPFWordExtractor we = new XWPFWordExtractor(docx);
            return we.getText();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}