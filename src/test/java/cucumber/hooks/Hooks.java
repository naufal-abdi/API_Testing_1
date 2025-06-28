package cucumber.hooks;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.github.cdimascio.dotenv.Dotenv;
import utils.WebDriverFactory;

import java.io.IOException;
import java.time.Duration;

import org.openqa.selenium.WebDriver;

public class Hooks {
    public static WebDriver driver;
    public static final Dotenv dotenv = Dotenv.configure().load();
    public static final String browser = "chrome";

    @Before
    public void setUp() throws IOException {
        if (driver == null) {
            switch (browser.toLowerCase()) {
                case "chrome" -> driver = WebDriverFactory.getChromeDriver();
                case "firefox" -> driver = WebDriverFactory.getFirefoxDriver();
                case "edge" -> driver = WebDriverFactory.getEdgeDriver();
                default -> driver = WebDriverFactory.getChromeDriver();
            }
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
            driver.manage().window().maximize();
            driver.get(dotenv.get("BASE_URL"));
        }
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }
}
