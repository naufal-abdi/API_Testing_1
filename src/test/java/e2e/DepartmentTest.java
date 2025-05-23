package e2e;

import java.util.List;

import org.testng.annotations.Test;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.restassured.RestAssured;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import models.TokenData;
import response_models.DepartmentResponse;

public class DepartmentTest {
    ObjectMapper mapper = new ObjectMapper();    
    TokenData tokenData = new TokenData();

    public DepartmentTest() throws Exception {
        
        if (tokenData.getToken() == "" || tokenData.getToken() == null) {
            tokenData.loadTokenFromFile(PrepareDataTest.TOKEN_DATA_PATH);
            
        }
    }

    @Test(dependsOnMethods = "login")
    public void getAllDepartment() throws Exception {
        Response res = RestAssured
            .given()
            .header("Authorization", "Bearer " + tokenData.getToken())
            .log()
            .all()
            .when()
            .get(PrepareDataTest.BASE_URL + "/api/department");

            System.out.println(res.getStatusCode());

        assert res.getStatusCode() == 200 : "Terjadi kesalahan";

        res.then().assertThat().body(JsonSchemaValidator.matchesJsonSchemaInClasspath("response/DepartmentResponse.json"));

        
        List<DepartmentResponse> departmentResponse = mapper.readValue(res.body().asString(),
                new TypeReference<List<DepartmentResponse>>() {
                });

        assert departmentResponse.size() > 0 : "Data kosong";
        assert departmentResponse.get(0).getId() != null : "ID Kosong";
        assert departmentResponse.get(0).getDepartment() != null : "Nama Department Kosong";

    }
}
