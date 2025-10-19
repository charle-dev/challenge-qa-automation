package com.accenture.challenge.steps;

import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.WebDriver;

import com.accenture.challenge.DriverFactory;
import com.accenture.challenge.Ui;
import com.accenture.challenge.pages.HomePage;
import com.accenture.challenge.pages.PracticeFormPage;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class PracticeFormSteps {

    private final WebDriver d = DriverFactory.get();
    private final PracticeFormPage practiceFormsPage = new PracticeFormPage(d);
    private final HomePage homePage = new HomePage(d);
    private String email;

    @And("seleciono o botao 'Forms'")
    public void selectForms() {
        homePage.selectForms();
    }

    @And("seleciono a opcao 'Practice Form' no menu lateral")
    public void selectPracticeForm() {
        practiceFormsPage.selectPracticeForm();
    }

    @When("eu preencho o formulario com dados validos")
    public void fillForm() {
        practiceFormsPage.waitUntilAdsLoad(d);
        practiceFormsPage.centeringFormContainer();
        email = Ui.randomEmail();
        practiceFormsPage.fillBasic("Carlos", "Test", email, "9999999999");
        practiceFormsPage.setDateOfBirth(10, 1992, "June");
        practiceFormsPage.setSubject("Maths");
        practiceFormsPage.selectHobbySports();
    }

    @And("eu faco upload do arquivo de texto")
    public void uploadTextArchive() {
        practiceFormsPage.uploadFile();
    }

    @And("eu submeto o formulario")
    public void submitForm() {
        practiceFormsPage.submit();
    }

    @Then("o popup de confirmacao e exibido")
    public void modalVisible() {
        Assertions.assertTrue(practiceFormsPage.modalVisible());
    }

    @And("eu fecho o popup")
    public void closeModal() {
        practiceFormsPage.closeModal();
    }
}
