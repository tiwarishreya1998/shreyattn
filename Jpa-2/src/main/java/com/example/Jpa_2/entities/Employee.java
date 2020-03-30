package com.example.Jpa_2.entities;

import javax.persistence.*;

@Entity
//@Table(name = "employee_table")
public class Employee {
    @Id
//    @Column(name = "emp_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  int id;
//    @Column(name = "emp_first_name")
    private String firstName;
//    @Column(name = "emp_last_name")
    private String lastName;
//    @Column(name = "emp_salary")
    private Double salary;
//    @Column(name = "emp_age")
    private Integer age;

    public int getId() {
        return id;
    }

    public void setId( int id) {
        this.id = id;
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

    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}