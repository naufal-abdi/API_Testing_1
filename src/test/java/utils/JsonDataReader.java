package utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import models.AccountData;
import models.ObjectData;
import models.TokenData;

import java.io.File;
import java.util.List;
import java.util.Map;


public class JsonDataReader {

    public static AccountData getAccountData() throws Exception {
        ObjectMapper mapper = new ObjectMapper();

        String filePath = "src/test/resources/userData.json";
        // Read JSON Data
        List<AccountData> dataList = mapper.readValue(new File(filePath), new TypeReference<List<AccountData>>() {});

        for (AccountData data : dataList) {
            if (!data.isIs_used()) {
                // Mark as used
                // data.setIs_used(true);

                // save updated data
                // mapper.writerWithDefaultPrettyPrinter().writeValue(new File(filePath), dataList);
                return data;
            }
        }

        throw new RuntimeException("Semua data telah terpakai");
    }

    public static ObjectData getObjectData() throws Exception {
        ObjectMapper mapper = new ObjectMapper();

        String filePath = "src/test/resources/objectData.json";

        // Read JSON Data
        List<ObjectData> dataList = mapper.readValue(new File(filePath), new TypeReference<List<ObjectData>>() {});

        for (ObjectData data : dataList) {
            if (!data.isIs_used()) {
                // Mark as used
                // data.setIs_used(true);

                // save updated data
                // mapper.writerWithDefaultPrettyPrinter().writeValue(new File(filePath), dataList);
                return data;
            }
        }

        throw new RuntimeException("Semua data telah terpakai");
    }

    public static void updateToken(String token) throws Exception {
        String filePath = "src/test/resources/tokenData.json";

        File tokenJsonFile = new File(filePath);

        ObjectMapper mapper = new ObjectMapper();

        // Read Json Data 
        TokenData tokenData = mapper.readValue(tokenJsonFile, TokenData.class);

        tokenData.setToken(token);

        mapper.writerWithDefaultPrettyPrinter().writeValue(tokenJsonFile, tokenData);
    }
}
