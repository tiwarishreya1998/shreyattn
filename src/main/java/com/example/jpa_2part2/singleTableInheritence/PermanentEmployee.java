package com.example.jpa_2part2.singleTableInheritence;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("regular_employee")
public class PermanentEmployee extends EmployeeType {

    private String project;

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public PermanentEmployee() {
    }

    public PermanentEmployee(String name, int salary, String project) {
        super(name,salary);
        this.project = project;
    }

    @Override
    public String toString() {
        return "RegularEmployee{" +
                "project='" + project + '\'' +
                "} " + super.toString();
    }
}
