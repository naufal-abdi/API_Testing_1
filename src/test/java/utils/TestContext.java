package utils;

import java.util.HashMap;
import java.util.Map;

import io.restassured.response.Response;

public class TestContext {
    private static final Map<String, Object> contextData = new HashMap<>();
    private Response response;

    public void set(String key, Object value) {
        contextData.put(key, value);
    }

    public Object get(String key) {
        return contextData.get(key);
    }

    public void setResponse(Response response) {
        this.response = response;
    }

     public Response getResponse() {
        return response;
    }

    public String getString(String key) {
        Object value = contextData.get(key);
        return value != null ? value.toString() : null;
    }

    public void clear() {
        contextData.clear();
    }
}
