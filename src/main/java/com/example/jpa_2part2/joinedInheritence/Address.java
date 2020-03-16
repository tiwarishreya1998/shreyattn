package com.example.jpa_2part2.joinedInheritence;

import javax.persistence.Embeddable;

@Embeddable
public class Address {
    private String city;
    private String area;

    public Address() {
    }

    public Address(String city,String area) {
        this.area = area;
        this.city = city;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }
}