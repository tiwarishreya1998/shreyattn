package com.example.demo3.Ques2;

import javax.validation.constraints.Size;

public class Employee {
    private Integer id;
    @Size(min = 2, message = "Name should have atleast two characters")     //atleast \
    private String name;
    private int age;

    protected Employee() {              //default constructor for the post method
    }

    public Employee(int id, String name, int age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
