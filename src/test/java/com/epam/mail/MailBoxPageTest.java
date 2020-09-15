package com.epam.mail;

import com.epam.util.TestListener;
import org.openqa.selenium.WebElement;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@Listeners({TestListener.class})
public class MailBoxPageTest extends BasicTest{

    MailBoxPage mailBoxPage;

    @BeforeClass
    public void setUp(){
        mailBoxPage = new MailBoxPage(driver);
    }

    @Test(groups = "sendmail",priority = 1)
    public void testAddDraftMail(){
        boolean draftAdded = mailBoxPage.addDraftEmail(emailSubject);
        assertThat(draftAdded, is(equalTo(true)));
    }

    @Test(groups = "sendmail",priority = 6)
    public void testGetSentEmail(){
        WebElement sent = mailBoxPage.getSentEmail(emailSubject);
        assertThat(sent, is(notNullValue()));
    }

    @Test(groups = "sendmail",priority = 7)
    public void testSearchEmail(){
        WebElement searchedEmail = mailBoxPage.searchEmail(emailSubject);
        assertThat(searchedEmail, is(notNullValue()));
    }

    @Test(groups = "sendmail",priority = 8)
    public void testDragSentEmailToStarred(){
        WebElement starredEmail = mailBoxPage.dragSentMailToStarred(emailSubject);
        assertThat(starredEmail, is(notNullValue()));
    }

    @Test(groups = "sendmail",priority = 9)
    public void testDeleteEmail(){
        WebElement deletedEmail = mailBoxPage.deleteEmail(emailSubject);
        assertThat(deletedEmail, is(equalTo(null)));
    }

    @Test(groups = "logout")
    public void testLogout(){
        boolean logedOut = mailBoxPage.logOut();
        assertThat(logedOut, is(equalTo(true)));
    }

}
