package cucumber.definitions;

import java.io.File;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.apache.commons.lang3.RandomStringUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.module.jsv.JsonSchemaValidator;
import response_models.LoginResponse;
import response_models.RegisterResponse;
import models.AccountData;
import utils.Config;


public class RegistrationDefinition {
    private String baseURL;
    public static Response res;
    private String randChar;
    private String randNumb;
    LoginResponse loginResponse;
    
    ObjectMapper mapper = new ObjectMapper();
    RegisterResponse registerResponse;
    private AccountData accountData = new AccountData();

    @Given("The base url and data to use in this feature is set")
    public void setBaseUrlFromConfig() throws Exception {
        baseURL = Config.BASE_URL;

        this.randChar = RandomStringUtils.randomAlphanumeric(10);
        this.randNumb = RandomStringUtils.randomNumeric(11);

        File accountDataFile = new File(Config.ACCOUNT_DATA_PATH);

        //AccountData accountData = new AccountData();

        this.accountData.setEmail("emailtest" + this.randChar.toLowerCase() + "@test.com");
        this.accountData.setFullName("Test " + this.randChar);
        this.accountData.setPassword("12345678A$");
        this.accountData.setDepartment("Finance");
        this.accountData.setPhoneNumber("0" + this.randNumb);

        mapper.writerWithDefaultPrettyPrinter().writeValue(accountDataFile, this.accountData);
    }

    @When("Register account with http {string} request to {string} with body:")
    public void sendRegisterRequest(String method, String uriPath, String body) {
        //Set body value
        String bodyValue = body
            .replace("<email>", accountData.getEmail())
            .replace("<full_name>", accountData.getFullName())
            .replace("<password>", accountData.getPassword())
            .replace("<department>", accountData.getDepartment())
            .replace("<phone_number>", accountData.getPhoneNumber());


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

    @Then("The response register endpoint status must be {int}")
    public void getRegisterStatusCode(int statusCode) {
        assert res.getStatusCode() == statusCode : "Terjadi kesalahan dengan status code " + statusCode;
    }

    @And("The response register api schema should be match with schema {string}")
    public void validateRegisterschema(String schemaPath) {
        res.then().assertThat().body(JsonSchemaValidator.matchesJsonSchemaInClasspath(schemaPath));
    }

    @Then("Map Register API Response")
    public void mapperRegisterResponse() throws Exception {
        registerResponse = mapper.readValue(res.body().asString(), RegisterResponse.class);
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
