package com.globant.sample;

import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
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

    public List<Book> findBooks(final Date from, final Date to) {
        Calendar end = Calendar.getInstance();
        end.setTime(to);
        end.roll(Calendar.YEAR, 1);

        return storeService.stream()
                .filter(book -> from.before(book.getPublished()) && end.getTime().after(book.getPublished()))
                .sorted(Comparator.comparing(Book::getPublished).reversed())
                .collect(toList());
    }
}