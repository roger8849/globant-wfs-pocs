package com.globant.testing.framework.web.test.pageobject;

import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;
import static org.openqa.selenium.By.tagName;
import static org.openqa.selenium.By.xpath;

/**
 * @author Juan Krzemien
 */
public abstract class AbstractTable extends PageElement {

    private static final By ROWS_LOCATOR = tagName("tr");
    private static final By COLUMNS_LOCATOR = xpath("td|th|span|div");

    private List<WebElement> dataRows = new ArrayList<>();

    public AbstractTable(By tableLocator) {
        super(tableLocator);
    }

    public boolean isVisible() {
        return getContextElement().isDisplayed();
    }

    private List<WebElement> getRows() {
        if (emptyOrStalled()) {
            dataRows.clear();
            List<WebElement> tempRows = waitFor(c -> {
                List<WebElement> rows = c.findElement(getContextLocator()).findElements(ROWS_LOCATOR);
                if (rows.size() > 0) {
                    return rows;
                } else {
                    return null;
                }
            });
            final int headerColumnsCount = tempRows.get(0).findElements(COLUMNS_LOCATOR).size();
            dataRows.addAll(
                    tempRows
                            .stream()
                            .filter(r -> r.findElements(COLUMNS_LOCATOR).size() == headerColumnsCount)
                            .collect(toList())
            );
        }
        return dataRows;
    }

    private boolean emptyOrStalled() {
        if (dataRows.isEmpty()) return true;
        try {
            return !dataRows.get(0).isDisplayed();
        } catch (StaleElementReferenceException sere) {
            return true;
        }
    }

    public boolean isEmpty() {
        List<WebElement> rows = getRows();
        final int size = rows.size();
        // One is supposed to be the header
        return size <= 1 || size == 2 && "No Quotes Found".equals(getText(rows.get(1)));
    }

    public int getColumnIndex(String columnName) {
        List<WebElement> columns = getRows()
                .get(0) // First row is supposed to be the header row
                .findElements(COLUMNS_LOCATOR);

        return IntStream.range(0, columns.size())
                .reduce((i, j) -> getText(columns.get(i)).contains(columnName) ? i : j)
                .orElse(-1);
    }

    public String getCellContent(int rowIndex, int columnIndex) {
        return getText(getCell(rowIndex, columnIndex));
    }

    public String getRowData(int rowIndex) {
        return getText(getRows().get(rowIndex));
    }

    public WebElement getCell(int rowIndex, int columnIndex) {
        return getRows()
                .get(rowIndex)
                .findElements(COLUMNS_LOCATOR)
                .get(columnIndex);
    }

    public List<String> getColumnValues(int columnIndex) {
        List<String> columnValues = getRows()
                .stream()
                .map(r -> {
                    try {
                        return r.findElements(COLUMNS_LOCATOR)
                                .get(columnIndex)
                                .getText();
                    } catch (IndexOutOfBoundsException e) {
                        return "";
                    }
                })
                .collect(toList());
        // Exclude column header
        if (columnValues.size() > 0) {
            columnValues.remove(0);
        }
        return columnValues;
    }

    public int getRowsCount() {
        return isEmpty() ? 0 : getRows().size() - 1; // When empty, table has 2 rows, when not empty it has headers row too
    }
}

