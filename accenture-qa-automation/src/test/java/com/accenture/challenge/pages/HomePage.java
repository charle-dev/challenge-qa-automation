package com.accenture.challenge.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.accenture.challenge.Ui;

public class HomePage {

    private final WebDriver d;
    private final WebDriverWait w;

    private final String url = "https://demoqa.com/";
    private final By appBtn = By.id("app");
    private final By elementsBtn = By.xpath("//h5[contains(text(), 'Elements')]");
    private final By formsBtn = By.xpath("//h5[contains(text(), 'Forms')]");
    private final By alertsFramesWindowsBtn = By.xpath("//h5[contains(text(), 'Alerts, Frame & Windows')]");
    private final By widgetsBtn = By.xpath("//h5[contains(text(), 'Widgets')]");
    private final By interactionsBtn = By.xpath("//h5[contains(text(), 'Interactions')]");

    public HomePage(WebDriver d) {
        this.d = d;
        this.w = Ui.wait(d);
    }

    public void openDemoQA() {
        d.navigate().to(url);
        Ui.dismissStickyAds(d);
        w.until(ExpectedConditions.visibilityOfElementLocated(appBtn));
    }

    public void selectElements() {
        w.until(ExpectedConditions.visibilityOfElementLocated(elementsBtn));
        Ui.robustClick(d, elementsBtn);
    }

    public void selectForms() {
        w.until(ExpectedConditions.visibilityOfElementLocated(formsBtn));
        Ui.robustClick(d, formsBtn);
    }

    public void selectAlertsFramesAndWindows() {
        w.until(ExpectedConditions.visibilityOfElementLocated(alertsFramesWindowsBtn));
        Ui.robustClick(d, alertsFramesWindowsBtn);
    }

    public void selectWidgets() {
        w.until(ExpectedConditions.visibilityOfElementLocated(widgetsBtn));
        Ui.robustClick(d, widgetsBtn);
    }

    public void selectInteractions() {
        w.until(ExpectedConditions.visibilityOfElementLocated(interactionsBtn));
        Ui.robustClick(d, interactionsBtn);
    }
}
