package test;

import cucumber.helper.ConfigManager;
import cucumber.helper.HitEndpoint;
import io.restassured.response.Response;

public class TestData {
    public static void main(String[] args) throws Exception{
        
        Response res = HitEndpoint.createRequest(
            "GET",
            (String) ConfigManager.getConfigByIndex("ENDPOINT_GET_ALL_DEPARTMENT"), 
            "", 
            true
        );

        System.out.println(res.body().asPrettyString());
        

        //System.out.println(ConfigManager.getBaseUrl());
    }
}