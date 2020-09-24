package com.epam.mail;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import ru.yandex.qatools.htmlelements.loader.decorator.HtmlElementDecorator;
import ru.yandex.qatools.htmlelements.loader.decorator.HtmlElementLocatorFactory;

public class MailBoxPage extends MailBoxArrow{
    private final Logger logger = LogManager.getRootLogger();

    private static final String TO = "mike_xi@epam.model";
    private static final String BODY = "Automation test email Body";

    public MailBoxPage(WebDriver driver){
        super(driver);
        PageFactory.initElements(new HtmlElementDecorator(new HtmlElementLocatorFactory(driver)), this);
    }

    public String getAccountEmail(){
        return super.getAccountEmail();
    }


    public boolean addDraftEmail(String emailSubject){
        return super.addDraftEmail(emailSubject, TO, BODY);
    }

    public WebElement getSentEmail(String emailSubject){
        return super.getSentEmail(emailSubject);
    }

    public WebElement searchEmail(String emailSubject){
        return super.searchEmail(emailSubject);
    }

    public WebElement dragSentMailToStarred(String emailSubject){
        return super.dragSentMailToStarred(emailSubject);
    }

    public WebElement deleteEmail(String emailSubject){
        return super.deleteEmail(emailSubject);
    }

}
