package e2e;

import org.openqa.selenium.WebElement;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import pages.BuyerPage;
import pages.CartPage;
import pages.CompletePage;
import pages.OverviewPage;
import pages.ProductPage;
import utils.PageUtils;


public class ProductTest extends BaseTest {
    
    ProductPage productPage;
    CartPage cartPage;
    BuyerPage buyerPage;
    OverviewPage overviewPage;
    CompletePage completePage;

    @BeforeClass
    public void setUpPage() {
        if (driver == null) {
            throw new RuntimeException("Driver is null. Make sure it is initialized properly.");
        }
        productPage = new ProductPage(driver);
    }

    @Test
    public void chooseProduct() {
        products = jsonDataReader.getProductData();

        System.out.println(">> TEST chooseProduct dimulai");

        System.out.println("Bukan super " + products.toString());
        System.out.println("Super " + super.products.toString());

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

            // PageUtils.verifyElementVisible(driver, btnBackToCatalog, 2);            

            // PageUtils.verifyElementVisible(driver, tempProductCartInDetail, 2);

            tempProductCartInDetail.click();

            PageUtils.waitForSeconds(4);

            btnBackToCatalog = productPage.getBtnBackToProduct();

            btnBackToCatalog.click();

            PageUtils.waitForPageLoad(driver, 2);
        }
         
        PageUtils.waitForSeconds(5);
    }

    @Test(dependsOnMethods = "chooseProduct")
    public void gotoCartPage() {
        products = jsonDataReader.getProductData();

        WebElement btnCart = productPage.getShoppingCartLink();

        btnCart.click();

        PageUtils.waitForPageLoad(driver, 2);

        cartPage = new CartPage(driver);
    }

    @Test(dependsOnMethods = "gotoCartPage")
    public void verifyProduct() {
        System.out.println("Mulai memverifikasi data");
        for (int i = 0; i < products.size(); i++) {
            cartPage.getProductByLabel(products.get(i));

            PageUtils.waitForSeconds(1);
        }
    }

    @Test(dependsOnMethods = "verifyProduct")
    public void checkoutAndFillBuyerData() {
        System.out.println("Checkout belanja");;

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

    @Test(dependsOnMethods = "checkoutAndFillBuyerData")
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

    @Test(dependsOnMethods = "overviewCart")
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
