package com.epam.mail;

import com.epam.util.TestListener;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@Listeners({TestListener.class})
public class LogoutPageTest extends BasicTest{

    LogoutPage logoutPage;

    @BeforeClass
    public void setUp(){
        logoutPage = new LogoutPage(driver);
    }

    @Test(groups = "logout")
    public void testLogout(){
        boolean logedOut = logoutPage.logOut();
        assertThat(logedOut, is(equalTo(true)));
    }
}
