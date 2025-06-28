package cucumber.runners;

import org.testng.annotations.AfterSuite;

import cucumber.helper.GenerateReport;
import cucumber.hooks.Hooks;
import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
    features = "classpath:feature/checkout.feature",
    glue = {"cucumber.definitions", "cucumber.hooks"},
    plugin = {"pretty", "json:target/CheckoutProductReport.json"}
)

public class RunnerCheckoutE2E extends AbstractTestNGCucumberTests {

    @AfterSuite
    public void afterSuite() {
        GenerateReport.generateReport("CheckoutProductReport.json", "Checkout Product Test");

        if (Hooks.driver != null) {
            Hooks.driver.quit();
        }
    }
}
