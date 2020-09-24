package com.epam.mail;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import ru.yandex.qatools.htmlelements.annotations.Name;
import ru.yandex.qatools.htmlelements.element.*;

@Name("Logout")
@FindBy(css = "div[style='position: relative;']")
public class LogoutArrow extends BasePage {

    @Name("Account")
    @FindBy(css = "a[aria-label^='Google Account:']")
    private Link accountButton;

    @Name("Logout button")
    @FindBy(css = "a[class='gb_Ib gb_eg gb_mg gb_1e gb_7c']")
    private Button logoutButton;

    public LogoutArrow(WebDriver driver) {
        super(driver);
    }

    public boolean logOut(){
        clickElementByJS(accountButton);
        sleepSeconds(1);
        clickElementByJS(logoutButton);
        sleepSeconds(3);
        String url = driver.getCurrentUrl();
        boolean isLoggedOut = url.contains("ServiceLogin");
        return isLoggedOut;
    }

}
