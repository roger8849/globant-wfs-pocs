package com.globant.sample;

import java.time.LocalDate;

/**
 * Simple sample model for unit testing purposes only.
 *
 * @author Juan Krzemien
 */
public class Book {
    private final String title;
    private final String author;
    private final LocalDate published;

    public Book(String title, String author, LocalDate published) {
        this.title = title;
        this.author = author;
        this.published = published;
    }

    public LocalDate getPublished() {
        return published;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }
}