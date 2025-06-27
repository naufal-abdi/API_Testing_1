package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class BuyerPage {
    WebDriver driver;

    @FindBy(id = "first-name")
    private WebElement fieldFirstName;

    @FindBy(id = "last-name")
    private WebElement fieldLastName;

    @FindBy(id = "postal-code")
    private WebElement postalCode;

    @FindBy(id = "continue")
    private WebElement btnContinue;

    public BuyerPage(WebDriver driver) {
        this.driver = driver;

        PageFactory.initElements(driver, this);

    }

    public WebElement getInputFirstName() {
        return fieldFirstName;
    }

    public WebElement getInputLastName() {
        return fieldLastName;        
    }

    public WebElement getInputPostalCode() {
        return postalCode;
    }

    public WebElement getBtnContinue() {
        return btnContinue;
    }
}
