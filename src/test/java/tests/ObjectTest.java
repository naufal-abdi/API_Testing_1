package tests;

import org.testng.Assert;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import io.restassured.RestAssured;

import io.restassured.response.Response;
import models.ObjectData;
import models.TokenData;
import utils.JsonDataReader;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class ObjectTest {
    public final String BASE_URL = "https://whitesmokehouse.com/webhook";
    public final int EXPECT_STATUS_CODE = 200;
    public int dynamicObjectID;
    private ObjectData objData;
    private String token;
    private final String tokenFilePath = "src/test/resources/tokenData.json";

    public void getRandomIdFromResponse(List<Map<String, Object>> responseData) {
        List<Integer> allIds = new ArrayList<>();
        Integer idValue = 0;
        for (int i = 0; i < responseData.size(); i++) {
            idValue = (Integer) responseData.get(0).get("id");
            allIds.add(idValue);
        }

        Random rand = new Random();
        int randIdx = rand.nextInt(allIds.size());
        int randVal = (int) allIds.get(randIdx);

        dynamicObjectID = randVal;
    }

    // public static void main(String[] args) throws Exception {
    //     ObjectTest objTest = new ObjectTest();
    //     objTest.setTokenData();

    //     System.out.println(objTest.token);
    // }

    @BeforeTest
    public void setTokenData () throws Exception {
        TokenData tokenData = new TokenData();
        if (token == null) {
            if (tokenData.getToken() != null) {
                token = tokenData.getToken();
            } else {
                tokenData.loadTokenFromFile(tokenFilePath);
                token = tokenData.getToken();
            }
        }
    }

    @Test(description = "Add Object")
    public void addObject() throws Exception {
        System.out.println("Running : Add Object");
        // get Account Data
        objData = JsonDataReader.getObjectData();

        String body = "{\r\n" +
            "\"name\" :\"" + objData.getName() + "\",\r\n " +
            "\"data\" : {\r\n" +
            "   \"year\" : \""+ objData.getData().get("year") + "\", \r\n" +
            "   \"price\" : \""+ objData.getData().get("price") + "\", \r\n" +
            "   \"cpu_model\" :\""+ objData.getData().get("cpu_model") + "\", \r\n" +
            "   \"hard_disk_size\" :\""+ objData.getData().get("hard_disk_size") + "\", \r\n" +
            "   \"capacity\" :\""+ objData.getData().get("capacity") + "\", \r\n" +
            "   \"screen_size\" :\""+ objData.getData().get("screen_size") + "\", \r\n" +
            "   \"color\" :\""+ objData.getData().get("color") + "\" \r\n" +
            "   } " +
            "}";

        // Hit api
        Response res = RestAssured
                .given()
                .header("Authorization", "Bearer " + token)
                .contentType("application/json")
                .body(body)
                .log()
                .all()
                .when()
                .post(BASE_URL + "/api/objects");

        System.out.println(res.getStatusCode());
        System.out.println(res.getBody().asPrettyString());

        if (res.statusCode() == EXPECT_STATUS_CODE) {
            Assert.assertNotNull(res.jsonPath().getString("[0].id"));
            Assert.assertEquals(objData.getName(), res.jsonPath().getString("[0].name"));

            dynamicObjectID = Integer.valueOf(res.jsonPath().getString("[0].id"));

            System.out.println("ID " + dynamicObjectID);
        }
    }

    @Test(dependsOnMethods = {"addObject"}, description = "Update Object")
    public void updateObject() {
        System.out.println("Running : Update Object");
        String updName = objData.getName() + " Update";
        objData.setName(updName);

        String body = "{\r\n" +
            "\"name\" :\"" + objData.getName() + "\",\r\n " +
            "\"data\" : {\r\n" +
            "   \"year\" : "+ objData.getData().get("year") + ", \r\n" +
            "   \"price\" : "+ objData.getData().get("price") + ", \r\n" +
            "   \"cpu_model\" :\""+ objData.getData().get("cpu_model") + "\", \r\n" +
            "   \"hard_disk_size\" :\""+ objData.getData().get("hard_disk_size") + "\", \r\n" +
            "   \"capacity\" :\""+ objData.getData().get("capacity") + "\", \r\n" +
            "   \"screen_size\" :\""+ objData.getData().get("screen_size") + "\", \r\n" +
            "   \"color\" :\""+ objData.getData().get("color") + "\", \r\n" +
            "   } " +
            "}";

        // Hit api
        Response res = RestAssured
                .given()
                .header("Authorization", "Bearer " + token)
                .contentType("application/json")
                .body(body)
                .log()
                .all()
                .when()
                .put(BASE_URL + "/37777abe-a5ef-4570-a383-c99b5f5f7906/api/objects/" + dynamicObjectID);

        if (res.statusCode() == EXPECT_STATUS_CODE) {
            Assert.assertNotNull(res.jsonPath().getString("[0].id"));
            Assert.assertEquals(objData.getName(), res.jsonPath().getString("[0].name"));
        }
    }

    @Test(description = "Partially Update Object")
    public void partiallyUpdateObject() {
        System.out.println("Running : Partially Update Object");
        String updName = objData.getName() + " Update Partially";
        objData.setName(updName);

        String body = "{\r\n" +
            "\"name\" :\"" + objData.getName() + "\",\r\n " +
            "}";

        // Hit api
        Response res = RestAssured
                .given()
                .header("Authorization", "Bearer " + token)
                .contentType("application/json")
                .body(body)
                .log()
                .all()
                .when()
                .patch(BASE_URL + "/39a0f904-b0f2-4428-80a3-391cea5d7d04/api/object/" + dynamicObjectID);

        if (res.statusCode() == EXPECT_STATUS_CODE) {
            Assert.assertNotNull(res.jsonPath().getString("id"));
            Assert.assertEquals(objData.getName(), res.jsonPath().getString("name"));
        }
    }

    @Test(description = "Get List All Object")
    public void getListAllObject() {
        System.out.println("Running  : Get List All Object");
        Response res = RestAssured
            .given()
            .header("Authorization", "Bearer " + token)
            .log()
            .all()
            .when()
            .get(BASE_URL + "/api/objects");

        System.out.println(res.getStatusCode());

        if (res.statusCode() == EXPECT_STATUS_CODE) {           

            List<Map<String, Object>> items = res.jsonPath().getList("$");

            Assert.assertTrue(items.size() > 0);

            // Validasi items berdasarkan attribute
            Map<String, Object> firstItem = items.get(0);
            Assert.assertTrue(firstItem.containsKey("id"));
            Assert.assertTrue(firstItem.containsKey("name"));
            Assert.assertTrue(firstItem.containsKey("data"));

            // Validasi items nested dari object data
            Map<String, Object> firstItemData = (Map<String, Object>) firstItem.get("data");
            Assert.assertTrue(firstItemData.containsKey("year"));
            Assert.assertTrue(firstItemData.containsKey("price"));
            Assert.assertTrue(firstItemData.containsKey("CPU model"));
            Assert.assertTrue(firstItemData.containsKey("Hard disk size"));
            Assert.assertTrue(firstItemData.containsKey("color"));
            Assert.assertTrue(firstItemData.containsKey("capacity"));
            Assert.assertTrue(firstItemData.containsKey("screen_size"));

            getRandomIdFromResponse(items);

            System.out.println(dynamicObjectID);
        }
    }

    @Test(description = "Get List of Object By Id")
    public void getListofObjectsById() {
        System.out.println("Running  : Get List of Object By Id");
        Response res = RestAssured
            .given()
            .header("Authorization", "Bearer " + token)
            .log()
            .all()
            .when()
            .get(BASE_URL + "/api/objects?id=" + dynamicObjectID);

        System.out.println(res.getStatusCode());

        if (res.statusCode() == EXPECT_STATUS_CODE) {           

            List<Map<String, Object>> items = res.jsonPath().getList("$");

            Assert.assertTrue(items.size() > 0);

            // Validasi items berdasarkan attribute
            Map<String, Object> firstItem = items.get(0);
            Assert.assertTrue(firstItem.containsKey("id"));
            Assert.assertTrue(firstItem.containsKey("name"));
            Assert.assertTrue(firstItem.containsKey("data"));

            // Validasi items nested dari object data
            Map<String, Object> firstItemData = (Map<String, Object>) firstItem.get("data");
            Assert.assertTrue(firstItemData.containsKey("year"));
            Assert.assertTrue(firstItemData.containsKey("price"));
            Assert.assertTrue(firstItemData.containsKey("CPU model"));
            Assert.assertTrue(firstItemData.containsKey("Hard disk size"));
            Assert.assertTrue(firstItemData.containsKey("color"));
            Assert.assertTrue(firstItemData.containsKey("capacity"));
            Assert.assertTrue(firstItemData.containsKey("screen_size"));
        }
    }

    @Test(description = "Get Single Object")
    public void getSingleObject() {
        System.out.println("Running  : Get Single Object");
        Response res = RestAssured
            .given()
            .header("Authorization", "Bearer " + token)
            .log()
            .all()
            .when()
            .get(BASE_URL + "/8749129e-f5f7-4ae6-9b03-93be7252443c/api/objects/" + dynamicObjectID);

        System.out.println(res.getStatusCode());

        if (res.statusCode() == EXPECT_STATUS_CODE) {   
            Assert.assertNotNull(res.jsonPath().getString("id"));
            Assert.assertFalse(res.jsonPath().getString("name").isEmpty());

            // Validasi object attribute data pada response
            Map<String, Object> resData = res.jsonPath().getMap("data");
            Assert.assertFalse(resData.get("year").toString().isEmpty());
            Assert.assertFalse(resData.get("price").toString().isEmpty());
            Assert.assertFalse(resData.get("cpu_model").toString().isEmpty());
            Assert.assertFalse(resData.get("hard_disk_size").toString().isEmpty());
            Assert.assertFalse(resData.get("color").toString().isEmpty());
            Assert.assertNotNull(resData.get("capacity"));
            Assert.assertNotNull(resData.get("screen_size"));
        }
    }

    
    @Test(dependsOnMethods = {"updateObject"}, description = "Delete Object")
    public void deleteObject() {
        System.out.println("Running  : Delete Object");
        Response res = RestAssured
            .given()
            .header("Authorization", "Bearer " + token)
            .log()
            .all()
            .when()
            .delete(BASE_URL + "/d79a30ed-1066-48b6-83f5-556120afc46f/api/objects/" + dynamicObjectID);

        if (res.statusCode() == EXPECT_STATUS_CODE) {
            Assert.assertEquals(res.jsonPath().getString("status"), "deleted");
            Assert.assertTrue(res.jsonPath().getString("message").contains(String.valueOf(dynamicObjectID)));
        }
    }

    @Test(description = "Get All Department")
    public void getAllDepartment() {
        System.out.println("Running  : Get All Department");
        Response res = RestAssured
            .given()
            .header("Authorization", "Bearer " + token)
            .log()
            .all()
            .when()
            .get(BASE_URL + "/api/department");

            System.out.println(res.getStatusCode());

        if (res.statusCode() == EXPECT_STATUS_CODE) {           

            List<Map<String, Object>> items = res.jsonPath().getList("$");

            Assert.assertTrue(items.size() > 0);

            // Validasi items berdasarkan attribute
            Map<String, Object> firstItem = items.get(0);
            Assert.assertTrue(firstItem.containsKey("id"));
            Assert.assertTrue(firstItem.containsKey("department"));            
        }
    }
}
