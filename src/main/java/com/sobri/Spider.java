package com.sobri;

import org.jsoup.Jsoup;
import org.jsoup.Connection;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.List;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;

public class Spider {
    private Document htmlDocument;
    private List<String> links = new LinkedList<String>();
    private static final String USER_AGENT = "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/535.1 (KHTML, like Gecko) Chrome/13.0.782.112 Safari/535.1";

    public void crawl(String url) {
        try {
            Connection connection = Jsoup.connect(url).userAgent(USER_AGENT);
            Document htmlDocument = connection.get();
            this.htmlDocument = htmlDocument;

            System.out.println("Crawling " + url);

            Elements linksOnPage = htmlDocument.select("a[href]");
            System.out.println("Found (" + linksOnPage.size() + ") links");

            for (Element link : linksOnPage) {
                this.links.add(link.absUrl("href"));
            }

        } catch (IOException ioe) {
            System.out.println("Error in out HTTP request " + ioe);
        }
    }

    public boolean searchForWord(String word) {
        if (this.htmlDocument == null) {
            System.out.println("ERROR! Call crawl() before performing analysis on the document");
            return false;
        }

        System.out.println("Searching for the word " + word + "...");
        String bodyText = this.htmlDocument.body().text();
        return bodyText.toLowerCase().contains(word.toLowerCase());
    }

    public List<String> getLinks() {
        return this.links;
    }
}
