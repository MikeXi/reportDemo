package com.epam.mail;

import com.epam.model.User;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;
import ru.yandex.qatools.htmlelements.annotations.Name;
import ru.yandex.qatools.htmlelements.element.Button;
import ru.yandex.qatools.htmlelements.element.TextInput;

@Name("Login From")
@FindBy(id = "initialView")
public class LoginArrow extends BasePage {

    @Name("Email address input")
    @FindBy(id = "identifierId")
    private TextInput emailInput;

    @Name("Password input")
    @FindBy(name = "password")
    private TextInput passwordInput;

    @Name("Next button")
    @FindBy(css = "div.VfPpkd-RLmnJb")
    private Button nextButton;

    private static final String URL = "https://gmail.com/";

    public LoginArrow(WebDriver driver) {
        super(driver);
    }

    public String loginGmail(User user){
        MailBoxPage mailBoxPage = new MailBoxPage(driver);
        driver.get(URL);
        emailInput.sendKeys(user.getUsername());
        nextButton.click();
        sleepSeconds(2);
        passwordInput.sendKeys(user.getPassword());
        try {
            nextButton.click();
        }catch (StaleElementReferenceException e){
            nextButton.click();
        }
        return mailBoxPage.getAccountEmail();
    }

}
