package extractor.implementation;

import extractor.Extractor;
import org.apache.commons.io.FilenameUtils;


public class ExtractorManager {
    private String extension;

    public ExtractorManager(String url) {
        this.setExtension(url);
    }

    private void setExtension(String url) {
        this.extension = FilenameUtils.getExtension(url);
    }

    public Extractor getExtractor() {
        String ext = this.extension.toLowerCase();

        if (ext.equals("txt")) {
            return new TXTExtractor();
        } else if (ext.equals("pdf")) {
            return new PDFExtractor();
        } else if (ext.equals("odt")) {
            return new ODTExtractor();
        } else if (ext.equals("doc") || ext.equals("docx")) {
            return new DOCXExtractor(ext);
        } else {
            return new HTMLExtractor();
        }
    }
}
