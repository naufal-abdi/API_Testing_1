package e2e;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.restassured.RestAssured;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;

import java.io.File;

import org.testng.annotations.Test;
import models.AccountData;
import models.LoginData;
import models.TokenData;
import response_models.LoginResponse;

public class LoginTest {

    ObjectMapper mapper = new ObjectMapper();
    private AccountData accountData = new AccountData();
    private LoginData loginData;
    public LoginTest() throws Exception {
        
        this.accountData = AccountData.loadDataFromFile(PrepareDataTest.ACCOUNT_DATA_PATH);
        this.loginData = new LoginData(this.accountData.getEmail(), this.accountData.getPassword());
    }

    // @Test(dependsOnMethods = "register")
    @Test
    public void login() throws Exception {
        TokenData tokenData = new TokenData();
        File tokenDataFile = new File(PrepareDataTest.TOKEN_DATA_PATH);

        String reqBody = mapper.writeValueAsString(this.loginData);

        System.out.println(reqBody);

        // Hit Login API
        Response res = RestAssured
            .given()
                .contentType("application/json")
            .body(reqBody)
                .log()
                .all()
            .when()
                .post(PrepareDataTest.BASE_URL + "/api/login");

        System.out.println(res.asPrettyString());

        assert res.getStatusCode() == 200 : "Terjadi kesalahan";

        res.then().assertThat().body(JsonSchemaValidator.matchesJsonSchemaInClasspath("response/LoginResponse.json"));

        LoginResponse loginResponse = mapper.readValue(res.body().asString(), LoginResponse.class);

        assert loginResponse != null : "Response tidak boleh kosong";
        assert loginResponse.toString().isEmpty() == false : "Response tidak boleh kosong";
        assert loginResponse.getToken() != null : "Token tidak boleh kosong";

        tokenData.setToken(loginResponse.getToken());

        mapper.writerWithDefaultPrettyPrinter().writeValue(tokenDataFile, tokenData);

        PrepareDataTest.token = loginResponse.getToken();
    }
}
