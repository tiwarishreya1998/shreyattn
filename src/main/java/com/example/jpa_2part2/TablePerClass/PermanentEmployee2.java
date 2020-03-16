package com.example.jpa_2part2.TablePerClass;

import javax.persistence.Entity;

@Entity
public class PermanentEmployee2 extends EmployeeType2 {

    private String project;

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public PermanentEmployee2() {
    }

    public PermanentEmployee2(String name, int salary, String project) {
        super(name,salary);
        this.project = project;
    }

    @Override
    public String toString() {
        return "PermanentEmployee2{" +
                "project='" + project + '\'' +
                "} " + super.toString();
    }
}