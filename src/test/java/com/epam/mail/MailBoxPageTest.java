package com.epam.mail;

import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;


public class MailBoxPageTest extends BasicTest{

    MailBoxPage mailBoxPage;

    @BeforeClass
    public void setUp(){
        mailBoxPage = new MailBoxPage(driver);
    }

    @Test(groups = "sendmail",priority = 1)
    public void testAddDraftMail(){
        boolean draftAdded = mailBoxPage.addDraftEmail(emailSubject);
        Assert.assertTrue(draftAdded);
    }

    @Test(groups = "sendmail",priority = 6)
    public void testGetSentEmail(){
        WebElement sent = mailBoxPage.getSentEmail(emailSubject);
        Assert.assertNotNull(sent);
    }

    @Test(groups = "sendmail",priority = 7)
    public void testSearchEmail(){
        WebElement searchedEmail = mailBoxPage.searchEmail(emailSubject);
        Assert.assertNotNull(searchedEmail);
    }

    @Test(groups = "sendmail",priority = 8)
    public void testDragSentEmailToStarred(){
        WebElement starredEmail = mailBoxPage.dragSentMailToStarred(emailSubject);
        Assert.assertNotNull(starredEmail);
    }

    @Test(groups = "sendmail",priority = 9)
    public void testDeleteEmail(){
        WebElement deletedEmail = mailBoxPage.deleteEmail(emailSubject);
        Assert.assertNull(deletedEmail);
    }

    @Test(groups = "logout")
    public void testLogout(){
        boolean logedOut = mailBoxPage.logOut();
        Assert.assertTrue(logedOut);
    }

}
