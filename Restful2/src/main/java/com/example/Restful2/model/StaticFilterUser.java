package com.example.Restful2.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class StaticFilterUser {

    private String username;

    @JsonIgnore
    private String password;

    public StaticFilterUser() {

    }

    public StaticFilterUser(String username, String password) {
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
