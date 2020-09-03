package com.epam.mail;

import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class LoginPage extends BasePage{
    private static final String URL = "https://gmail.com/";

    private By initialViewLocator = By.id("initialView");
    private By mailInputLocator = By.id("identifierId");
    private By emaiNextButtonLocator = By.cssSelector("div.VfPpkd-RLmnJb");
    private By passwordInputLocator = By.name("password");

    public LoginPage(WebDriver driver){
        super(driver);
    }

    public String loginGmail(String email, String password){
        MailBoxPage mailBoxPage = new MailBoxPage(driver);
        driver.get(URL);
        WebElement mailInput = driver.findElement(mailInputLocator);
        mailInput.sendKeys(email);
        WebElement emailNext = driver.findElement(emaiNextButtonLocator);
        clickElementByJS(emailNext);
        WebElement passwordInput =  wait.until(ExpectedConditions.visibilityOfElementLocated(passwordInputLocator));
        passwordInput.sendKeys(password);
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
