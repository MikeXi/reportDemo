package com.epam.mail;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class MessageDialogPageTest extends BasicTest {

    MessageDialogPage messageDialogPage;

    @BeforeClass
    public void setUp(){
        messageDialogPage = new MessageDialogPage(driver);
    }

    @Test(groups = "sendmail",priority = 2)
    public void testGetMailTo(){
        String str = messageDialogPage.getMailContent(emailSubject,"TO");
        Assert.assertEquals(str,"mike_xi@epam.com");
    }

    @Test(groups = "sendmail",priority = 3)
    public void testGetMailSubject(){
        String str = messageDialogPage.getMailContent(emailSubject,"SUBJECT");
        Assert.assertEquals(str,emailSubject);
    }

    @Test(groups = "sendmail",priority = 4)
    public void testGetMailBody(){
        String str = messageDialogPage.getMailContent(emailSubject,"BODY");
        Assert.assertEquals(str,"Automation test email Body");
    }

    @Test(groups = "sendmail",priority = 5)
    public void testSendEmail(){
        boolean sent = messageDialogPage.sendEmail();
        Assert.assertEquals(sent,true);
    }
}
