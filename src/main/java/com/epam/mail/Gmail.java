package com.epam.mail;

import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Gmail {
    private static final String DRIVER_LOCATION = ".\\drivers\\chromedriver.exe";
    private static final String URL = "https://gmail.com/";
    private static final String TO = "mike_xi@epam.com";
    private static final String SUBJECT = "Automation test email Subject";
    private static final String BODY = "Automation test email Body";
    private WebDriver driver;

    public Gmail(){
        System.setProperty("webdriver.chrome.driver", DRIVER_LOCATION);
        ChromeOptions options = new ChromeOptions();
        options.addArguments("start-maximized");
        options.setExperimentalOption("useAutomationExtension", false);
        options.setExperimentalOption("excludeSwitches", Collections.singletonList("enable-automation"));
        driver =  new ChromeDriver(options);
    }

    public String loginGmail(String email, String password){
        driver.get(URL);
        WebElement mailInput = driver.findElement(By.id("identifierId"));
        mailInput.sendKeys(email);
        WebElement emailNext = driver.findElement(By.cssSelector("div.VfPpkd-RLmnJb"));
        emailNext.click();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        WebElement passwordInput = driver.findElement(By.name("password"));
        passwordInput.sendKeys(password);
        try {
            WebElement passwordNext = driver.findElement(By.className("VfPpkd-RLmnJb"));
            passwordNext.click();
        }catch (StaleElementReferenceException e){
            WebElement passwordNext = driver.findElement(By.className("VfPpkd-RLmnJb"));
            passwordNext.click();
        }
        WebDriverWait wait = new WebDriverWait(driver,30);
        WebElement account = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("a[class='gb_D gb_Ua gb_i']")));
        String accountInfos = account.getAttribute("aria-label");
        String[] accountInfo = accountInfos.split(":");
        return accountInfo[1].trim();
    }

    public String addDraftEmail(){
        WebElement addButton = driver.findElement(By.cssSelector("div[gh='cm']"));
        addButton.click();
        setMailContents(TO,SUBJECT,BODY);
        WebElement closeIcon = driver.findElement(By.cssSelector("img[aria-label='Save & close']"));
        closeIcon.click();

        tabClick("Drafts");
        WebElement subjectCell = getRow();
        subjectCell.click();
        return subjectCell.getText();
    }

    public String getMailContent(String field){
        WebElement emailDialog = driver.findElement(By.cssSelector("div[role='dialog']"));
        switch (field){
            case "TO":
                WebElement toText = emailDialog.findElement(By.cssSelector("input[name='to']"));
                return toText.getAttribute("value");
            case "SUBJECT":
                WebElement subjectText = emailDialog.findElement(By.cssSelector("div.aYF"));
                return subjectText.getText();
            default:
                WebElement bodyText = emailDialog.findElement(By.cssSelector("div[aria-label='Message Body']"));
                return bodyText.getText();
        }
    }

    public WebElement sendEmail(){
        WebElement emailDialog = driver.findElement(By.cssSelector("div[role='dialog']"));
        WebElement sendButton = emailDialog.findElement(By.cssSelector("div[data-tooltip^='Send']"));
        sendButton.click();
        WebElement row = getRow();
        return row;
    }

    public String getSentEmail(){
        tabClick("Sent");
        WebElement row = getRow();
        return row.getText();
    }

    public boolean logOut(){
        WebElement account = driver.findElement(By.cssSelector("a[class='gb_D gb_Ua gb_i']"));
        account.click();
        WebElement logOutButton = driver.findElement(By.cssSelector("a[class='gb_Mb gb_mg gb_ug gb_7e gb_7c']"));
        logOutButton.click();
        WebElement login = driver.findElement(By.id("initialView"));
        if(login.isDisplayed()){
            return true;
        }else{
            return false;
        }
    }

    public void setMailContents(String to, String subject, String body){
        WebElement newMessageDialog = driver.findElement(By.cssSelector("div[role='dialog']"));
        WebElement toText = newMessageDialog.findElement(By.cssSelector("textarea[aria-label='To']"));
        toText.sendKeys(to);
        WebElement subjectText = newMessageDialog.findElement(By.cssSelector("input[aria-label='Subject']"));
        subjectText.sendKeys(subject);
        WebElement bodyText = newMessageDialog.findElement(By.cssSelector("div[aria-label='Message Body']"));
        bodyText.sendKeys(body);
    }

    public void tabClick(String tabName){
        WebElement draftsBox = driver.findElement(By.cssSelector("div[data-tooltip='" + tabName + "']"));
        draftsBox.click();
        try {
            Thread.sleep(5000);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public WebElement getRow(){
        List<WebElement> row = driver.findElements(By.cssSelector("tr[role='row']"));
        int rowCount = row.size();
        if(rowCount == 1){
            return null;
        }else{
            WebElement subjectCell = row.get(1).findElement(By.cssSelector("span.bog"));
            return subjectCell;
        }
    }
}
