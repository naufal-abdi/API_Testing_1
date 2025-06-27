package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class OverviewPage {
    WebDriver driver;

    @FindBy(id = "finish") 
    private WebElement btnFinish;

    public OverviewPage(WebDriver driver) {
        this.driver = driver;

        PageFactory.initElements(driver, this);
    }

    public WebElement getBtnFinish() {
        return btnFinish;
    }

    public WebElement getProductByLabel(String productname) {
        String xpath = "//div[@class='cart_list']//div[@class='cart_item_label']//div[@class='inventory_item_name'][text()='"+ productname +"']";

        return driver.findElement(By.xpath(xpath));
    }
}
