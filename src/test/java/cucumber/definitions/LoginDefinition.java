package cucumber.definitions;

import java.util.HashMap;

import org.openqa.selenium.WebDriver;

import cucumber.hooks.Hooks;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import pages.LoginPage;
import pages.ProductPage;
import utils.JsonDataReader;
import utils.PageUtils;

public class LoginDefinition {
    private LoginPage loginPage;
    private ProductPage productPage;
    JsonDataReader jsonDataReader = new JsonDataReader();
    HashMap<String, String> invalidLoginData = new HashMap<>();
    HashMap<String, String> validLoginData = new HashMap<>();

    WebDriver driver;

    @Given("Prepare data for checkout test")
    public void setUpPage() {
        driver = Hooks.driver;

        loginPage = new LoginPage(driver);

        System.out.println("LoginPage init - driver is: " + Hooks.driver);

        invalidLoginData = jsonDataReader.getInvalidLoginData();
        validLoginData = jsonDataReader.getValidLoginData();
        
    }

    @When("Try invalid login with Empty Username and Password")
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

    @Then("Try invalid login with Empty Password")
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

    @Then("Try login with invalid Username and Password")
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

    @Then("Try login with valid Username and Password")
    public void validLogin() throws InterruptedException {
        PageUtils.waitForElementPresent(driver, loginPage.getLoginButton(), 5);

        // Melakukan login dengan username dan password yang valid
        loginPage.login(validLoginData.get("username"), validLoginData.get("password"));

        PageUtils.waitForPageLoad(driver, 5);

        productPage = new ProductPage(driver);

        PageUtils.waitForElementVisible(driver, productPage.getAppLogo(), 2);
    }
}
