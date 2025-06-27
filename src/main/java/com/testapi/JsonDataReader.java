package com.testapi;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;

import io.github.cdimascio.dotenv.Dotenv;

public class JsonDataReader {
    ObjectMapper objectMapper = new ObjectMapper();
    private static final Dotenv dotenv = Dotenv.configure().load();

    static String jsonStringOutput = "";

    public JsonDataReader() {
        try {
            JsonNode jsonData = JsonDataReader.readJsonFile(dotenv.get("TEST_DATA_PATH"));
            
            jsonStringOutput = objectMapper.writeValueAsString(jsonData);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static JsonNode readJsonFile(String filePath) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readTree(new File(filePath));
    }

    public static Object getValueByKey(JsonNode node, String key) {
        JsonNode keyNode = node.get(key);

        return (keyNode != null) ? keyNode : null;
    }

    public List<String> getInvalidLoginData() {
        String invalidUsername = JsonPath.read(jsonStringOutput, "$.invalidLoginData[0].username");
        String invalidPassword = JsonPath.read(jsonStringOutput, "$.invalidLoginData[0].password");

        List<String> invalidLoginData = Arrays.asList(invalidUsername, invalidPassword);

        return invalidLoginData;
    }

    public List<String> getValidLoginData() {
        String validUsername = JsonPath.read(jsonStringOutput, "$.invalidLoginData[1].username");
        String validPassword = JsonPath.read(jsonStringOutput, "$.invalidLoginData[1].password");

        return Arrays.asList(validUsername, validPassword);
    }

    public List<String> getProductData() {
        List<String> products = JsonPath.read(jsonStringOutput, "$.productToCheckout");

        return products;
    }

    
}
