package com.epam.mail;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

public class MailBoxPage extends BasePage{
    private static final String TO = "mike_xi@epam.com";
//    private static final String SUBJECT = "Automation test email Subject";
    private static final String BODY = "Automation test email Body";

    private By accoutInfoLocator = By.cssSelector("a[class='gb_D gb_Ua gb_i']");
    private By addEmailButtonLocator = By.cssSelector("div[gh='cm']");
    private By rowsLocator = By.cssSelector("tr[role='row']");
    private By emailRowLocator = By.cssSelector("span.bog");
    private By draftRowCountLocator = By.cssSelector("div.bsU");
    private By draftMenuLocator = By.cssSelector("div[data-tooltip='Drafts']");
    private By sentMenuLocator = By.cssSelector("div[data-tooltip='Sent']");
    private By starredMenuLocator = By.cssSelector("div[data-tooltip='Starred']");
    private By searchTextLocator = By.cssSelector("input[aria-label='Search mail']");
    private By logoutButtonLocator = By.cssSelector("a[class='gb_Mb gb_mg gb_ug gb_7e gb_7c']");

    public MailBoxPage(WebDriver driver){
        super(driver);
    }

    public String getAccountEmail(){
        WebElement account = wait.until(ExpectedConditions.visibilityOfElementLocated(accoutInfoLocator));
        String accountInfos = account.getAttribute("aria-label");
        String[] accountInfo = accountInfos.split(":");
        return accountInfo[1].trim();
    }


    public boolean addDraftEmail(String emailSubject){
        int draftCountBefore = getDraftMailCount();
        MessageDialogPage messageDialogPage = new MessageDialogPage(driver);
        WebElement addEmailButton = driver.findElement(addEmailButtonLocator);
        clickElementByJS(addEmailButton);
        messageDialogPage.setMailContents(TO,emailSubject,BODY);
        messageDialogPage.closeMessageDialog();
        sleepSeconds(2);
        int draftCountAfter = getDraftMailCount();
        clickFirstEmailSubject(draftMenuLocator,emailSubject);
        return draftCountAfter == draftCountBefore + 1;
    }

    public WebElement getSentEmail(String emailSubject){
        return getEmailWithSubject(sentMenuLocator,emailSubject);
    }

    public WebElement searchEmail(String emailSubject){
        WebElement searchText = driver.findElement(searchTextLocator);
        searchText.sendKeys(emailSubject);
        sendKey(Keys.ENTER);
        sleepSeconds(2);
        return getEmailWithSubject(sentMenuLocator,emailSubject);
    }

    public WebElement dragSentMailToStarred(String emailSubject){
        WebElement sentEmail = getEmailWithSubject(sentMenuLocator, emailSubject);
        highlightElementByJS(sentEmail);
        WebElement starredMenu = driver.findElement(starredMenuLocator);
        highlightElementByJS(starredMenu);
        dragAndDrop(sentEmail,starredMenu);
        return getEmailWithSubject(starredMenuLocator,emailSubject);
    }

    public WebElement deleteEmail(String emailSubject){
        WebElement email = getEmailWithSubject(starredMenuLocator, emailSubject);
        highlightElementByJS(email);
        contextClick(email);
        for(int i = 0; i < 6; i ++){
            sendKey(Keys.DOWN);
        }
        sendKey(Keys.ENTER);
        sleepSeconds(2);
        return getEmailWithSubject(starredMenuLocator, emailSubject);
    }

    public boolean logOut(){
        WebElement account = driver.findElement(accoutInfoLocator);
        clickElementByJS(account);
        WebElement logOutButton = driver.findElement(logoutButtonLocator);
        clickElementByJS(logOutButton);
        LoginPage loginPage = new LoginPage(driver);
        WebElement login = loginPage.getInitialView();
        return login.isDisplayed();
    }

    public void tabClick(By menuLocator){
        String url = driver.getCurrentUrl();
        boolean isNotSearch = !(url.contains("search"));
        if(isNotSearch) {
            String menuName = url.split("#")[1].toUpperCase();
            boolean inocrrectMenu = !menuLocator.toString().toUpperCase().contains(menuName);
            if (inocrrectMenu) {
                WebElement menu = driver.findElement(menuLocator);
                highlightElementByJS(menu);
                clickElementByJS(menu);
                while (inocrrectMenu) {
                    sleepSeconds(1);
                    url = driver.getCurrentUrl();
                    menuName = url.split("#")[1].toUpperCase();
                    inocrrectMenu = !menuLocator.toString().toUpperCase().contains(menuName);
                }
            }
        }
    }

    public int getDraftMailCount(){
        WebElement draftsBox = driver.findElement(draftMenuLocator);
        WebElement draftCountLabel = draftsBox.findElement(draftRowCountLocator);
        return Integer.parseInt(draftCountLabel.getText());
    }

    public List<WebElement> getRows(By menuLocator){
        tabClick(menuLocator);
        List<WebElement> rows = driver.findElements(rowsLocator);
        return rows;
    }

    public int getRowsCount(By menuLocator){
        List<WebElement> rows = getRows(menuLocator);
        System.out.println(rows.size());
        return rows.size();
    }

    public WebElement getEmailWithSubject(By menuLocator, String emailSubject){
        WebElement firstEmail = null;
        List<WebElement> rows = getRows(menuLocator);
        int rowCount = getRowsCount(menuLocator);
        for(int i = 0; i < rowCount; i++){
            WebElement row = rows.get(i).findElement(emailRowLocator);
            String rowSubject = row.getText();
            System.out.println(rowSubject);
            if(rowSubject.equals(emailSubject)){
                firstEmail = row;
                highlightElementByJS(firstEmail);
                break;
            }
        }
        return firstEmail;
    }

    public void clickFirstEmailSubject(By menuLocator, String emailSubject){
        WebElement subjectCell = getEmailWithSubject(menuLocator,emailSubject);
        clickElementByJS(subjectCell);
    }

}
