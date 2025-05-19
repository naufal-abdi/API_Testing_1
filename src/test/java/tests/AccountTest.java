package tests;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import models.AccountData;
import models.TokenData;

import org.testng.Assert;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import utils.JsonDataReader;

public class AccountTest {
    public final String BASE_URL = "https://whitesmokehouse.com/webhook";
    public final int SUCCESS_STATUS_CODE = 200;
    private String accountEmail;
    private String accountPassword;
    

    @BeforeSuite(description = "Register")
    public void register() throws Exception {
        System.out.println("Running : Register");
        // get Account Data
        AccountData account = JsonDataReader.getAccountData();
        
        // request body
        String body = "{\r\n" +
                "\"email\" : \"" + account.getEmail() + "\",\r\n " +
                "\"full_name\" :\"" + account.getFullName() + "\", \r\n" +
                "\"password\"  :\"" + account.getPassword() + "\", \r\n" +
                "\"department\" :\"" + account.getDepartment() + "\", \r\n" +
                "\"phone_number\" :\"" + account.getPhoneNumber() + "\" \r\n" +
                "}";

        // Hit api
        Response res = RestAssured
                .given()
                .contentType("application/json")
                .body(body)
                .log()
                .all()
                .when()
                .post(BASE_URL + "/api/register");   
                
        System.out.println(res.getStatusCode());
        
        if (res.statusCode() == SUCCESS_STATUS_CODE) {
            boolean hasStatus = res.jsonPath().getMap("").containsKey("result");

            if (!hasStatus) {
                Assert.assertTrue(res.jsonPath().getString("id") != null);
                Assert.assertEquals(account.getEmail(), res.jsonPath().getString("email"));
                Assert.assertEquals(account.getFullName(), res.jsonPath().getString("full_name"));
                Assert.assertEquals(account.getDepartment(), res.jsonPath().getString("department"));
                Assert.assertEquals(account.getPhoneNumber(), res.jsonPath().getString("phone_number"));
            }

            // set accountEmail and accountPassword value
            accountEmail = account.getEmail();
            accountPassword = account.getPassword();
        }

    }

    @Test(description = "Login")
    public void login() throws Exception {
        System.out.println("Running : Login");
        // check token
        TokenData tokenData = new TokenData();
        if (tokenData.getToken() != null) {
            System.out.println("Test dilewatkan karena sudah memiliki token");
            return;
        }

        String body = "{\n" +
                "\"email\" :\"" + accountEmail + "\", \n " +
                "\"password\" :\"" + accountPassword + "\" \n" + 
                "}";

        // Hit api
        Response res = RestAssured
            .given()
            .contentType(ContentType.JSON)
            .body(body)
            .log()
            .all()
            .when()
            .post(BASE_URL + "/api/login");

        if (res.statusCode() == SUCCESS_STATUS_CODE) {
            String tokenResponse = res.jsonPath().getString("token");

            Assert.assertNotNull(tokenResponse);

            Assert.assertFalse(tokenResponse.isEmpty());

            JsonDataReader.updateToken(tokenResponse);

            System.out.println("Token : " + tokenResponse);
        }

    }

}
