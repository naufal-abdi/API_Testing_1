package cucumber.definitions;

import models.AccountData;
import models.LoginData;
import response_models.LoginResponse;
import utils.Config;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;

public class LoginDefinition {
    private String baseURL;
    ObjectMapper mapper = new ObjectMapper();
    private AccountData accountData = new AccountData();
    private LoginData loginData;
    public static Response res;
    LoginResponse loginResponse;

    @Given("Prepare data for login test")
    public void setBaseUrlFromConfig() throws Exception {
        baseURL = Config.BASE_URL;
        
        this.accountData = AccountData.loadDataFromFile(Config.ACCOUNT_DATA_PATH);
        this.loginData = new LoginData();
        this.loginData.setEmail(this.accountData.getEmail());
        this.loginData.setPassword(this.accountData.getPassword());

    }

    // Login Scenario
    @When("Login account with http {string} request to {string} with body:")
    public void sendLoginRequest(String method, String uriPath, String body) {
        System.out.println(this.loginData.getEmail());
        System.out.println(this.loginData.getPassword());

        String bodyValue = body
            .replace("<email>", this.loginData.getEmail())            
            .replace("<password>", this.loginData.getPassword());

         // Hit Register API
        res = RestAssured
            .given()
                .contentType("application/json")
            .body(bodyValue)
                .log()
                .all()
            .when()
                .post(baseURL + uriPath);
    }

    @Then("The response login endpoint status must be {int}")
    public void getLoginStatusCode(int statusCode) {
        assert res.getStatusCode() == statusCode : "Terjadi kesalahan dengan status code " + statusCode;
    }

    @And("The response login api schema should be match with schema {string}")
    public void validateLoginSchema(String schemaPath) {
        res.then().assertThat().body(JsonSchemaValidator.matchesJsonSchemaInClasspath(schemaPath));

    }
}
