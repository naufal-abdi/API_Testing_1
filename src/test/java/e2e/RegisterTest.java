package e2e;

import java.io.File;

import org.apache.commons.lang3.RandomStringUtils;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.restassured.RestAssured;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import models.AccountData;
import response_models.RegisterResponse;

public class RegisterTest {
    private String randChar;
    private String randNumb;
    
    ObjectMapper mapper = new ObjectMapper();
    private AccountData accountData = new AccountData();
    
    public RegisterTest() throws Exception {
        this.randChar = RandomStringUtils.randomAlphanumeric(10);
        this.randNumb = RandomStringUtils.randomNumeric(11);

        File accountDataFile = new File(PrepareDataTest.ACCOUNT_DATA_PATH);

        AccountData accountData = new AccountData();

        accountData.setEmail("emailtest" + this.randChar.toLowerCase() + "@test.com");
        accountData.setFullName("Test " + this.randChar);
        accountData.setPassword("12345678A$");
        accountData.setDepartment("Finance");
        accountData.setPhoneNumber("0" + this.randNumb);

        mapper.writerWithDefaultPrettyPrinter().writeValue(accountDataFile, accountData);
    }

    @BeforeSuite
    public void prepareData() throws Exception {
        
        this.accountData = AccountData.loadDataFromFile(PrepareDataTest.ACCOUNT_DATA_PATH);
        
        System.out.println(this.accountData);
        
    }

    // public static void main(String[] args) throws Exception{
    //     RegisterTest registerTest = new RegisterTest();
    //     registerTest.prepareData();

    //     System.out.println(PrepareDataTest.accountData.getFullName());
    // }

    @Test
    public void register() throws Exception {
        String reqBody = mapper.writeValueAsString(this.accountData);

        System.out.println(reqBody);
        
        // Hit Register API
        Response res = RestAssured
            .given()
                .contentType("application/json")
            .body(reqBody)
                .log()
                .all()
            .when()
                .post(PrepareDataTest.BASE_URL + "/api/register");

        // System.out.println(res.asPrettyString());

        assert res.getStatusCode() == 200 : "Terjadi kesalahan";

        res.then().assertThat().body(JsonSchemaValidator.matchesJsonSchemaInClasspath("response/RegisterResponse.json"));

        RegisterResponse registerResponse = mapper.readValue(res.body().asString(), RegisterResponse.class);

        assert registerResponse != null : "Response tidak boleh kosong";
        assert registerResponse.getId() != null : "ID tidak boleh kosong";
        assert registerResponse.getEmail().equals(this.accountData.getEmail()) : "Email yang diinput tidak sesuai";     
        assert registerResponse.getFullName().equals(this.accountData.getFullName()) : "Nama yang diinput tidak sesuai";
        assert registerResponse.getDepartment().equals(this.accountData.getDepartment()) : "Department yang diinput tidak sesuai";
        assert registerResponse.getPhoneNumber().equals(this.accountData.getPhoneNumber()) : "Phone Number yang diinput tidak sesuai";
    }
}
