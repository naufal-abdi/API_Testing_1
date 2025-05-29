package models;

public class LoginData {
    private String email;
    private String password;

    //public LoginData(String email, String password) {
    //    this.email = email;
    //    this.password = password;
    //}

    public LoginData() {

    }

    public String getEmail() { return this.email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}