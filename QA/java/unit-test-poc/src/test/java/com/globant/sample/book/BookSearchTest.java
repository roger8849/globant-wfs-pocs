package com.globant.sample.book;

import com.globant.sample.Book;
import com.globant.sample.Library;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Sample plain old unit test
 *
 * @author Juan Krzemien
 */
@RunWith(MockitoJUnitRunner.class)
public class BookSearchTest {

    // Could also be @Mock
    private List<Book> mockStoreService = new ArrayList<>();

    private Library classUnderTest = new Library(mockStoreService);

    @Test
    public void findStoredBook() {
        // Precondition
        Book aBook = new Book("A Title", "Ruso", LocalDate.now().minusMonths(6));
        mockStoreService.add(aBook);

        // Invocation arguments
        LocalDate from = LocalDate.now().minusYears(1);
        LocalDate to = LocalDate.now();

        // Actual call
        List<Book> result = classUnderTest.findBooks(from, to);

        // Validations
        assertThat(result.size(), equalTo(1));
        assertThat(result.get(0), equalTo(aBook));
    }

    @Test
    public void findStoredBookOutOfDateRange() {
        // Precondition
        Book aBook = new Book("A Title", "Ruso", LocalDate.now().minusYears(6));
        mockStoreService.add(aBook);

        // Invocation arguments
        LocalDate from = LocalDate.now().minusYears(1);
        LocalDate to = LocalDate.now();

        // Actual call
        List<Book> result = classUnderTest.findBooks(from, to);

        // Validations
        assertThat(result.size(), equalTo(0));
    }

}