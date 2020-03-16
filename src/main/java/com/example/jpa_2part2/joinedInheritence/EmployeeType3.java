package com.example.jpa_2part2.joinedInheritence;


import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class EmployeeType3 {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private int salary;

    public EmployeeType3() {
    }

    public EmployeeType3(String name, int salary) {
        this.name=name;
        this.salary=salary;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }


    @Override
    public String toString() {
        return "EmployeeType3{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", salary=" + salary +
                '}';
    }
}

