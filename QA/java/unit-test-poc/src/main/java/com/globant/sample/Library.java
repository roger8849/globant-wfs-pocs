package com.globant.sample;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;

import static java.util.stream.Collectors.toList;


/**
 * Simple sample class for unit testing purposes only.
 *
 * @author Juan Krzemien
 */
public class Library {
    private final List<Book> storeService;

    public Library(List<Book> storeService) {
        this.storeService = storeService;
    }

    public void addBook(final Book book) {
        storeService.add(book);
    }

    public List<Book> findBooks(final LocalDate from, final LocalDate to) {
        return storeService.stream()
                .filter(book -> from.isBefore(book.getPublished()) && book.getPublished().isBefore(to))
                .sorted(Comparator.comparing(Book::getPublished).reversed())
                .collect(toList());
    }
}