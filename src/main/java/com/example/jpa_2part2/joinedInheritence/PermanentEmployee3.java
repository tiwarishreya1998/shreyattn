package com.example.jpa_2part2.joinedInheritence;


import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;

@Entity
@PrimaryKeyJoinColumn(name = "id")
public class PermanentEmployee3 extends EmployeeType3 {

    private String project;
    @Embedded
    Address address;

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public PermanentEmployee3() {
    }

    public PermanentEmployee3(String name, int salary, String project,Address address) {
        super(name,salary);
        this.project = project;
        this.address=address;
    }

    @Override
    public String toString() {
        return "PermanentEmployee3{" +
                "project='" + project + '\'' +
                "} " + super.toString();
    }
}
