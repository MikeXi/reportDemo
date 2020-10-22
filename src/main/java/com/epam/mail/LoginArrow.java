package com.epam.mail;

import com.epam.mail.iservice.LoginIService;
import com.epam.model.User;
import com.epam.service.CredentialsUserCreatorDecorator;
import com.epam.service.EmailUserCreator;
import com.epam.service.UserCreatorDecorator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;
import ru.yandex.qatools.htmlelements.annotations.Name;
import ru.yandex.qatools.htmlelements.element.Button;
import ru.yandex.qatools.htmlelements.element.TextInput;

@Name("Login From")
@FindBy(id = "initialView")
public class LoginArrow extends BasePage implements LoginIService {
    private final Logger logger = LogManager.getRootLogger();

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
    UserCreatorDecorator userCreatorDecorator;
    User user;

    public LoginArrow(WebDriver driver) {
        super(driver);
        userCreatorDecorator = new CredentialsUserCreatorDecorator(new EmailUserCreator());
        user = userCreatorDecorator.createUser();
    }

    @Override
    public String loginGmail(){
        MailBoxPage mailBoxPage = new MailBoxPage(driver);
        driver.get(URL);
        objectIsDisplayed(emailInput).sendKeys(user.getUsername());
        objectIsDisplayed(nextButton).click();
        objectIsDisplayed(passwordInput).sendKeys(user.getPassword());
        try {
            objectIsDisplayed(nextButton).click();
        }catch (StaleElementReferenceException e){
            objectIsDisplayed(nextButton).click();
        }
        return mailBoxPage.getAccountEmail();
    }
}
