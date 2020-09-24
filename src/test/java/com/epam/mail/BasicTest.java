package com.epam.mail;

import com.epam.driver.DriverSingleton;
import com.epam.service.TestDataReader;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Parameters;

import java.io.IOException;
import java.util.Random;

public class BasicTest {

    public static WebDriver driver;
    public static String emailSubject;

    @BeforeSuite
    @Parameters({"env", "browser"})
    public void setUp(String env, String browser) throws IOException {
        TestDataReader.setEnvoronment(env);
        DriverSingleton driverSingleton = DriverSingleton.getInstance();
        driverSingleton.setBrowser(browser);
        driver = driverSingleton.getDriver();
        Random r = new Random();
        emailSubject = "Automation test email Subject" + r.nextInt(1000);
    }

    @AfterSuite
    public void closeDriver(){
        DriverSingleton.closeDriver();
    }
}
