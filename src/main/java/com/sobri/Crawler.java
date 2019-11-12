package com.sobri;

import java.util.Set;
import java.util.HashSet;
import java.util.List;
import java.util.LinkedList;

public class Crawler {
    private static final int MAX_PAGES_TO_SEARCH = 100;
    private Set<String> pagesVisited = new HashSet<String>();
    private List<String> pagesToVisit = new LinkedList<String>();

    public void search(String url, String searchWord) {
        this.pagesToVisit.add(url);

        while (this.pagesVisited.size() < MAX_PAGES_TO_SEARCH) {
            Spider spider = new Spider();

            if (this.pagesToVisit.isEmpty()) {
                break;
            }

            String currentUrl = this.nextUrl();

            spider.crawl(currentUrl);
            this.pagesToVisit.addAll(spider.getLinks());

            if (spider.searchForWord(searchWord)) {
                System.out.println(String.format("**Success** Word %s found at %s\n", searchWord, currentUrl));
            } else {
                System.out.println(String.format("**Failure** Word %s not found at %s\n", searchWord, currentUrl));
            }
        }

        System.out.println(String.format("**Done** Visited %s web page(s)", this.pagesVisited.size()));
    }

    private String nextUrl() {
        String nextUrl;

        do {
            nextUrl = this.pagesToVisit.remove(0);
        } while (this.pagesVisited.contains(nextUrl));

        this.pagesVisited.add(nextUrl);
        return nextUrl;
    }
}
