package cucumber.runners;

import org.testng.annotations.AfterSuite;

import cucumber.helper.GenerateReport;
import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
    features = "classpath:feature/registration.feature",  // Pastikan urutan eksekusi sesuai
    glue = {"cucumber.definitions"},
    plugin = {"pretty", "json:target/RegisterReports.json"}
)

public class CucumberRunnerRegistration extends AbstractTestNGCucumberTests {
    @AfterSuite
    public void after_suite() {
        GenerateReport.generateReport();
    }
}
