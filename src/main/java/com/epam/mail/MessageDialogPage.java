package com.epam.mail;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import ru.yandex.qatools.htmlelements.loader.decorator.HtmlElementDecorator;
import ru.yandex.qatools.htmlelements.loader.decorator.HtmlElementLocatorFactory;

public class MessageDialogPage extends MessageDialogArrow{
    private final Logger logger = LogManager.getRootLogger();

    public MessageDialogPage(WebDriver driver){
        super(driver);
        PageFactory.initElements(new HtmlElementDecorator(new HtmlElementLocatorFactory(driver)), this);
    }

    public void setMailContents(String to, String subject, String body){
        super.setMailContents(to, subject, body);
    }

    public String getMailContent(String emailSubject, String field){
        return super.getMailContent(emailSubject, field);
    }

    public Boolean sendEmail(){
        return super.sendEmail();
    }

    public void closeMessageDialog(){
       super.closeMessageDialog();
    }
}
