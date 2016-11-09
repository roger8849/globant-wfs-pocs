package com.globant.sample.features.book;

import com.globant.sample.Book;
import com.globant.sample.Library;
import cucumber.api.Format;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * @author Juan Krzemien
 */
public class BookSearchSteps {

    // Alternative: @Mock
    private List<Book> mockStoreService = new ArrayList<>();

    private Library classUnderTest = new Library(mockStoreService);

    private List<Book> result = new ArrayList<>();

    @Given(".+book with the title '(.+)', written by '(.+)', published in (.+)")
    public void addNewBook(final String title, final String author, @Format("dd MMMMM yyyy") final Date published) {
        Book book = new Book(title, author, published);
        classUnderTest.addBook(book);
    }

    @When("^the customer searches for books published between (\\d+) and (\\d+)$")
    public void setSearchParameters(@Format("yyyy") final Date from, @Format("yyyy") final Date to) {
        result = classUnderTest.findBooks(from, to);
    }

    @Then("(\\d+) books should have been found$")
    public void verifyAmountOfBooksFound(final int booksFound) {
        assertThat(result.size(), equalTo(booksFound));
    }

    @Then("Book (\\d+) should have the title '(.+)'$")
    public void verifyBookAtPosition(final int position, final String title) {
        assertThat(result.get(position - 1).getTitle(), equalTo(title));
    }

}