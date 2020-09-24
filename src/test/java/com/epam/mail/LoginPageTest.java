package com.epam.mail;

import com.epam.mail.iservice.LoginIService;
import com.epam.util.TestListener;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

@Listeners({TestListener.class})
public class LoginPageTest extends BasicTest{

    LoginIService loginIService;

    @Test(groups = "login")
    public void testLoginGmail(){
        loginIService = new LoginPage(driver);
        String str = loginIService.loginGmail();
        assertThat(str, is(equalTo("Mike Xi  \n" +
                "(mikeximodule7@gmail.com)")));
    }
}
