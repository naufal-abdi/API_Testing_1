package models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AccountData {
    private String email;
    @JsonProperty("full_name")
    private String full_name;
    private String password;
    private String department;
    @JsonProperty("phone_number")
    private String phone_number;
    private boolean is_used;

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getFullName() { return full_name; }
    public void setFullName(String full_name) { this.full_name = full_name; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }

    public String getPhoneNumber() { return phone_number; }
    public void setPhoneNumber(String phone_number) { this.phone_number = phone_number; }

    public boolean isIs_used() { return is_used; }
    public void setIs_used(boolean is_used) { this.is_used = is_used; }
}
