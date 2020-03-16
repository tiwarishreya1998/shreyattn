package com.example.jpa_2part2.embedded;

import javax.persistence.*;

@Entity
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String firstName;
    private String lastName;
    private int age;

    @Embedded
    EmployeeSalary employeeSalary;

    public Employee(Long id, String firstName, String lastName, int age, EmployeeSalary employeeSalary) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.employeeSalary = employeeSalary;
    }

    public Employee(String radha, String kumar, int i, EmployeeSalary employeeSalary) {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public EmployeeSalary getEmployeeSalary() {
        return employeeSalary;
    }

    public void setEmployeeSalary(EmployeeSalary employeeSalary) {
        this.employeeSalary = employeeSalary;
    }
}
