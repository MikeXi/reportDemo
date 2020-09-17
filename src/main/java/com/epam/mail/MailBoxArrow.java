package com.epam.mail;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import ru.yandex.qatools.htmlelements.annotations.Name;
import ru.yandex.qatools.htmlelements.element.*;
import java.util.List;

@Name("MailBox")
@FindBy(css = "div[style='position: relative;']")
public class MailBoxArrow extends BasePage {

    @Name("Account")
    @FindBy(css = "a[aria-label^='Google Account:']")
    private Link accountButton;

    @Name("Add Email button")
    @FindBy(css = "div[gh='cm']")
    private Button addEmailButton;

    @Name("Email table")
    @FindBy(css = "tr[role='row']")
    private List<WebElement> emailTable;

    @Name("Email item")
    @FindBy(css = "span.bog")
    private List<WebElement> emailItemRow;

    @Name("Draft Email count label")
    @FindBy(css = "div.bsU")
    private List<Button> draftEmailCountLabel;

    @Name("Drafts")
    @FindBy(css = "div[data-tooltip='Drafts']")
    private WebElement draftEmailMenu;

    @Name("Sent")
    @FindBy(css = "div[data-tooltip='Sent']")
    private WebElement sentEmailMenu;

    @Name("Starred")
    @FindBy(css = "div[data-tooltip='Starred']")
    private WebElement starredEmailMenu;

    @Name("Search email input")
    @FindBy(css = "input[aria-label='Search mail']")
    private TextInput searchInput;

    @Name("Logout button")
    @FindBy(css = "a[class='gb_Ib gb_eg gb_mg gb_1e gb_7c']")
    private Button logoutButton;

    public MailBoxArrow(WebDriver driver) {
        super(driver);
    }
    private final Logger logger = LogManager.getRootLogger();

    public String getAccountEmail(){
        boolean accountNotDisplayed = !accountButton.isDisplayed();
        while(accountNotDisplayed){
            sleepSeconds(2);
        }
        String accountInfos = accountButton.getAttribute("aria-label");
        String[] accountInfo = accountInfos.split(":");
        return accountInfo[1].trim();
    }

    public boolean addDraftEmail(String emailSubject,String to, String body) {
        int draftCountBefore = getDraftMailCount();
        logger.info("\nbefore add: "+draftCountBefore);
        addEmailButton.click();
        MessageDialogPage messageDialogPage = new MessageDialogPage(driver);
        messageDialogPage.setMailContents(to,emailSubject,body);
        messageDialogPage.closeMessageDialog();
        sleepSeconds(2);
        int draftCountAfter = getDraftMailCount();
        clickFirstEmailSubject(draftEmailMenu,emailSubject);
        logger.info("\nafter add: "+draftCountAfter);
        return draftCountAfter == draftCountBefore + 1;
    }

    public WebElement getSentEmail(String emailSubject){
        return getEmailWithSubject(sentEmailMenu,emailSubject);
    }

    public WebElement searchEmail(String emailSubject){
        searchInput.sendKeys(emailSubject);
        sendKey(Keys.ENTER);
        sleepSeconds(2);
        return getEmailWithSubject(sentEmailMenu,emailSubject);
    }

    public WebElement dragSentMailToStarred(String emailSubject){
        WebElement sentEmail = getEmailWithSubject(sentEmailMenu, emailSubject);
        highlightElementByJS(starredEmailMenu);
        dragAndDrop(sentEmail,starredEmailMenu);
        return getEmailWithSubject(sentEmailMenu,emailSubject);
    }

    public WebElement deleteEmail(String emailSubject){
        WebElement email = getEmailWithSubject(starredEmailMenu, emailSubject);
        highlightElementByJS(email);
        contextClick(email);
        for(int i = 0; i < 6; i ++){
            sendKey(Keys.DOWN);
        }
        sendKey(Keys.ENTER);
        sleepSeconds(2);
        return getEmailWithSubject(starredEmailMenu, emailSubject);
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

    public void tabClick(WebElement menu){
        String url = driver.getCurrentUrl();
        boolean isNotSearch = !(url.contains("search"));
        if(isNotSearch) {
            String menuName = url.split("#")[1].toUpperCase();
            boolean inocrrectMenu = !menu.toString().toUpperCase().contains(menuName);
            if (inocrrectMenu) {
                menu.click();
                while (inocrrectMenu) {
                    sleepSeconds(2);
                    url = driver.getCurrentUrl();
                    menuName = url.split("#")[1].toUpperCase();
                    inocrrectMenu = !menu.toString().toUpperCase().contains(menuName);
                }
            }
        }
    }

    public int getDraftMailCount(){
        return Integer.parseInt(draftEmailCountLabel.get(1).getText());
    }

    public List<WebElement> getRows(WebElement menu){
        tabClick(menu);
        return emailItemRow;
    }

    public int getRowsCount(WebElement menu){
        List<WebElement> rows = getRows(menu);
        return rows.size();
    }

    public WebElement getEmailWithSubject(WebElement menu, String emailSubject){
        WebElement firstEmail = null;
        List<WebElement> rows = getRows(menu);
        int rowCount = getRowsCount(menu);
        for(int i = 0; i < rowCount; i++){
            WebElement row = rows.get(i);
            String rowSubject = row.getText();
            if(rowSubject.equals(emailSubject)){
                firstEmail = row;
                highlightElementByJS(firstEmail);
                break;
            }
        }
        return firstEmail;
    }

    public void clickFirstEmailSubject(WebElement menu, String emailSubject){
        WebElement subjectCell = getEmailWithSubject(menu,emailSubject);
        clickElementByJS(subjectCell);
    }

}
