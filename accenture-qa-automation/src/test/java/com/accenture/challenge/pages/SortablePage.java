package com.accenture.challenge.pages;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.accenture.challenge.Ui;

public class SortablePage {

    private final WebDriver d;
    private final WebDriverWait w;

    private final By sortableBtn = By.xpath("//span[contains(text(), 'Sortable')]");
    private final By sortableContainer = By.id("sortableContainer");
    private final By adsBanner = By.id("Ad.Plus-970x250-1");
    private final By listTabBtn = By.id("demo-tab-list");
    private final By listItems = By.cssSelector("#demo-tabpane-list .list-group-item");
    private final By listPanel = By.id("demo-tabpane-list");

    public SortablePage(WebDriver d) {
        this.d = d;
        this.w = Ui.wait(d);
        w.pollingEvery(java.time.Duration.ofMillis(200));
    }

    public void selectSortable() {
        w.until(ExpectedConditions.elementToBeClickable(sortableBtn));
        Ui.robustClick(d, sortableBtn);
    }

    public void waitUntilAdsLoad(WebDriver d) {
        w.until(ExpectedConditions.elementToBeClickable(adsBanner));
    }

    public void centeringSortableContainer() {
        w.until(ExpectedConditions.elementToBeClickable(sortableContainer));
        Ui.scrollIntoView(d, d.findElement(sortableContainer));
    }

    public void sortDescendingAndAssert() {
        List<String> expected = Arrays.asList("Six", "Five", "Four", "Three", "Two", "One");

        try {
            w.until(ExpectedConditions.elementToBeClickable(listTabBtn)).click();
        } catch (Exception ignore) {
        }
        w.until(ExpectedConditions.visibilityOfElementLocated(listPanel));
        w.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(listItems));

        for (int i = 0; i < expected.size(); i++) {
            final int expectedIndex = i;
            String shouldBeHere = expected.get(i);

            List<WebElement> items = d.findElements(listItems);
            int currentIndex = indexOf(items, shouldBeHere);
            if (currentIndex == -1) {
                throw new NoSuchElementException("Item não encontrado: " + shouldBeHere);
            }

            if (currentIndex != expectedIndex) {
                WebElement source = items.get(currentIndex);
                WebElement target = items.get(expectedIndex);

                String before = currentListTexts().toString();
                dragAndDrop(source, target);

                w.withTimeout(Duration.ofSeconds(5))
                        .pollingEvery(Duration.ofMillis(150))
                        .until(dr -> {
                            List<String> now = currentListTexts();
                            boolean movedIndex = indexOf(dr.findElements(listItems), shouldBeHere) == expectedIndex;
                            boolean changed = !before.equals(now.toString());
                            return movedIndex && changed;
                        });
            }
        }

        List<String> actual = currentListTexts();
        if (!expected.equals(actual)) {
            throw new AssertionError("Ordem esperada: " + expected + " | Ordem atual: " + actual);
        }
    }

    public void assertDescendingByText() {
        List<String> expected = Arrays.asList("Six", "Five", "Four", "Three", "Two", "One");
        List<String> actual = currentListTexts();
        if (!expected.equals(actual)) {
            throw new AssertionError("Ordem esperada: " + expected + " | Ordem atual: " + actual);
        }
    }

    private List<String> currentListTexts() {
        w.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(listItems));
        return d.findElements(listItems).stream()
                .map(WebElement::getText)
                .map(String::trim)
                .collect(Collectors.toList());
    }

    private int indexOf(List<WebElement> elements, String text) {
        for (int i = 0; i < elements.size(); i++) {
            if (text.equalsIgnoreCase(elements.get(i).getText().trim())) {
                return i;
            }
        }
        return -1;
    }

    private void dragAndDrop(WebElement source, WebElement target) {
        Ui.scrollIntoView(d, source);
        Ui.scrollIntoView(d, target);

        try {
            new org.openqa.selenium.interactions.Actions(d)
                    .clickAndHold(source)
                    .moveToElement(target, 0, 8)
                    .pause(Duration.ofMillis(200))
                    .release()
                    .build()
                    .perform();
            microWait();
            return;
        } catch (Exception ignored) {
        }

        try {
            org.openqa.selenium.Point s = source.getLocation();
            org.openqa.selenium.Dimension sd = source.getSize();
            org.openqa.selenium.Point t = target.getLocation();
            org.openqa.selenium.Dimension td = target.getSize();

            int startX = s.getX() + sd.getWidth() / 2;
            int startY = s.getY() + sd.getHeight() / 2;
            int endX = t.getX() + td.getWidth() / 2;
            int endY = t.getY() + td.getHeight() / 2 + 6;

            int moveX = endX - startX;
            int moveY = endY - startY;

            new org.openqa.selenium.interactions.Actions(d)
                    .moveToElement(source, sd.getWidth() / 2, sd.getHeight() / 2)
                    .clickAndHold()
                    .pause(Duration.ofMillis(80))
                    .moveByOffset(moveX, moveY)
                    .pause(Duration.ofMillis(200))
                    .release()
                    .build()
                    .perform();
            microWait();
            return;
        } catch (Exception ignored) {
        }

        try {
            jsHtml5DragAndDrop(source, target);
            microWait();
        } catch (Exception ignored) {
        }
    }

    private void microWait() {
        try {
            Thread.sleep(120);
        } catch (InterruptedException ignored) {
        }
    }

    private void jsHtml5DragAndDrop(WebElement source, WebElement target) {
        String script
                = "function dt(){return new DataTransfer();}"
                + "var src=arguments[0], trg=arguments[1];"
                + "var dataTransfer=dt();"
                + "src.dispatchEvent(new DragEvent('dragstart',{bubbles:true,cancelable:true,dataTransfer:dataTransfer}));"
                + "trg.dispatchEvent(new DragEvent('dragenter',{bubbles:true,cancelable:true,dataTransfer:dataTransfer}));"
                + "trg.dispatchEvent(new DragEvent('dragover',{bubbles:true,cancelable:true,dataTransfer:dataTransfer}));"
                + "trg.dispatchEvent(new DragEvent('drop',{bubbles:true,cancelable:true,dataTransfer:dataTransfer}));"
                + "src.dispatchEvent(new DragEvent('dragend',{bubbles:true,cancelable:true,dataTransfer:dataTransfer}));";
        ((JavascriptExecutor) d).executeScript(script, source, target);
    }
}
