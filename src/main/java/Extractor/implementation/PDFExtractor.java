package Extractor.implementation;

import java.io.*;

import org.apache.pdfbox.pdmodel.*;
import org.apache.pdfbox.text.PDFTextStripper;
import Extractor.Extractor;

public class PDFExtractor extends Extractor {

    @Override
    public String getText(String filename) {
        PDDocument pd;
        File input = new File(filename);
        try {
            pd = PDDocument.load(input);
            PDFTextStripper stripper = new PDFTextStripper();
            return stripper.getText(pd);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        return "";
    }
}

