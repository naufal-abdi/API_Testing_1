package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ProductPage {
    WebDriver driver;

    @FindBy(xpath = "//div[@class='header_label']/div[@class='app_logo']")
    private WebElement appLogo;

    @FindBy(xpath = "//a[@class='shopping_cart_link']")
    private WebElement shoppingCartLink;

    @FindBy(id = "react-burger-menu-btn")
    WebElement burgerMenu;

    public ProductPage(WebDriver driver) {
        this.driver = driver;

        PageFactory.initElements(driver, this);
    }

    public WebElement getAppLogo() {
        return appLogo;
    }

    public WebElement getShoppingCartLink() {
        return shoppingCartLink;
    }

    public WebElement getProductByLabel(String productname) {
        String xpath = "//div[@class='inventory_item']//div[@class='inventory_item_label']/a[.='" + productname + "']";

        return driver.findElement(By.xpath(xpath));
    }

    public WebElement getBtnBackToProduct() {
        String xpath = "//div[@class='left_component']/button[text()='Back to products']";

        return driver.findElement(By.xpath(xpath));
    }

    public WebElement getBtnAddToCartInDetail() {
        String xpath = "//div[@id='inventory_item_container']//button[text()='Add to cart']";

        return driver.findElement(By.xpath(xpath));
    }

    public WebElement parentSidebarMenu() {
        String xpath = "//div[@class='bm-menu-wrap']";

        return driver.findElement(By.xpath(xpath));
    }

    public WebElement logoutLink() {
        String xpath = "//a[@id='logout_sidebar_link']";

        return driver.findElement(By.xpath(xpath));
    }
}
