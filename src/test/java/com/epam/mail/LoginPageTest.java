package com.epam.mail;

import org.testng.Assert;
import org.testng.annotations.Test;

public class LoginPageTest extends BasicTest{
    private static final String USER_MAIL = "mikeximodule7@gmail.com";
    private static final String USER_PASSWORD = "Welcome2@";

    LoginPage loginPage;

    @Test(groups = "login")
    public void testLoginGmail(){
        loginPage = new LoginPage(driver);
        String str = loginPage.loginGmail(USER_MAIL,USER_PASSWORD);
        Assert.assertEquals(str,"Mike Xi  \n" +
                "(mikeximodule7@gmail.com)");
    }

}
