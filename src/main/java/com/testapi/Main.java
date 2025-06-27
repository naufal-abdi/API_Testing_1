package com.testapi;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import io.github.cdimascio.dotenv.Dotenv;

public class Main {
    
    

    public static void main(String[] args) {
        JsonDataReader jsonDataReader = new JsonDataReader();

        System.out.println(jsonDataReader.getProductData());


       
    }
}