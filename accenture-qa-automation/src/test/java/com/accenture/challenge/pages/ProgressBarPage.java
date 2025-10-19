package com.accenture.challenge.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.accenture.challenge.Ui;

public class ProgressBarPage {

    private final WebDriver d;
    private final WebDriverWait w;

    private final By startStopBtn = By.id("startStopButton");
    private final By resetBtn = By.id("resetButton");
    private final By progressBarBtn = By.xpath("//span[contains(text(), 'Progress Bar')]");
    private final By progressBar = By.cssSelector("div[role='progressbar']");

    public ProgressBarPage(WebDriver d) {
        this.d = d;
        this.w = Ui.wait(d);
    }

    public void selectProgressBar() {
        w.until(ExpectedConditions.elementToBeClickable(progressBarBtn));
        Ui.robustClick(d, progressBarBtn);
    }

    public void startAndPauseAtTwentyFive() {
        w.until(ExpectedConditions.elementToBeClickable(startStopBtn));
        Ui.robustClick(d, startStopBtn);
        Ui.shortWait(d).until(ExpectedConditions.attributeContains(progressBar, "aria-valuenow", "25"));
        w.until(Ui.attributeNumberBelowOrEqualsTwentyFive(progressBar, "aria-valuenow"));
        Ui.robustClick(d, startStopBtn);
    }

    public int valueNow() {
        WebElement pb = d.findElement(progressBar);
        return Integer.parseInt(pb.getAttribute("aria-valuenow"));
    }

    public void goToOneHundredAndReset() {
        Ui.robustClick(d, startStopBtn);
        Ui.wait(d).until(ExpectedConditions.attributeContains(progressBar, "aria-valuenow", "100"));
        w.until(Ui.attributeNumberEqualsOneHundred(progressBar, "aria-valuenow"));
        Ui.robustClick(d, resetBtn);
        w.until(Ui.attributeNumberEqualsZero(progressBar, "aria-valuenow"));
    }
}
