package com.example.Restful2.model;

public class DetailedUser {
    private String firstName;
    private String lastName;
    private String add;

    public DetailedUser() {
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAdd() {
        return add;
    }

    public void setAdd(String add) {
        this.add = add;
    }

    public DetailedUser(String firstName, String lastName, String add) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.add = add;
    }
}
