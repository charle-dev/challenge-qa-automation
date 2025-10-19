package com.accenture.challenge.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.accenture.challenge.Ui;

public class WebTablesPage {

    private final WebDriver d;
    private final WebDriverWait w;

    private final By webTablesBtn = By.xpath("//span[contains(text(), 'Web Tables')]");
    private final By addBtn = By.id("addNewRecordButton");
    private final By modal = By.id("registration-form-modal");
    private final By firstName = By.id("firstName");
    private final By lastName = By.id("lastName");
    private final By userEmail = By.id("userEmail");
    private final By age = By.id("age");
    private final By salary = By.id("salary");
    private final By department = By.id("department");
    private final By submit = By.id("submit");

    public WebTablesPage(WebDriver d) {
        this.d = d;
        this.w = Ui.wait(d);
    }

    public void selectWebTables() {
        w.until(ExpectedConditions.elementToBeClickable(webTablesBtn));
        Ui.robustClick(d, webTablesBtn);
    }

    public void add(String f, String l, String email, String a, String s, String dept) {
        Ui.robustClick(d, addBtn);
        w.until(ExpectedConditions.visibilityOfElementLocated(modal));

        Ui.typeReliably(d, firstName, f);
        Ui.typeReliably(d, lastName, l);
        Ui.typeReliably(d, userEmail, email);
        Ui.typeReliably(d, age, a);
        Ui.typeReliably(d, salary, s);
        Ui.typeReliably(d, department, dept);

        Ui.robustClick(d, submit);
        waitRowByEmail(email);
    }

    public void addRecord(String f, String l, String email, String a, String s, String dept) {
        add(f, l, email, a, s, dept);
    }

    private void waitRowByEmail(String email) {
        By rowCell = By.xpath("//div[@class='rt-td' and normalize-space()='" + email + "']");
        w.until(ExpectedConditions.visibilityOfElementLocated(rowCell));
    }

    public void editByEmail(String email, String newDept) {
        WebElement rowCell = w.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//div[@class='rt-td' and normalize-space()='" + email + "']")));
        WebElement row = rowCell.findElement(By.xpath("./ancestor::div[contains(@class,'rt-tr')]"));
        WebElement edit = row.findElement(By.cssSelector("span[title='Edit']"));
        Ui.scrollIntoView(d, edit);
        Ui.robustClick(d, edit);

        w.until(ExpectedConditions.visibilityOfElementLocated(modal));
        Ui.typeReliably(d, department, newDept);
        Ui.robustClick(d, submit);
        waitRowByEmail(email);
    }

    public void deleteByEmail(String email) {
        WebElement rowCell = w.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//div[@class='rt-td' and normalize-space()='" + email + "']")));
        WebElement row = rowCell.findElement(By.xpath("./ancestor::div[contains(@class,'rt-tr')]"));
        WebElement del = row.findElement(By.cssSelector("span[title='Delete']"));
        Ui.scrollIntoView(d, del);
        Ui.robustClick(d, del);
    }

    public boolean isDeleted(String email) {
        return d.findElements(By.xpath("//div[@class='rt-td' and normalize-space()='" + email + "']")).isEmpty();
    }
}
