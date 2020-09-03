package com.epam.mail;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class MessageDialogPage extends BasePage{

    private By draftMenuLocator = By.cssSelector("div[data-tooltip='Drafts']");
    private By messageDialogLocator = By.cssSelector("div[role='dialog']");
    private By toTextAreaLocator = By.cssSelector("textarea[aria-label='To']");
    private By subjectTextLocator = By.cssSelector("input[name='subjectbox']");
    private By bodyTextLocator = By.cssSelector("div[aria-label='Message Body']");
    private By closeIconLocator = By.cssSelector("img[aria-label='Save & close']");
    private By toEmailLocator = By.cssSelector("input[name='to']");
    private By messageDialogTitleLocator = By.cssSelector("div.aYF");
    private By sendButtonLocator = By.cssSelector("div[data-tooltip^='Send']");

    MailBoxPage mailBoxPage;


    public MessageDialogPage(WebDriver driver){
        super(driver);
        mailBoxPage = new MailBoxPage(driver);
    }

    public void setMailContents(String to, String subject, String body){
        WebElement messageDialog = wait.until(ExpectedConditions.visibilityOfElementLocated(messageDialogLocator));
        WebElement toText = messageDialog.findElement(toTextAreaLocator);
        toText.sendKeys(to);
        WebElement subjectText = messageDialog.findElement(subjectTextLocator);
        subjectText.sendKeys(subject);
        WebElement bodyText = messageDialog.findElement(bodyTextLocator);
        bodyText.sendKeys(body);
    }

    public String getMailContent(String emailSubject, String field){
        WebElement messageDialog = wait.until(ExpectedConditions.visibilityOfElementLocated(messageDialogLocator));
        switch (field){
            case "TO":
                WebElement toText = messageDialog.findElement(toEmailLocator);
                return toText.getAttribute("value");
            case "SUBJECT":
                WebElement subjectText = messageDialog.findElement(messageDialogTitleLocator);
                return subjectText.getText();
            default:
                WebElement bodyText = messageDialog.findElement(bodyTextLocator);
                return bodyText.getText();
        }
    }

    public Boolean sendEmail(){
        int beforeSendDraftCount = mailBoxPage.getDraftMailCount();
        WebElement emailDialog = driver.findElement(messageDialogLocator);
        WebElement sendButton = emailDialog.findElement(sendButtonLocator);
        sendButton.click();
        mailBoxPage.sleepSeconds(2);
        int afterSendDraftCount = mailBoxPage.getDraftMailCount();
        if(afterSendDraftCount == beforeSendDraftCount - 1){
            return true;
        }else{
            return false;
        }
    }

    public void closeMessageDialog(){
        WebElement closeIcon = driver.findElement(closeIconLocator);
        closeIcon.click();
    }
}
