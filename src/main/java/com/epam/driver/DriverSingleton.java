package com.epam.driver;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.ie.InternetExplorerDriver;

public class DriverSingleton {
    private static final Logger logger = LogManager.getRootLogger();

    private static WebDriver driver;

    private DriverSingleton(){}

    public static void setBrowser(String browser){
        System.setProperty("browser", browser);
    }

    public static WebDriver getDriver(){
        if (null == driver){
            logger.info("\n" + "Test browser is: " + System.getProperty("browser"));
            String browser = System.getProperty("browser").toUpperCase();
            switch (browser){
                case "FIREFOX": {
                    WebDriverManager.firefoxdriver().setup();
                    driver = new FirefoxDriver();
                }
                case "IE":{
                    WebDriverManager.iedriver().setup();
                    driver = new InternetExplorerDriver();
                }
                default: {
                    WebDriverManager.chromedriver().setup();
                    driver = new ChromeDriver();
                }
            }

            driver.manage().window().maximize();
        }
        return driver;
    }

    public static void closeDriver(){
        driver.quit();
        driver = null;
    }
}
