package com.accenture.challenge.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.accenture.challenge.Ui;

public class PracticeFormPage {

    private final WebDriver d;
    private final WebDriverWait w;

    private final By practiceFormBtn = By.xpath("//span[contains(text(), 'Practice Form')]");
    private final By adsBanner = By.id("Ad.Plus-970x250-1");
    private final By formContainer = By.id("userForm");
    private final By dateOfBirthInput = By.id("dateOfBirthInput");
    private final By subjectInput = By.id("subjectsInput");
    private final By sportsButton = By.xpath("//label[normalize-space()='Sports']");
    private final By uploadPictureButton = By.id("uploadPicture");
    private final By submitButton = By.id("submit");
    private final By closeLargeModalButton = By.id("closeLargeModal");

    public PracticeFormPage(WebDriver d) {
        this.d = d;
        this.w = Ui.wait(d);
    }

    public void selectPracticeForm() {
        w.until(ExpectedConditions.elementToBeClickable(practiceFormBtn));
        Ui.robustClick(d, practiceFormBtn);
    }

    public void waitUntilAdsLoad(WebDriver d) {
        w.until(ExpectedConditions.elementToBeClickable(adsBanner));
    }

    public void centeringFormContainer() {
        w.until(ExpectedConditions.elementToBeClickable(formContainer));
        Ui.scrollIntoView(d, d.findElement(formContainer));
    }

    public void fillBasic(String first, String last, String email, String mobile) {
        Ui.typeReliably(d, By.id("firstName"), first);
        Ui.typeReliably(d, By.id("lastName"), last);
        Ui.typeReliably(d, By.id("userEmail"), email);
        setGender("Male");
        Ui.typeReliably(d, By.id("userNumber"), mobile);
    }

    public void setGender(String genderText) {
        Ui.robustClick(d, By.xpath("//label[normalize-space()='" + genderText + "']"));
    }

    public void setDateOfBirth(int day, int year, String monthName) {
        Ui.robustClick(d, dateOfBirthInput);
        WebElement month = w.until(ExpectedConditions.visibilityOfElementLocated(By.className("react-datepicker__month-select")));
        WebElement yearSel = w.until(ExpectedConditions.visibilityOfElementLocated(By.className("react-datepicker__year-select")));
        new Select(month).selectByVisibleText(monthName);
        new Select(yearSel).selectByVisibleText(String.valueOf(year));
        By dayCell = By.xpath("//div[contains(@class,'react-datepicker__day') and not(contains(@class,'outside-month')) and normalize-space()='" + day + "']");
        Ui.robustClick(d, dayCell);
    }

    public void setSubject(String subject) {
        w.until(ExpectedConditions.elementToBeClickable(subjectInput));
        Ui.robustClick(d, subjectInput);
        Ui.typeReliably(d, subjectInput, subject);
    }

    public void selectHobbySports() {
        w.until(ExpectedConditions.elementToBeClickable(sportsButton));
        Ui.robustClick(d, sportsButton);
    }

    public void uploadFile() {
        String path = com.accenture.challenge.Ui.ensureTempFile("upload.txt", "hello from test");
        WebElement input = w.until(ExpectedConditions.presenceOfElementLocated(uploadPictureButton));
        input.sendKeys(path);
    }

    public void submit() {
        Ui.robustClick(d, submitButton);
        w.until(ExpectedConditions.visibilityOfElementLocated(By.id("example-modal-sizes-title-lg")));
    }

    public boolean modalVisible() {
        return w.until(ExpectedConditions.visibilityOfElementLocated(By.className("modal-content"))).isDisplayed();
    }

    public void closeModal() {
        Ui.robustClick(d, closeLargeModalButton);
        w.until(ExpectedConditions.invisibilityOfElementLocated(By.className("modal-content")));
    }
}
