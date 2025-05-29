package cucumber.definitions;

import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import cucumber.helper.ConfigManager;
import cucumber.helper.HitEndpoint;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import response_models.DepartmentResponse;
import utils.TestContext;

public class DepartmentDefinition {
    TestContext testContext = new TestContext();
    List<DepartmentResponse> departmentResponse ;
    ObjectMapper mapper = new ObjectMapper();
    public static Response res;

    @When("Send request api to endpoint getAllDepartment using {string} method")
    public void sendRequest(String method) throws Exception {
        // Hit Login API
        res = HitEndpoint.createRequest(
            method,
            (String) ConfigManager.getConfigByIndex("ENDPOINT_GET_ALL_DEPARTMENT"), 
            "", 
            true
        );

        testContext.setResponse(res);
    }

    @Then("The response getAllDepartment endpoint status must be {int}")
    public void getRegisterStatusCode(int statusCode) {
        assert testContext.getResponse().getStatusCode() == statusCode : "Terjadi kesalahan dengan status code " + statusCode;
    }

    @And("The response getAllDepartment api schema should be match with schema {string}")
    public void validateschema(String schemaPath) {
        testContext.getResponse().then().assertThat().body(JsonSchemaValidator.matchesJsonSchemaInClasspath(schemaPath));
    }

    @Then("Map getAllDepartment API Response")
    public void mapperResponse() throws Exception {
        departmentResponse = mapper.readValue(res.body().asString(),
                new TypeReference<List<DepartmentResponse>>() {
                });
    }

    @Then("check getAllDepartment api data response")
    public void checkRegisterResponseData() {
        assert departmentResponse.size() > 0 : "Data tidak boleh kosong";
        assert departmentResponse.get(0).getId() != null : "ID tidak boleh Kosong";
        assert departmentResponse.get(0).getDepartment() != null : "Nama Department tidak boleh Kosong";
    } 
}
