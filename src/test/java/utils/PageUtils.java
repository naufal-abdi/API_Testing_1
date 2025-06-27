package utils;

import java.time.Duration;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class PageUtils {

    public static void waitForPageLoad(WebDriver driver, int timeoutInSeconds) {
        new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds)).until(
                webDriver -> ((JavascriptExecutor) webDriver)
                        .executeScript("return document.readyState").equals("complete"));
    }

    public static void waitForElementPresent(WebDriver driver, WebElement elm, int timeoutInSeconds) {
        /*
         * WebDriverWait wait = new WebDriverWait(driver,
         * Duration.ofSeconds(timeoutInSeconds));
         * wait.until((ExpectedCondition<Boolean>) d -> {
         * try {
         * elm.isEnabled();
         * return true;
         * } catch (StaleElementReferenceException e) {
         * return false;
         * }
         * });
         */

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds));

        // Tunggu sampai elemen dapat diklik
        wait.until(ExpectedConditions.elementToBeClickable(elm));
    }

    public static WebElement waitForElementVisible(WebDriver driver, WebElement elm, int timeoutInSeconds) {
        // WebDriverWait wait = new WebDriverWait(driver,
        // Duration.ofSeconds(timeoutInSeconds));
        // wait.until(ExpectedConditions.visibilityOf(elm));

        /*
         * Wait<WebDriver> wait = new FluentWait<>(driver)
         * .withTimeout(Duration.ofSeconds(10)) // Timeout maksimal
         * .pollingEvery(Duration.ofMillis(500)) // Interval pengecekan setiap 500 ms
         * .ignoring(NoSuchElementException.class) // Mengabaikan pengecualian jika
         * elemen tidak ditemukan
         * .ignoring(TimeoutException.class); // Mengabaikan TimeoutException jika
         * halaman lambat
         * 
         * // Fungsi untuk mengecek apakah elemen terlihat (visible)
         * return wait.until(new Function<WebDriver, WebElement>() {
         * public WebElement apply(WebDriver driver) {
         * // Mengecek jika elemen tersebut visible
         * if (elm.isDisplayed()) {
         * return elm;
         * }
         * return null; // Jika tidak ditemukan atau tidak terlihat, return null
         * }
         * });
         */

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds));

        // Tunggu hingga elemen terlihat
        return wait.until(ExpectedConditions.visibilityOf(elm));
    }

    public static boolean verifyElementVisible(WebDriver driver, WebElement element, int timeoutInSeconds) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds));
            wait.until(ExpectedConditions.visibilityOf(element));
            return true;
        } catch (TimeoutException e) {
            System.out.println("Element not visible: " + element);
            return false;
        } catch (StaleElementReferenceException e) {
            System.out.println("Element is stale: " + element);
            return false;
        }
    }

    public static boolean verifyElementClickable(WebDriver driver, WebElement element, int timeoutInSeconds) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds));
            wait.until(ExpectedConditions.elementToBeClickable(element));
            return true;
        } catch (TimeoutException e) {
            System.out.println("Element not clickable: " + element);
            return false;
        } catch (StaleElementReferenceException e) {
            System.out.println("Element is stale: " + element);
            return false;
        }
    }

    public static void waitForSeconds(int seconds) {
        try {
            Thread.sleep(seconds * 1000L);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("Thread interrupted while waiting.");
        }
    }

    public static void mouseOver(WebDriver driver, WebElement element) {
        Actions actions = new Actions(driver);
        actions.moveToElement(element).perform();
    }
}
