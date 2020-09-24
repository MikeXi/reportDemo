package com.epam.driver;

import org.openqa.selenium.WebDriver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DriverSingleton{
    private static final Logger logger = LogManager.getRootLogger();
    private static DriverSingleton driverSingleton;
    private static boolean initialized = false;
    private static WebDriver driver;

    private DriverSingleton(){}

    public static synchronized DriverSingleton getInstance(){
        if(initialized) return driverSingleton;
        driverSingleton = new DriverSingleton();
        initialized = true;
        return driverSingleton;
    }

    public void setBrowser(String browser){
        System.setProperty("browser", browser);
    }

    public WebDriver getDriver(){
        DriverFactory driverFactory;
        if (null == driver){
            String browser = System.getProperty("browser").toUpperCase();
            logger.info("\n" + "Test browser is: " + browser);
            switch (browser){
                case "CHROME":{
                    driverFactory = new ChromeDriverFactory();
                    break;
                }
                case "FIREFOX": {
                    driverFactory = new FirefoxDriverFactory();
                    break;
                }
                case "IE":{
                    driverFactory = new IEDriverFactory();
                    break;
                }
                default: {
                    throw new IllegalArgumentException("Cannot create driver for type: " + browser);
                }
            }
            driver = driverFactory.getDriver();
            driver.manage().window().maximize();
        }
        return driver;
    }

    public static void closeDriver(){
        driver.quit();
        driver = null;
    }
}
