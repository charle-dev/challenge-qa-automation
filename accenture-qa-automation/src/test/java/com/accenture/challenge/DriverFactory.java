package com.accenture.challenge;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import io.github.bonigarcia.wdm.WebDriverManager;

public class DriverFactory {

    private static final ThreadLocal<WebDriver> TL = new ThreadLocal<>();

    public static WebDriver get() {
        WebDriver d = TL.get();
        if (d == null) {
            WebDriverManager.chromedriver().setup();
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--start-maximized");
            options.addArguments("--disable-gpu");
            options.addArguments("--no-sandbox");
            options.addArguments("--disable-dev-shm-usage");
            options.addArguments("--remote-allow-origins=*");
            d = new ChromeDriver(options);
            d.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(60));
            d.manage().timeouts().scriptTimeout(Duration.ofSeconds(30));
            d.manage().timeouts().implicitlyWait(Duration.ofSeconds(0));
            TL.set(d);
        }
        return TL.get();
    }

    public static void remove() {
        TL.remove();
    }
}
