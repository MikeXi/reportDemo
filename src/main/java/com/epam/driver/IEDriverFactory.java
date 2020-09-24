package com.epam.driver;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

public class IEDriverFactory implements DriverFactory {
    @Override
    public WebDriver getDriver(){
        WebDriverManager.iedriver().setup();
        return new InternetExplorerDriver();
    }
}
