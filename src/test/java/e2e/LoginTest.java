package e2e;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import pages.LoginPage;
import pages.ProductPage;
import utils.PageUtils;


public class LoginTest extends BaseTest {

    private LoginPage loginPage;
    private ProductPage productPage;

    @BeforeClass
    public void setUpPage() {
        loginPage = new LoginPage(driver);
    }

    @Test
    public void invalidLoginEmptyUsernamePassword() throws InterruptedException {
        PageUtils.waitForElementPresent(driver, loginPage.getLoginButton(), 5);

        // Melakukan login dengan username dan password yang salah
        loginPage.login("", "");

        // Tunggu error message muncul
        PageUtils.waitForElementVisible(driver, loginPage.getErrorElement(), 5);

        // Verifikasi jika error muncul
        assert loginPage.isErrorVisible() : "Error message tidak muncul";

        PageUtils.waitForSeconds(3);

        // Refresh halaman setelah pengecekan
        driver.navigate().refresh();

        PageUtils.waitForPageLoad(driver, 5);
    }

    @Test(dependsOnMethods = "invalidLoginEmptyUsernamePassword")
    public void invalidLoginEmptyPassword() throws InterruptedException {
        PageUtils.waitForElementPresent(driver, loginPage.getLoginButton(), 5);

        // Melakukan login dengan username dan password yang salah
        loginPage.login(invalidLoginData.get("username"), "");

        // Tunggu error message muncul
        PageUtils.waitForElementVisible(driver, loginPage.getErrorElement(), 5);

        // Verifikasi jika error muncul
        assert loginPage.isErrorVisible() : "Error message tidak muncul";

        PageUtils.waitForSeconds(3);

        // Refresh halaman setelah pengecekan
        driver.navigate().refresh();

        PageUtils.waitForPageLoad(driver, 5);
    }

    @Test(dependsOnMethods = "invalidLoginEmptyPassword")
    public void invalidLoginUsernamePassword() throws InterruptedException {
        PageUtils.waitForElementPresent(driver, loginPage.getLoginButton(), 5);

        // Melakukan login dengan username dan password yang salah
        loginPage.login(invalidLoginData.get("username"), invalidLoginData.get("password"));

        // Tunggu error message muncul
        PageUtils.waitForElementVisible(driver, loginPage.getErrorElement(), 5);

        // Verifikasi jika error muncul
        assert loginPage.isErrorVisible() : "Error message tidak muncul";

        PageUtils.waitForSeconds(3);

        // Refresh halaman setelah pengecekan
        driver.navigate().refresh();

        PageUtils.waitForPageLoad(driver, 5);
    }

    @Test(dependsOnMethods = "invalidLoginUsernamePassword")
    public void validLogin() throws InterruptedException {
        PageUtils.waitForElementPresent(driver, loginPage.getLoginButton(), 5);

        // Melakukan login dengan username dan password yang valid
        loginPage.login(validLoginData.get("username"), validLoginData.get("password"));

        PageUtils.waitForPageLoad(driver, 5);

        productPage = new ProductPage(driver);

        PageUtils.waitForElementVisible(driver, productPage.getAppLogo(), 2);
    }

}
