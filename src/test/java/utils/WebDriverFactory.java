package utils;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;

public class WebDriverFactory {
    public static WebDriver getChromeDriver() {
        WebDriverManager.chromedriver().setup();

        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("browserName", "chrome");
        capabilities.setCapability("platform", "ANY");;
        capabilities.setCapability("version", "latest");

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

        return new FirefoxDriver(options);
    }

    public static WebDriver getEdgeDriver() {
        WebDriverManager.edgedriver().setup();

        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("browserName", "MicrosoftEdge");
        capabilities.setCapability("platform", "ANY");;
        capabilities.setCapability("version", "latest");

        EdgeOptions options = new EdgeOptions();
        options.addArguments("--incognito");
        options.addArguments("--start-maximized");
        return new EdgeDriver(options);
    }
}
