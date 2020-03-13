package com.example.Restful2.model;

public class DynamicFilterUser {

    private String username;

    private String password;

    public DynamicFilterUser() {

    }


    public DynamicFilterUser(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
