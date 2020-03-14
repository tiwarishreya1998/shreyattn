package com.shreya.JPAtest.Entity;



import javax.persistence.*;

@Entity
public class Employee {
    @Id
    private int id;
    private String name;
    private int age;
    private String location;

    public int getId() {
        return id;
    }

    public void setId(int id) {
      this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
       this.name = name;
    }

    public int getAge() {


        return age;
    }

    public void setAge(int age) {this.age = age;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}