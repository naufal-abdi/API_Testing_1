package e2e;

import java.io.IOException;
import java.time.Duration;
import java.util.HashMap;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.fasterxml.jackson.databind.JsonNode;

import pages.LoginPage;
import utils.JsonDataReader;
import utils.PageUtils;
import utils.WebDriverFactory;
import io.github.cdimascio.dotenv.Dotenv;

public class LoginTest {

    WebDriver driver;
    LoginPage loginPage;

    String browser = "edge";
    HashMap<String, String> invalidLoginData = new HashMap<>();

    private static final Dotenv dotenv = Dotenv.configure().load();

    @BeforeClass
    public void setUp() {
        //read json file
        try {
            JsonNode jsonData = JsonDataReader.readJsonFile(dotenv.get("TEST_DATA_PATH"));

            String invalidUsername = (String) JsonDataReader.getValueByKey(jsonData, "invalidLoginData.username");

            String invalidPassword = (String) JsonDataReader.getValueByKey(jsonData, "invalidLoginData.password");

            invalidLoginData.put("username", invalidUsername);

            invalidLoginData.put("password", invalidPassword);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (browser.equalsIgnoreCase("chrome")) {
            driver = WebDriverFactory.getChromeDriver();
        } else if (browser.equalsIgnoreCase("firefox")) {
            driver = WebDriverFactory.getFirefoxDriver();
        } else if (browser.equalsIgnoreCase("edge")) {
            driver = WebDriverFactory.getEdgeDriver();
        } else {
            System.out.println("Browser tidak dikenali, akan menggunakan Edge sebagai gantinya");
            driver = WebDriverFactory.getEdgeDriver();
        }

        // driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
        // driver.manage().window().maximize();

        loginPage = new LoginPage(driver);

        driver.get(dotenv.get("BASE_URL"));

        
    }

    @Test
    public void invalidLoginUsernamePassword() {
        PageUtils.waitForElementVisible(driver, loginPage.getLoginButton(), 2);

        loginPage.login(invalidLoginData.get("username"), invalidLoginData.get("password"));

        PageUtils.waitForElementVisible(driver, loginPage.getErrorElement(), 2);

        driver.navigate().refresh();
    }

    @Test(dependsOnMethods = "invalidLoginUsernamePassword")
    public void validLogin() {
        PageUtils.waitForElementVisible(driver, loginPage.getLoginButton(), 2);

        loginPage.login(dotenv.get("USERNAME"), dotenv.get("PASSWORD"));
    }
    
}
