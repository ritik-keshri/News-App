package com.example.news;

public class News {

    private String date, tittle, url,sectionName;

    public News(String sectionName, String title, String date, String url) {
        this.tittle = title;
        this.date = date;
        this.url = url;
        this.sectionName=sectionName;
    }

    public String getTittle() {
        return tittle;
    }

    public String getDate() {
        return date;
    }

    public String getUrl() {
        return url;
    }

    public String getSectionName() {
        return sectionName;
    }
}
