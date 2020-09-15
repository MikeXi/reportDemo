package com.epam.mail;

import com.epam.util.TestListener;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

@Listeners({TestListener.class})
public class MessageDialogPageTest extends BasicTest {

    MessageDialogPage messageDialogPage;

    @BeforeClass
    public void setUp(){
        messageDialogPage = new MessageDialogPage(driver);
    }

    @Test(groups = "sendmail",priority = 2)
    public void testGetMailTo(){
        String str = messageDialogPage.getMailContent(emailSubject,"TO");
        assertThat(str, is(equalTo("mike_xi@epam.model")));
    }

    @Test(groups = "sendmail",priority = 3)
    public void testGetMailSubject(){
        String str = messageDialogPage.getMailContent(emailSubject,"SUBJECT");
        assertThat(str, is(equalTo(emailSubject)));
    }

    @Test(groups = "sendmail",priority = 4)
    public void testGetMailBody(){
        String str = messageDialogPage.getMailContent(emailSubject,"BODY");
        assertThat(str, is(equalTo("Automation test email Body")));
        Assert.assertEquals(str,"Automation test email Body");
    }

    @Test(groups = "sendmail",priority = 5)
    public void testSendEmail(){
        boolean sent = messageDialogPage.sendEmail();
        assertThat(sent, is(equalTo(true)));
    }
}
