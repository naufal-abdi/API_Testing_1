package models;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class TokenData {
    private String token;

    public TokenData() {}

    public TokenData(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void loadTokenFromFile(String filePath) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        TokenData dataFromFile = mapper.readValue(new File(filePath), TokenData.class);
        this.token = dataFromFile.getToken();
        
        // String rawJson = new String(Files.readAllBytes(Paths.get(filePath)));
        // System.out.println("Raw file content:\n" + rawJson);
    }
}
