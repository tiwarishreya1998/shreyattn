package com.example.jpa_2part2.TablePerClass;


import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class EmployeeType2  {
@Id
@GeneratedValue(strategy = GenerationType.AUTO)
private Long id;
    private String name;
    private int salary;

    public EmployeeType2() {
    }

    public EmployeeType2(String name, int salary) {
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
        return "EmployeeType2{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", salary=" + salary +
                '}';
    }
}

