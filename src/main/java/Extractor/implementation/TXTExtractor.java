package Extractor.implementation;

import Extractor.Extractor;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class TXTExtractor extends Extractor {

    @Override
    public String getText(String filename) {
        try {
            FileInputStream fs = new FileInputStream(filename);
            Scanner sn = new Scanner(fs);
            String text = "";
            while (sn.hasNext()) {
                text += sn.next();
            }
            return text;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return "";
    }
}
