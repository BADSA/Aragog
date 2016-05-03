package Extractor.implementation;

import Extractor.Extractor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class HTMLExtractor extends Extractor{


    @Override
    public String getText(String filename){
        try {
            String html = "";
            FileInputStream fs = new FileInputStream(filename);
            Scanner sn = new Scanner(fs);
            while (sn.hasNext()) {
                html +=" "+ sn.next();
            }


            Document htmlDocument = Jsoup.parse(html);

            String text = htmlDocument.text();

            Elements linksOnPage = htmlDocument.select("a[href]");
            for(Element link : linksOnPage)
            {
                text += '\n'+link.absUrl("href") ;
            }

            return text;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return "";
    }


}