package com.accenture.challenge;

import org.openqa.selenium.WebDriver;

import io.cucumber.java.After;
import io.cucumber.java.Before;

public class Hooks {

    @Before("@ui")
    public void beforeUi() {
        DriverFactory.get();
    }

    @After("@ui")
    public void afterUi() {
        WebDriver d = DriverFactory.get();
        if (d != null) {
            d.quit();
        }
        DriverFactory.remove();
    }
}
