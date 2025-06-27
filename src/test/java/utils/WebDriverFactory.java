package utils;

import io.github.bonigarcia.wdm.WebDriverManager;
import io.github.cdimascio.dotenv.Dotenv;

import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;

public class WebDriverFactory {

    private static final Dotenv dotenv = Dotenv.configure().load();
    // Buat folder temporary sebagai user-data-dir unik
    

    
    public static WebDriver getChromeDriver() throws IOException {
        String chromeDriverPath = dotenv.get("CHROME_DRIVER_PATH");
        System.setProperty("webdriver.chrome.driver", chromeDriverPath);

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--incognito");
        options.addArguments("--start-maximized");

        return new ChromeDriver(options);
    }

    public static WebDriver getFirefoxDriver() {
        WebDriverManager.firefoxdriver().setup();

        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("browserName", "firefox");
        capabilities.setCapability("platform", "ANY");;
        capabilities.setCapability("version", "latest");

        FirefoxOptions options = new FirefoxOptions();
        options.addArguments("-private");
        options.addArguments("-start-fullscreen");
        options.addArguments("--remote-allow-origins=*", "ignore-certificate-errors");
        return new FirefoxDriver(options);
    }

    public static WebDriver getEdgeDriver() throws IOException {
        String edgeDriverPath = dotenv.get("EDGE_DRIVER_PATH");
        System.setProperty("webdriver.edge.driver", edgeDriverPath);

        EdgeOptions options = new EdgeOptions();
        options.addArguments("--incognito");
        options.addArguments("--start-maximized");
        options.addArguments("--remote-allow-origins=*", "ignore-certificate-errors");
        return new EdgeDriver(options);
    }
}
