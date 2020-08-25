package com.epam.mail;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class GmailTest {
    private static final String USER_MAIL = "mikeximodule7@gmail.com";
    private static final String USER_PASSWORD = "Welcome2@";

    Gmail gmail;

    @BeforeClass
    public void setUp(){
        gmail = new Gmail();
    }


    @Test
    public void testLoginGmail(){
        String str = gmail.loginGmail(USER_MAIL,USER_PASSWORD);
        Assert.assertEquals(str,"Mike Xi  \n" +
                "(mikeximodule7@gmail.com)");
    }

    @Test(dependsOnMethods = "testLoginGmail")
    public void testAddDraftMail(){
        String str = gmail.addDraftEmail();
        Assert.assertEquals(str,"Automation test email Subject");
    }

    @Test(dependsOnMethods = "testAddDraftMail")
    public void testGetMailTo(){
        String str = gmail.getMailContent("TO");
        Assert.assertEquals(str,"mike_xi@epam.com");
    }

    @Test(dependsOnMethods = "testGetMailTo")
    public void testGetMailSubject(){
        String str = gmail.getMailContent("SUBJECT");
        Assert.assertEquals(str,"Automation test email Subject");
    }
    @Test(dependsOnMethods = "testGetMailSubject")
    public void testGetMailBody(){
        String str = gmail.getMailContent("BODY");
        Assert.assertEquals(str,"Automation test email Body");
    }

    @Test(dependsOnMethods = "testGetMailBody")
    public void testSendEmail(){
        WebElement row = gmail.sendEmail();
        Assert.assertEquals(row,null);
    }

    @Test(dependsOnMethods = "testSendEmail")
    public void testGetSentEmail(){
        String subject = gmail.getSentEmail();
        Assert.assertEquals(subject,"Automation test email Subject");
    }

    @Test(dependsOnMethods = "testGetSentEmail")
    public void testLogout(){
        boolean logedOut = gmail.logOut();
        Assert.assertTrue(logedOut);
    }
    //    @AfterSuite
//    public void closeDriver(){
//        driver.close();
//    }

}
