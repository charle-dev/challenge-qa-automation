package com.accenture.challenge;

import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Ui {

    public static WebDriverWait wait(WebDriver d) {
        WebDriverWait w = new WebDriverWait(d, Duration.ofSeconds(20));
        return w;
    }

    public static WebDriverWait shortWait(WebDriver d) {
        WebDriverWait w = new WebDriverWait(d, Duration.ofSeconds(20));
        w.pollingEvery(java.time.Duration.ofMillis(30));
        return w;
    }

    public static void scrollIntoView(WebDriver d, WebElement el) {
        ((JavascriptExecutor) d).executeScript("arguments[0].scrollIntoView({block:'center'});", el);
    }

    public static void jsClick(WebDriver d, WebElement el) {
        ((JavascriptExecutor) d).executeScript("arguments[0].click()", el);
    }

    public static void robustClick(WebDriver d, By locator) {
        WebElement el = wait(d).until(ExpectedConditions.elementToBeClickable(locator));
        scrollIntoView(d, el);
        try {
            el.click();
            return;
        } catch (Throwable ignore) {
        }
        jsClick(d, el);
    }

    public static void robustClick(WebDriver d, WebElement el) {
        scrollIntoView(d, el);
        try {
            el.click();
            return;
        } catch (Throwable ignore) {
        }
        try {
            new Actions(d).moveToElement(el).pause(Duration.ofMillis(100)).click().perform();
            return;
        } catch (Throwable ignore) {
        }
        jsClick(d, el);
    }

    public static void typeReliably(WebDriver d, By locator, String text) {
        WebElement el = wait(d).until(ExpectedConditions.visibilityOfElementLocated(locator));
        scrollIntoView(d, el);
        el.click();
        try {
            el.clear();
        } catch (Throwable ignore) {
        }
        el.sendKeys(Keys.chord(Keys.CONTROL, "a"));
        el.sendKeys(text);
        el.sendKeys(Keys.TAB);
    }

    public static ExpectedCondition<Boolean> attributeNumberBelowOrEqualsTwentyFive(By locator, String attribute) {
        return driver -> {
            WebElement el = driver.findElement(locator);
            int value = Integer.parseInt(el.getAttribute(attribute));
            int twentyFive = 25;
            try {
                assertEquals(value, twentyFive);
                return value == twentyFive;
            } catch (Exception e) {
                return false;
            }
        };
    }

    public static ExpectedCondition<Boolean> attributeNumberEqualsOneHundred(By locator, String attribute) {
        return driver -> {
            WebElement el = driver.findElement(locator);
            int value = Integer.parseInt(el.getAttribute(attribute));
            int oneHundred = 100;
            try {
                assertEquals(value, oneHundred);
                return value == oneHundred;
            } catch (Exception e) {
                return false;
            }
        };
    }

    public static ExpectedCondition<Boolean> attributeNumberEqualsZero(By locator, String attribute) {
        return driver -> {
            WebElement el = driver.findElement(locator);
            int value = Integer.parseInt(el.getAttribute(attribute));
            int zero = 0;
            try {
                assertEquals(value, zero);
                return value == zero;
            } catch (Exception e) {
                return false;
            }
        };
    }

    public static void dismissStickyAds(WebDriver d) {
        try {
            ((JavascriptExecutor) d).executeScript(
                    "document.querySelectorAll('[id*=ad], .fixedban, .adplus, .advertisement, iframe').forEach(e=>e.remove());"
            );
        } catch (Exception ignored) {
        }
    }

    public static String randomEmail() {
        String user = UUID.randomUUID().toString().replace("-", "").substring(0, 10);
        return user + "@example.com";
    }

    public static String ensureTempFile(String name, String content) {
        try {
            Path dir = Path.of("src", "test", "resources", "files");
            Files.createDirectories(dir);
            Path file = dir.resolve(name);
            if (content != null) {
                Files.writeString(file, content);
            } else if (!Files.exists(file)) {
                Files.writeString(file, "sample file for upload\n");
            }
            return file.toAbsolutePath().toString();
        } catch (Exception e) {
            throw new RuntimeException("Failed to create temp file", e);
        }
    }

}
