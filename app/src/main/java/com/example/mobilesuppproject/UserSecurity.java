package com.example.mobilesuppproject;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.List;

@Entity(tableName = "userinfo")
public class UserSecurity {
    @PrimaryKey
    @NonNull
    private String email;  // field name matches the constructor param

    private String password;
    private String fName;
    private String lName;
    private int age;
    private String phoneNb;

    public UserSecurity(@NonNull String email, String password,
                        String fName, String lName, int age, String phoneNb) {
        this.email = email;
        this.password = password;
        this.fName = fName;
        this.lName = lName;
        this.age = age;
        this.phoneNb = phoneNb;
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
    public String getFName() {
        return fName;
    }
    public void setFName(String fName) {
        this.fName = fName;
    }
    public String getLName() {
        return lName;
    }
    public void setLName(String lName) {
        this.lName = lName;
    }
    public int getAge() {
        return age;
    }
    public void setAge(int age) {
        this.age = age;
    }
    public String getPhoneNb() {
        return phoneNb;
    }
    public void setPhoneNb(String phoneNb) {
        this.phoneNb = phoneNb;
    }
}
