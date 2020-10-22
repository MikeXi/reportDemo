package com.epam.mail;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.yandex.qatools.htmlelements.element.HtmlElement;

public class BasePage extends HtmlElement {
    private final Logger logger = LogManager.getRootLogger();
    final WebDriver driver;
    final WebDriverWait wait;
    final JavascriptExecutor jsExecutor;
    final Actions actionProvider;

    public BasePage(WebDriver driver){
        this.driver = driver;
        wait = new WebDriverWait(driver,10);
        jsExecutor = (JavascriptExecutor)driver;
        actionProvider = new Actions(driver);
    }

    public void highlightElementByJS(WebElement element){
        jsExecutor.executeScript("arguments[0].setAttribute(\"style\", arguments[1]);",element, "border: 2px solid red;");
    }

    public void clickElementByJS(WebElement element) {
        objectIsDisplayed(element);
        jsExecutor.executeScript("arguments[0].click();", element);
    }

    public void dragAndDrop(WebElement sourceElement, WebElement targetElement) {
        highlightElementByJS(sourceElement);
        highlightElementByJS(targetElement);
        actionProvider.dragAndDrop(sourceElement, targetElement).build().perform();
    }

    public void contextClick(WebElement element) {
        highlightElementByJS(element);
        actionProvider.contextClick(element).build().perform();
        sleepSeconds(2);
    }

    public void sendKey(Keys key) {
        actionProvider.sendKeys(key).build().perform();
    }

    public void sleepSeconds(int seconds){
        try {
            Thread.sleep(seconds * 1000);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public WebElement objectIsDisplayed(WebElement object){
        int count = 0;
        boolean isNotDisplayed = true;
        while (isNotDisplayed){
            logger.debug("Retry count is: " + count);
            logger.debug("Element is not displayed: " + isNotDisplayed);
            try {
                isNotDisplayed = !object.isDisplayed();
                logger.debug("Element is displayed: " + !isNotDisplayed);
            }catch (NoSuchElementException e) {
                count ++;
                if(count == 5){
                    logger.error(e.getLocalizedMessage());
                    break;
                }
            }
        }
        highlightElementByJS(object);
        return object;
    }
}
