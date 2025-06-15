package utils;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.devtools.idealized.Javascript;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class PageUtils {

    public static void waitForPageLoad(WebDriver driver, int timeoutInSeconds) {
        String jsSyntax = "return document.readyState";

        ExpectedCondition<Boolean> jsLoad = webDriver -> 
            ((JavascriptExecutor) webDriver)
                .executeScript(jsSyntax).toString().equals("complete");

        ExpectedCondition<Boolean> jQueryLoad = webDriver -> {
            try {
                return (Boolean) ((JavascriptExecutor) webDriver)
                        .executeScript("return typeof jQuery != 'undefined' && jQuery.active == 0;");
            } catch (Exception e) {
                // jQuery is not present, consider it loaded
                return true;
            }
        };

        if (timeoutInSeconds <= 0) {
            boolean jsReady = jsLoad.apply(driver);
            boolean jqueryReady = jQueryLoad.apply(driver);

            if (!jsReady || !jqueryReady) {
                System.out.println("Halaman belum selesai dibuat");
            }
        } else {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds));
            wait.until(jsLoad);
            wait.until(jQueryLoad);
        }
    }
    
    public static void waitForElementPresent(WebDriver driver, WebElement elm, int timeoutInSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds));
        wait.until((ExpectedCondition<Boolean>) d -> {
            try {
                elm.isEnabled();
                return true;
            } catch (StaleElementReferenceException e) {
                return false;
            }
        });
    }

    public static void waitForElementVisible(WebDriver driver, WebElement elm, int timeoutInSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds));
        wait.until(ExpectedConditions.visibilityOf(elm));
    }
}
