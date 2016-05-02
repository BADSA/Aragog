import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Crawler {

    private List<String> links = new LinkedList<String>();
    private Document htmlDocument;
    private Boolean status = false;

    public Crawler(String url) {
        this.status = this.crawl(url);
    }

    public boolean status() {
        return this.status;
    }

    // This method retrive the page and fill a list with all
    // Return false if text it is not a valid url
    private boolean crawl(String url) {
        try {
            Connection connection = Jsoup.connect(url);//.userAgent(USER_AGENT);
            Document htmlDocument = connection.get();
            this.htmlDocument = htmlDocument;
            if(connection.response().statusCode() == 200) {// 200 is the HTTP OK status code
                return false;
            }

            if(!connection.response().contentType().contains("text/html")) {
                return false;
            }

            Elements linksOnPage = htmlDocument.select("a[href]");

            for(Element link : linksOnPage) {
                this.links.add(link.absUrl("href"));
            }

            return true;
        } catch(IOException ioe) {
            // We were not successful in our HTTP request
            return false;
        }
    }

    // Return page content
    public String getDocument(){
        return this.htmlDocument.toString();
    }

    // Return all collected links as a list
    public List<String> getLinks()
    {
        return this.links;
    }

}