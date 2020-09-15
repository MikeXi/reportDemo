package com.epam.mail;

import com.epam.model.User;
import com.epam.service.UserCreator;
import com.epam.util.TestListener;
import org.testng.annotations.Listeners;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

@Listeners({TestListener.class})
public class LoginPageTest extends BasicTest{

    LoginPage loginPage;

    @Test(groups = "login")
    public void testLoginGmail(){
        loginPage = new LoginPage(driver);
        User user = UserCreator.withCredentialsFromProperty();
        String str = loginPage.loginGmail(user);
        assertThat(str, is(equalTo("Mike Xi  \n" +
                "(mikeximodule7@gmail.com)")));
    }

}
