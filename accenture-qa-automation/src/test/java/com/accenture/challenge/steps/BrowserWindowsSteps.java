package com.accenture.challenge.steps;

import org.openqa.selenium.WebDriver;

import com.accenture.challenge.DriverFactory;
import com.accenture.challenge.pages.BrowserWindowsPage;
import com.accenture.challenge.pages.HomePage;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;


public class BrowserWindowsSteps {

    private final WebDriver d = DriverFactory.get();
    private final BrowserWindowsPage browserWindowsPage = new BrowserWindowsPage(d);
    private final HomePage homePage = new HomePage(d);
   
    @And("seleciono o botao 'Alerts, Frame & Windows'")
    public void selectAlertsFramesAndWindows() {
        homePage.selectAlertsFramesAndWindows();
    }

    @And("seleciono a opcao 'Browser Windows' no menu lateral")
    public void selectBrowserWindows() {
        browserWindowsPage.selectBrowserWindows();
    }   

    @When("eu clico no botao New Window")
    public void clickNewWindow() {
        browserWindowsPage.clickNewWindow();
    }

    @Then("uma nova janela e aberta com a mensagem correta")
    public void validateNewWindow() {
        browserWindowsPage.switchToNewWindowAndAssertMessage();
    }

    @And("eu fecho a nova janela")
    public void closeNew() {
        browserWindowsPage.closeNewWindowAndBack();
    }
}
