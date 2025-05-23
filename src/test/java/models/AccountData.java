package models;

import java.io.File;
import java.io.IOException;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;

public class AccountData {
    private String email;
    @JsonProperty("full_name")
    private String fullName;
    private String password;
    private String department;
    @JsonProperty("phone_number")
    private String phoneNumber;
    
    public AccountData() {}

    public String getEmail() { return this.email; }
    public void setEmail(String email) { this.email = email; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }

    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    public static AccountData loadDataFromFile(String filePath) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        AccountData dataFromFile = mapper.readValue(new File(filePath), AccountData.class);
        return dataFromFile;
        
    }

}
