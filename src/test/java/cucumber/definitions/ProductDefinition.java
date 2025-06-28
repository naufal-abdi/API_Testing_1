package cucumber.definitions;

import java.util.ArrayList;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import cucumber.hooks.Hooks;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import pages.BuyerPage;
import pages.CartPage;
import pages.CompletePage;
import pages.OverviewPage;
import pages.ProductPage;
import utils.PageUtils;
import utils.JsonDataReader;

public class ProductDefinition {
    WebDriver driver;
    ProductPage productPage;
    CartPage cartPage;
    BuyerPage buyerPage;
    OverviewPage overviewPage;
    CompletePage completePage;
    ArrayList<String> products = new ArrayList<>();
    JsonDataReader jsonDataReader = new JsonDataReader();

    // @Given("Prepare data for product test")
    // public void setUpPage() {
    //     driver = Hooks.driver;

    //     if (driver == null) {
    //         throw new RuntimeException("Driver is null. Make sure it is initialized properly.");
    //     }
    //     
    //     products = jsonDataReader.getProductData();
    // }

    @When("Choose product in catalog and add to cart")
    public void chooseProduct() {
        driver = Hooks.driver;
        productPage = new ProductPage(driver);
        products = jsonDataReader.getProductData();

        System.out.println(">> TEST chooseProduct dimulai");
        boolean titleIsVisible = PageUtils.verifyElementVisible(driver, productPage.getAppLogo(), 0);
        assert titleIsVisible : "App Logo tidak muncul";

        PageUtils.waitForSeconds(2);

        String tempProductName = "";

        WebElement tempProduct;
        WebElement tempProductCartInDetail;
        WebElement btnBackToCatalog;

        boolean productIsVisible = false;

        System.out.println(products.toString());

        
        //menambahkan product ke cart
        for (int i = 0; i < products.size(); i++) {
            
            if (i > 0) {
                titleIsVisible = PageUtils.verifyElementVisible(driver, productPage.getAppLogo(), 0);

                assert titleIsVisible : "App Logo tidak muncul";

                PageUtils.waitForSeconds(2);
            }

            tempProductName = products.get(i);

            tempProduct = productPage.getProductByLabel(tempProductName);

            productIsVisible = PageUtils.verifyElementVisible(driver, tempProduct, 2);

            assert productIsVisible : "Produk tidak terlihat";

            tempProduct.click();

            PageUtils.waitForPageLoad(driver, 2);

            tempProductCartInDetail = productPage.getBtnAddToCartInDetail();

            tempProductCartInDetail.click();

            PageUtils.waitForSeconds(4);

            btnBackToCatalog = productPage.getBtnBackToProduct();

            btnBackToCatalog.click();

            PageUtils.waitForPageLoad(driver, 2);
        }
         
        PageUtils.waitForSeconds(5);
    }


    @Then("Go to cart page and verify product list")
    public void gotoCartPage() {
        products = jsonDataReader.getProductData();

        WebElement btnCart = productPage.getShoppingCartLink();

        btnCart.click();

        PageUtils.waitForPageLoad(driver, 2);

        cartPage = new CartPage(driver);
    }

    @Then("Verify product in list")
    public void verifyProduct() {
        System.out.println("Mulai memverifikasi data");
        for (int i = 0; i < products.size(); i++) {
            cartPage.getProductByLabel(products.get(i));

            PageUtils.waitForSeconds(1);
        }
    }

    @Then("Click checkout button and fill buyer data")
    public void checkoutAndFillBuyerData() {
        System.out.println("Checkout belanja");

        PageUtils.mouseOver(driver, cartPage.getBtnCheckout());

        cartPage.getBtnCheckout().click();

        PageUtils.waitForPageLoad(driver, 5);

        PageUtils.waitForSeconds(2);

        buyerPage = new BuyerPage(driver);

        WebElement inputFirstName = buyerPage.getInputFirstName();

        inputFirstName.sendKeys("demo");

        WebElement inputLastName = buyerPage.getInputLastName();

        inputLastName.sendKeys("demo");

        WebElement inputPostalCode = buyerPage.getInputPostalCode();

        inputPostalCode.sendKeys("127812");

        PageUtils.waitForSeconds(2);

        WebElement btnContinue = buyerPage.getBtnContinue();

        btnContinue.click();

        PageUtils.waitForPageLoad(driver, 2);

        PageUtils.waitForSeconds(2);
    }

    @Then("Verify product in overview page")
    public void overviewCart() {
        overviewPage = new OverviewPage(driver);

        System.out.println("Mulai memverifikasi data");
        for (int i = 0; i < products.size(); i++) {
            overviewPage.getProductByLabel(products.get(i));

            PageUtils.waitForSeconds(1);
        }

        WebElement btnFinish = overviewPage.getBtnFinish();

        btnFinish.click();

        PageUtils.waitForPageLoad(driver, 2);

        PageUtils.waitForSeconds(2);
    }

    @Then("Verify success message and back to catalog page")
    public void verifySuccessMsg() {
        completePage = new CompletePage(driver);

        boolean isSuccess = PageUtils.verifyElementVisible(driver, completePage.getSuccessMsg(), 0);

        assert isSuccess : "Checkout gagal dilakukan";

        WebElement btnBackToCatalogPage = completePage.getBackToProductPage();

        btnBackToCatalogPage.click();

        PageUtils.waitForPageLoad(driver, 2);

        PageUtils.waitForSeconds(5);
    }
}
