package com.sobri;

public class App {
    public static void main(String[] args) {
        Crawler crawler = new Crawler();
        crawler.search("http://arstechnica.com/", "computer");
    }
}