package com.globant.testing.framework.web.test.pageobject;

import org.openqa.selenium.By;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;
import org.openqa.selenium.support.pagefactory.ByChained;
import org.openqa.selenium.support.pagefactory.DefaultFieldDecorator;
import org.openqa.selenium.support.pagefactory.ElementLocatorFactory;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Juan Krzemien
 */
class CustomFieldDecorator extends DefaultFieldDecorator {

    CustomFieldDecorator(ElementLocatorFactory factory) {
        super(factory);
    }

    private static By extractLocatorFromField(Field field) {
        FindBy findBy = field.getAnnotation(FindBy.class);
        By by = extractLocatorFromFindBy(findBy);
        if (by != null) return by;
        List<By> chained = new ArrayList<>();
        FindBys findBys = field.getAnnotation(FindBys.class);
        if (findBys != null) {
            for (FindBy find : findBys.value()) {
                chained.add(extractLocatorFromFindBy(find));
            }
            if (!chained.isEmpty()) return new ByChained(chained.toArray(new By[0]));
        }
        FindAll findAll = field.getAnnotation(FindAll.class);
        if (findAll != null) {
            for (FindBy find : findAll.value()) {
                chained.add(extractLocatorFromFindBy(find));
            }
            if (!chained.isEmpty()) return new ByChained(chained.toArray(new By[0]));
        }
        return null;
    }

    private static By extractLocatorFromFindBy(FindBy findBy) {
        if (findBy != null) {
            if (!findBy.id().isEmpty()) return By.id(findBy.id());
            if (!findBy.name().isEmpty()) return By.name(findBy.name());
            if (!findBy.css().isEmpty()) return By.cssSelector(findBy.css());
            if (!findBy.xpath().isEmpty()) return By.xpath(findBy.xpath());
            if (!findBy.className().isEmpty()) return By.className(findBy.className());
            if (!findBy.linkText().isEmpty()) return By.linkText(findBy.linkText());
            if (!findBy.partialLinkText().isEmpty()) return By.partialLinkText(findBy.partialLinkText());
            return By.tagName(findBy.tagName());
        }
        return null;
    }

    @Override
    public Object decorate(ClassLoader loader, Field field) {
        Object result = super.decorate(loader, field);
        By locator = extractLocatorFromField(field);
        if (result != null && locator != null) {
            return StalenessHandler.proxyFor(result, loader, locator);
        }
        return null;
    }
}
