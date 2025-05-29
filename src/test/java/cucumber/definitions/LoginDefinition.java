package cucumber.definitions;

import models.AccountData;
import models.LoginData;
import models.TokenData;
import response_models.LoginResponse;
import utils.TestContext;

import java.io.File;

import com.fasterxml.jackson.databind.ObjectMapper;

import cucumber.helper.ConfigManager;
import cucumber.helper.HitEndpoint;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;

public class LoginDefinition {
    ObjectMapper mapper = new ObjectMapper();
    private AccountData accountData = new AccountData();
    private LoginData loginData;
    public static Response res;
    LoginResponse loginResponse;
    private final TestContext testContext = new TestContext();

    @Given("Prepare data for login test")
    public void prepareData() throws Exception {
        this.accountData = AccountData.loadDataFromFile((String) ConfigManager.getConfigByIndex("ACCOUNT_DATA_PATH"));
        this.loginData = new LoginData();
        this.loginData.setEmail(this.accountData.getEmail());
        this.loginData.setPassword(this.accountData.getPassword());
    }

    // Login Scenario
    @When("Send request api to endpoint login using {string} method with body:")
    public void sendLoginRequest(String method, String body) throws Exception {
        System.out.println(this.loginData.getEmail());
        System.out.println(this.loginData.getPassword());

        String bodyValue = body
            .replace("<email>", this.loginData.getEmail())            
            .replace("<password>", this.loginData.getPassword());

        // Hit Login API
        res = HitEndpoint.createRequest(
            method,
            (String) ConfigManager.getConfigByIndex("ENDPOINT_LOGIN"), 
            bodyValue, 
            false
        );

        testContext.setResponse(res);
    }

    @Then("The response login endpoint status must be {int}")
    public void getLoginStatusCode(int statusCode) {
        assert testContext.getResponse().getStatusCode() == statusCode : "Terjadi kesalahan dengan status code " + statusCode;
    }

    @And("The response login api schema should be match with schema {string}")
    public void validateLoginSchema(String schemaPath) {
        testContext.getResponse().then().assertThat().body(JsonSchemaValidator.matchesJsonSchemaInClasspath(schemaPath));

    }

    @Then("Map login API Response")
    public void mapperLoginResponse() throws Exception {
        loginResponse = mapper.readValue(testContext.getResponse().body().asString(), LoginResponse.class);
    }

    @Then("check login api data response")
    public void checkLoginResponseData() {
        assert loginResponse.getToken() != "Token tidak boleh kosong";
    } 

    @And("Save the token from the login api response to local storage") 
    public void saveTokenToLocalStorage() throws Exception {
        File tokenDataFile = new File((String) ConfigManager.getConfigByIndex("TOKEN_DATA_PATH"));
        TokenData tokenData = new TokenData();
        tokenData.setToken(loginResponse.getToken());
        mapper.writerWithDefaultPrettyPrinter().writeValue(tokenDataFile, tokenData);
    }
    
}
