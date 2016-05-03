package Extractor.implementation;

import java.io.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import Extractor.Extractor;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class HTMLExtractor extends Extractor{

    @Override
    public String getText(String filename){
        try {

            String html = "";
            FileInputStream fs = new FileInputStream(filename);
            Scanner sn = new Scanner(fs);
            while (sn.hasNext()) {
                html += sn.next();
            }
            return Jsoup.parse(html).text();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return "";
    }

}