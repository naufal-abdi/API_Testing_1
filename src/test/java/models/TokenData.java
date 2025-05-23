package models;

import java.io.File;
import java.io.IOException;
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
        
    }
}
