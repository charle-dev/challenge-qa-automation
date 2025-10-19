package com.accenture.challenge.steps;

import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.WebDriver;

import com.accenture.challenge.DriverFactory;
import com.accenture.challenge.Ui;
import com.accenture.challenge.pages.HomePage;
import com.accenture.challenge.pages.WebTablesPage;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class WebTablesSteps {

    private final WebDriver d = DriverFactory.get();
    private final WebTablesPage webTablesPage = new WebTablesPage(d);
    private final HomePage homePage = new HomePage(d);
    private String email;

    @And("seleciono o botao 'Elements'")
    public void selectElements() {
        homePage.selectElements();
    }

    @And("seleciono a opcao 'Web Tables' no menu lateral")
    public void selectWebTables() {
        webTablesPage.selectWebTables();
    }

    @When("eu crio um novo registro")
    public void create() {
        email = Ui.randomEmail();
        webTablesPage.add("Ana", "Silva", email, "28", "6000", "QA");
    }

    @And("eu edito o registro criado")
    public void edit() {
        webTablesPage.editByEmail(email, "Quality");
    }

    @And("eu deleto o registro criado")
    public void delete() {
        webTablesPage.deleteByEmail(email);
    }

    @Then("o registro nao deve mais existir")
    public void assertDeleted() {
        Assertions.assertTrue(webTablesPage.isDeleted(email));
    }
}
