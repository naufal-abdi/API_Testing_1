package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import utils.PageUtils;

public class LoginPage {
    WebDriver driver;

    @FindBy(id = "user-name")
    private WebElement fieldUsername;

    @FindBy(id = "password")
    private WebElement fieldPassword;

    @FindBy(id = "login-button")
    private WebElement btnLogin;

    private final By errorMessageBy = By.xpath("//div[@class='error-message-container error']/h3[@data-test='error']");

    private final By btnCloseErrorMessageBy = By.xpath("//div[@class='error-message-container error']/h3[@data-test='error']/button[@class='error-button']");

    public LoginPage(WebDriver driver) {
        this.driver = driver;

        PageFactory.initElements(driver, this);
    }

    public void login(String username, String password) {
        PageUtils.waitForElementVisible(this.driver, btnLogin, 2);

        fieldUsername.sendKeys(username);
        fieldPassword.sendKeys(password);        

        btnLogin.click();

        PageUtils.waitForPageLoad(driver, 2);
    }

    public WebElement getLoginButton() {
        return btnLogin;
    }

    public By getErrorMessageBy() {
        return errorMessageBy;
    }

    public By getBtnCloseErrorMessageBy() {
        return btnCloseErrorMessageBy;
    }

    public WebElement getErrorElement() {
        return driver.findElement(getErrorMessageBy());
    }

    public boolean isErrorVisible() {
        WebElement errMsg = driver.findElement(errorMessageBy);

        try {
            return errMsg.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public String getErrorMessageContent() {
        WebElement errMsg = driver.findElement(errorMessageBy);

        try {
            return errMsg.getText();
        } catch (Exception e) {
            return "";
        }
    }
}
