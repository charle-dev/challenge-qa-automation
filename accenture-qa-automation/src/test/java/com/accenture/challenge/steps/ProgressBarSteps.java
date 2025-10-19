package com.accenture.challenge.steps;

import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.WebDriver;

import com.accenture.challenge.DriverFactory;
import com.accenture.challenge.pages.HomePage;
import com.accenture.challenge.pages.ProgressBarPage;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class ProgressBarSteps {

    private final WebDriver d = DriverFactory.get();
    private final ProgressBarPage page = new ProgressBarPage(d);
    private final HomePage homePage = new HomePage(d);

    @And("seleciono o botao 'Widgets'")
    public void selectWidgets() {
        homePage.selectWidgets();
    }

    @And("seleciono a opcao 'Progress Bar' no menu lateral")
    public void selectProgressBar() {
        page.selectProgressBar();
    }
    
    @When("eu inicio e paro aos 25 por cento")
    public void stopBeforeTwentyFive() {
        page.startAndPauseAtTwentyFive();
    }

    @Then("a barra deve estar menor ou igual a 25 por cento")
    public void assertBelowTwentyFive() {
        Assertions.assertTrue(page.valueNow() <= 25);
    }

    @When("eu inicio novamente ate 100 por cento e reseto")
    public void goToOneHundredAndReset() {
        page.goToOneHundredAndReset();
    }
}
