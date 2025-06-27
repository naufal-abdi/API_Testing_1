package e2e;

import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterSuite;

import org.testng.annotations.BeforeSuite;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.github.cdimascio.dotenv.Dotenv;
import utils.JsonDataReader;
import utils.WebDriverFactory;

public class BaseTest {
    static WebDriver driver;
    
    String browser = "chrome";
    ObjectMapper objectMapper = new ObjectMapper();
    HashMap<String, String> invalidLoginData = new HashMap<>();
    HashMap<String, String> validLoginData = new HashMap<>();
    ArrayList<String> products = new ArrayList<>();
    String jsonStringOutput = "";
    private static final Dotenv dotenv = Dotenv.configure().load();
    
    JsonDataReader jsonDataReader = new JsonDataReader();

    @BeforeSuite(alwaysRun = true)
    public void setUp() throws Exception {
        invalidLoginData = jsonDataReader.getInvalidLoginData();
        validLoginData = jsonDataReader.getValidLoginData();
        

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

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        driver.manage().window().maximize();

        driver.get(dotenv.get("BASE_URL"));

        
    }

    @AfterSuite(alwaysRun = true)
    public void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
