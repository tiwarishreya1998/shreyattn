package com.example.Restful2.model;

public class BasicUser {
    private String name;
    private String age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public BasicUser() {
    }

    public BasicUser(String name, String age) {
        this.name = name;
        this.age = age;
    }
}
