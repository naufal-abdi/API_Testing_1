package cucumber.definitions;

import java.io.File;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.apache.commons.lang3.RandomStringUtils;
import com.fasterxml.jackson.databind.ObjectMapper;

import cucumber.helper.ConfigManager;
import cucumber.helper.HitEndpoint;
import io.restassured.module.jsv.JsonSchemaValidator;
import response_models.RegisterResponse;
import models.AccountData;
import utils.TestContext;


public class RegistrationDefinition {
    public static Response res;
    private String randChar;
    private String randNumb;
    private final TestContext testContext = new TestContext();
    
    ObjectMapper mapper = new ObjectMapper();
    RegisterResponse registerResponse;
    private AccountData accountData = new AccountData();

    @Given("The base url and data to use in this feature is set")
    public void prepareData() throws Exception {
        this.randChar = RandomStringUtils.randomAlphanumeric(10);
        this.randNumb = RandomStringUtils.randomNumeric(11);

        File accountDataFile = new File((String) ConfigManager.getConfigByIndex("ACCOUNT_DATA_PATH"));

        this.accountData.setEmail("emailtest" + this.randChar.toLowerCase() + "@test.com");
        this.accountData.setFullName("Test " + this.randChar);
        this.accountData.setPassword("12345678A$");
        this.accountData.setDepartment("Finance");
        this.accountData.setPhoneNumber("0" + this.randNumb);

        mapper.writerWithDefaultPrettyPrinter().writeValue(accountDataFile, this.accountData);
    }

    @When("Send request api to endpoint register using {string} method with body:")
    public void sendRegisterRequest(String method, String body) throws Exception {
        //Set body value
        String bodyValue = body
            .replace("<email>", accountData.getEmail())
            .replace("<full_name>", accountData.getFullName())
            .replace("<password>", accountData.getPassword())
            .replace("<department>", accountData.getDepartment())
            .replace("<phone_number>", accountData.getPhoneNumber());


         // Hit Register API
        res = HitEndpoint.createRequest(
            method,
            (String) ConfigManager.getConfigByIndex("ENDPOINT_REGISTER"),
            bodyValue,
            false
        );

        testContext.setResponse(res);
    }

    @Then("The response register endpoint status must be {int}")
    public void getRegisterStatusCode(int statusCode) {
        assert testContext.getResponse().getStatusCode() == statusCode : "Terjadi kesalahan dengan status code " + statusCode;
    }

    @And("The response register api schema should be match with schema {string}")
    public void validateRegisterschema(String schemaPath) {
        testContext.getResponse().then().assertThat().body(JsonSchemaValidator.matchesJsonSchemaInClasspath(schemaPath));
    }

    @Then("Map Register API Response")
    public void mapperRegisterResponse() throws Exception {
        registerResponse = mapper.readValue(testContext.getResponse().body().asString(), RegisterResponse.class);
    }

    @Then("check register api data response")
    public void checkRegisterResponseData() {
        assert registerResponse.getId() != null : "ID tidak boleh kosong";
        assert registerResponse.getEmail().equals(this.accountData.getEmail()) : "Email yang diinput tidak sesuai";     
        assert registerResponse.getFullName().equals(this.accountData.getFullName()) : "Nama yang diinput tidak sesuai";
        assert registerResponse.getDepartment().equals(this.accountData.getDepartment()) : "Department yang diinput tidak sesuai";
        assert registerResponse.getPhoneNumber().equals(this.accountData.getPhoneNumber()) : "Phone Number yang diinput tidak sesuai";

    } 

    
}
