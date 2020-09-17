package com.epam.mail;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import ru.yandex.qatools.htmlelements.annotations.Name;
import ru.yandex.qatools.htmlelements.element.Button;
import ru.yandex.qatools.htmlelements.element.TextInput;
import java.awt.*;

@Name("Message From")
@FindBy(css = "div[role='dialog']")
public class MessageDialogArrow extends BasePage {

    @Name("Draft Email menu")
    @FindBy(css = "div[data-tooltip='Drafts']")
    private Menu draftEmailMenu;

    @Name("To input")
    @FindBy(css = "textarea[aria-label='To']")
    private TextInput toInput;

    @Name("Subject input")
    @FindBy(css = "input[name='subjectbox']")
    private TextInput subjectInput;

    @Name("Body input")
    @FindBy(css = "div[aria-label='Message Body']")
    private WebElement bodyInput;

    @Name("Close iocn")
    @FindBy(css = "img[aria-label='Save & close']")
    private Button closeIcon;

    @Name("To label")
    @FindBy(css = "input[name='to']")
    private TextInput toLabel;

    @Name("Subject label")
    @FindBy(css = "div.aYF")
    private WebElement subjectlabel;

    @Name("Send button")
    @FindBy(css = "div[data-tooltip^='Send']")
    private Button sendButton;

    public MessageDialogArrow(WebDriver driver) {
        super(driver);
    }

    public void setMailContents(String to, String subject, String body){
        toInput.sendKeys(to);
        subjectInput.sendKeys(subject);
        bodyInput.sendKeys(body);
    }

    public String getMailContent(String emailSubject, String field){
        switch (field){
            case "TO":
                return toLabel.getAttribute("value");
            case "SUBJECT":
                return subjectlabel.getText();
            default:
                return bodyInput.getText();
        }
    }

    public Boolean sendEmail(){
        MailBoxPage mailBoxPage = new MailBoxPage(driver);
        int beforeSendDraftCount = mailBoxPage.getDraftMailCount();
        sleepSeconds(1);
        sendButton.click();
        sleepSeconds(2);
        int afterSendDraftCount = mailBoxPage.getDraftMailCount();
        if(afterSendDraftCount == beforeSendDraftCount - 1){
            return true;
        }else{
            return false;
        }
    }

    public void closeMessageDialog(){
        closeIcon.click();
    }

}
