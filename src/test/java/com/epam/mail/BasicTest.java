package com.epam.mail;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import java.util.Collections;
import java.util.Random;

public class BasicTest {

    private static final String DRIVER_LOCATION = ".\\drivers\\chromedriver.exe";

    public static WebDriver driver;
    public static String emailSubject;

    @BeforeSuite
    public void setUp(){
        System.setProperty("webdriver.chrome.driver", DRIVER_LOCATION);
        ChromeOptions options = new ChromeOptions();
        options.addArguments("start-maximized");
        options.setExperimentalOption("useAutomationExtension", false);
        options.setExperimentalOption("excludeSwitches", Collections.singletonList("enable-automation"));
        driver =  new ChromeDriver(options);
        Random r = new Random();
        emailSubject = "Automation test email Subject" + r.nextInt(1000);
    }

    @AfterSuite
    public void closeDriver(){
        driver.quit();
    }
}
