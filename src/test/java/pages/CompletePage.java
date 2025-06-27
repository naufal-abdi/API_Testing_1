package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class CompletePage {
    WebDriver driver;

    @FindBy(xpath = "//h2[@class='complete-header']")
    private WebElement successMsg;

    @FindBy(id = "back-to-products") 
    private WebElement backToProductPage;

    public CompletePage(WebDriver driver) {
        this.driver = driver;

        PageFactory.initElements(driver, this);
    }

    public WebElement getSuccessMsg() {
        return successMsg;
    }

    public WebElement getBackToProductPage() {
        return backToProductPage;
    }
    
}
