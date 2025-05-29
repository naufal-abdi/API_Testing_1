package cucumber.helper;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import models.TokenData;

public class HitEndpoint {
    private static TokenData tokenData = new TokenData();

    public static Response createRequest(String method, String pathIndex, String bodyRequest, boolean withToken)
            throws Exception {
        Response res = null;
        RestAssured.baseURI = ConfigManager.getBaseUrl();
        RequestSpecification request = RestAssured.given()
                .header("Content-Type", "application/json");

        if (withToken) {
            tokenData.loadTokenFromFile((String) ConfigManager.getConfigByIndex("TOKEN_DATA_PATH"));
            String token = tokenData.getToken();

            request.header("Authorization", "Bearer " + token);
        }

        request.log().all();

        if (!bodyRequest.isBlank() || bodyRequest != "") {
            request.body(bodyRequest);
        }

        // choose method
        switch (method.toUpperCase()) {
            case "POST":
                res = request.when().post(pathIndex);
                break;
            case "GET":
                res = request.when().get(pathIndex);
                break;
            case "PUT":
                res = request.when().put(pathIndex);
                break;
            case "DELETE":
                res = request.when().delete(pathIndex);
                break;
            case "PATCH":
                res = request.when().patch(pathIndex);
                break;
            default:
                throw new IllegalArgumentException("Unsupported HTTP method: " + method);
        }

        return res;
    }
}
