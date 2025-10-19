package com.accenture.challenge.steps;

import org.openqa.selenium.WebDriver;

import com.accenture.challenge.DriverFactory;
import com.accenture.challenge.pages.HomePage;
import com.accenture.challenge.pages.SortablePage;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class SortableSteps {

    private final WebDriver d = DriverFactory.get();
    private final SortablePage sortablePage = new SortablePage(d);
    private final HomePage homePage = new HomePage(d);

    @And("seleciono o botao 'Interactions'")
    public void selectInteractions() {
        homePage.selectInteractions();
    }

    @And("seleciono a opcao 'Sortable' no menu lateral")
    public void selectSortable() {
        sortablePage.selectSortable();
    }

    @When("eu reordeno a lista em ordem decrescente")
    public void sortDescending() {
        sortablePage.waitUntilAdsLoad(d);
        sortablePage.centeringSortableContainer();
        sortablePage.sortDescendingAndAssert();
    }

    @Then("a lista deve estar em ordem decrescente")
    public void assertDescending() {
        sortablePage.assertDescendingByText();
    }
}
