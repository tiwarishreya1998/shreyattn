package com.example.Restful2.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@ApiModel(description = "this is user class")
public class User {
    @ApiModelProperty(notes = "This is user name")
    private String name;
    @ApiModelProperty(notes = "this is a user add")
    private String address;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAdd(String address) {
        this.address = address;
    }

    public User() {
    }

    public User(String name, String address) {
        this.name = name;
        this.address = address;
    }
}
