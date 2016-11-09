package com.globant.sample;

import java.util.Date;

/**
 * Simple sample model for unit testing purposes only.
 *
 * @author Juan Krzemien
 */
public class Book {
    private final String title;
    private final String author;
    private final Date published;

    public Book(String title, String author, Date published) {
        this.title = title;
        this.author = author;
        this.published = published;
    }

    public Date getPublished() {
        return published;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }
}