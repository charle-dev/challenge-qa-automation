package com.accenture.challenge.steps;

import org.openqa.selenium.WebDriver;

import com.accenture.challenge.DriverFactory;
import com.accenture.challenge.pages.HomePage;

import io.cucumber.java.en.Given;

public class HomePageSteps {
    private final WebDriver d = DriverFactory.get();
    private final HomePage homePage = new HomePage(d);

    @Given("que acesso a pagina DemoQA")
    public void openDemoQA() {
        homePage.openDemoQA();
    }

}
