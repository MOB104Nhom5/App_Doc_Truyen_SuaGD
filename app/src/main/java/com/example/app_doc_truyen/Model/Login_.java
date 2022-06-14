package com.example.app_doc_truyen.Model;

public class Login_ {
    private String email , password , token;

    public Login_(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public Login_() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String gettoken() {
        return token;
    }

    public void settoken(String token) {
        this.token = token;
    }
}
