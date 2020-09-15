package com.epam.mail;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.epam.model.User;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class LoginPage extends BasePage{
    private static final String URL = "https://gmail.com/";
    private final Logger logger = LogManager.getRootLogger();

    private By initialViewLocator = By.id("initialView");
    private By mailInputLocator = By.id("identifierId");
    private By emaiNextButtonLocator = By.cssSelector("div.VfPpkd-RLmnJb");
    private By passwordInputLocator = By.name("password");

    public LoginPage(WebDriver driver){
        super(driver);
    }

    public String loginGmail(User user){
        MailBoxPage mailBoxPage = new MailBoxPage(driver);
        driver.get(URL);
        logger.info("Go to: " + URL);
        WebElement mailInput = driver.findElement(mailInputLocator);
        mailInput.sendKeys(user.getUsername());
        WebElement emailNext = driver.findElement(emaiNextButtonLocator);
        clickElementByJS(emailNext);
        WebElement passwordInput =  wait.until(ExpectedConditions.visibilityOfElementLocated(passwordInputLocator));
        passwordInput.sendKeys(user.getPassword());
        try {
            WebElement passwordNext = driver.findElement(emaiNextButtonLocator);
            clickElementByJS(passwordNext);
        }catch (StaleElementReferenceException e){
            WebElement passwordNext = driver.findElement(emaiNextButtonLocator);
            clickElementByJS(passwordNext);
        }
        return mailBoxPage.getAccountEmail();
    }

    public WebElement getInitialView(){
        return wait.until(ExpectedConditions.visibilityOfElementLocated(initialViewLocator));
    }
}
