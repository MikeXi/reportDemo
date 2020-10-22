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

    @Name("Message From")
    @FindBy(css = "div[role='dialog']")
    private WebElement messageDialog;

    public MailBoxArrow(WebDriver driver) {
        super(driver);
    }
    private final Logger logger = LogManager.getRootLogger();

    public String getAccountEmail(){
        String accountInfos = objectIsDisplayed(accountButton).getAttribute("aria-label");
        String[] accountInfo = accountInfos.split(":");
        return accountInfo[1].trim();
    }

    public boolean addDraftEmail(String emailSubject,String to, String body) {
        int draftCountBefore = getDraftMailCount();
        logger.info("Before add, draft email count is: "+draftCountBefore);
        objectIsDisplayed(addEmailButton).click();
        MessageDialogPage messageDialogPage = new MessageDialogPage(driver);
        messageDialogPage.setMailContents(to,emailSubject,body);
        messageDialogPage.closeMessageDialog();
        sleepSeconds(2);
        int draftCountAfter = getDraftMailCount();
        clickFirstEmailSubject(draftEmailMenu,emailSubject);
        logger.info("After add, draft email count is: "+draftCountAfter);
        return draftCountAfter == draftCountBefore + 1;
    }

    public WebElement getSentEmail(String emailSubject){
        return getEmailWithSubject(sentEmailMenu,emailSubject);
    }

    public WebElement searchEmail(String emailSubject){
        objectIsDisplayed(searchInput).sendKeys(emailSubject);
        sendKey(Keys.ENTER);
        sleepSeconds(2);
        return getEmailWithSubject(sentEmailMenu,emailSubject);
    }

    public WebElement dragSentMailToStarred(String emailSubject){
        WebElement sentEmail = getEmailWithSubject(sentEmailMenu, emailSubject);
        dragAndDrop(sentEmail,starredEmailMenu);
        return getEmailWithSubject(sentEmailMenu,emailSubject);
    }

    public WebElement deleteEmail(String emailSubject){
        WebElement email = getEmailWithSubject(starredEmailMenu, emailSubject);
        contextClick(email);
        for(int i = 0; i < 6; i ++){
            sendKey(Keys.DOWN);
        }
        sendKey(Keys.ENTER);
        sleepSeconds(2);
        return getEmailWithSubject(starredEmailMenu, emailSubject);
    }

    public void tabClick(WebElement menu){
        String url = driver.getCurrentUrl();
        boolean isNotSearch = !(url.contains("search"));
        if(isNotSearch) {
            String menuName = url.split("#")[1].toUpperCase();
            boolean incorrectMenu = !menu.toString().toUpperCase().contains(menuName);
            if (incorrectMenu) {
                logger.warn("You are in the incorrect menu.");
                objectIsDisplayed(menu).click();
                while (incorrectMenu) {
                    sleepSeconds(2);
                    url = driver.getCurrentUrl();
                    menuName = url.split("#")[1].toUpperCase();
                    incorrectMenu = !menu.toString().toUpperCase().contains(menuName);
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
        logger.debug("Email count is: " + rowCount);
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
