package cucumber.runners;

import org.testng.annotations.AfterSuite;

import cucumber.helper.GenerateReport;
import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
    features = "classpath:feature/login.feature", // Pastikan file login dijalankan setelah registrasi
    glue = {"cucumber.definitions"},
    plugin = {"pretty", "json:target/LoginReport.json"}
)

public class CucumberRunnerLogin extends AbstractTestNGCucumberTests {
    @AfterSuite
    public void after_suite() {
        GenerateReport.generateReport();
    }
}
