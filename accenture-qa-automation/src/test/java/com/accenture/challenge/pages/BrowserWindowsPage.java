package com.accenture.challenge.pages;

import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.accenture.challenge.Ui;

public class BrowserWindowsPage {

    private final WebDriver d;
    private final WebDriverWait w;
    private String original;

    private static final By browserWindowsBtn = By.xpath("//span[contains(text(), 'Browser Windows')]");
    private static final By newWindowBtn = By.id("windowButton");
    private static final By sampleHeading = By.id("sampleHeading");

    public BrowserWindowsPage(WebDriver d) {
        this.d = d;
        this.w = Ui.wait(d);
    }

    public void selectBrowserWindows() {
        w.until(ExpectedConditions.elementToBeClickable(browserWindowsBtn));
        Ui.robustClick(d, browserWindowsBtn);
    }

    public void clickNewWindow() {
        original = d.getWindowHandle();
        int before = d.getWindowHandles().size();
        w.until(ExpectedConditions.elementToBeClickable(newWindowBtn));
        Ui.robustClick(d, newWindowBtn);
        w.until(ExpectedConditions.numberOfWindowsToBe(before + 1));
    }

    public void switchToNewWindowAndAssertMessage() {
        if (original == null) {
            original = d.getWindowHandle();
        }

        Set<String> handles = d.getWindowHandles();
        String newHandle = handles.stream()
                .filter(h -> !h.equals(original))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Nova janela não encontrada"));
        d.switchTo().window(newHandle);
        try {
            w.until(dr -> "complete".equals(
                    ((JavascriptExecutor) dr).executeScript("return document.readyState")));
        } catch (Exception ignore) {
        }

        WebElement msg = w.until(ExpectedConditions.visibilityOfElementLocated(sampleHeading));
        String text = msg.getText() == null ? "" : msg.getText().trim();
        if (!"This is a sample page".equals(text)) {
            throw new AssertionError("Mensagem inesperada: [" + text + "]");
        }
    }

    public void closeNewWindowAndBack() {
        if (d.getWindowHandles().size() > 1) {
            d.close();
            d.switchTo().window(original);
        }
    }
}
