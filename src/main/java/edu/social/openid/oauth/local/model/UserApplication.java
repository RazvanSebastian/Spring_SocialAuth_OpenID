package edu.social.openid.oauth.local.model;

public class UserApplication extends UserBasicInformation {

    private String phone;
    private String password;

    public UserApplication(String email) {
        super(email);
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
