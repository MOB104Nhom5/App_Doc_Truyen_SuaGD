package com.example.app_doc_truyen.Model;

public class User {
//    FullName: String,
//    Email: String,
//    Password: String,
//    Role: String,
//    IDComicSave: [String],
//    IDComicUp: [String],
//    DateOfBirth:String,
//    Avatar:String,
//    GT:String,
//    PhoneNumber:String,
    String FullName , Email ,Password ,Role ,IDComicSave , IDComicUp  ;
    String DateOfBirth , Avatar , GT , PhoneNumber  , token ;
    String IDuser ;
    public User() {
    }

    public User(String email, String password) {
        this.Email = email;
        this.Password = password;
    }

    public User(String fullName, String email, String password, String dateOfBirth, String GT, String phoneNumber ,  String role) {
        FullName = fullName;
        Email = email;
        Password = password;
        DateOfBirth = dateOfBirth;
        this.GT = GT;
        PhoneNumber = phoneNumber;
        this.Role = role ;
    }


    public String getIDuser() {
        return IDuser;
    }

    public void setIDuser(String IDuser) {
        this.IDuser = IDuser;
    }

    public String getFullName() {
        return FullName;
    }

    public void setFullName(String fullName) {
        FullName = fullName;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getRole() {
        return Role;
    }

    public void setRole(String role) {
        Role = role;
    }

    public String getIDComicSave() {
        return IDComicSave;
    }

    public void setIDComicSave(String IDComicSave) {
        this.IDComicSave = IDComicSave;
    }

    public String getIDComicUp() {
        return IDComicUp;
    }

    public void setIDComicUp(String IDComicUp) {
        this.IDComicUp = IDComicUp;
    }

    public String getDateOfBirth() {
        return DateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        DateOfBirth = dateOfBirth;
    }

    public String getAvatar() {
        return Avatar;
    }

    public void setAvatar(String avatar) {
        Avatar = avatar;
    }

    public String getGT() {
        return GT;
    }

    public void setGT(String GT) {
        this.GT = GT;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        PhoneNumber = phoneNumber;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
