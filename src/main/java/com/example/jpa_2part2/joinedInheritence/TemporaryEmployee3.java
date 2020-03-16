package com.example.jpa_2part2.joinedInheritence;


import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;

@Entity
@PrimaryKeyJoinColumn(name = "id")
public class TemporaryEmployee3 extends EmployeeType3 {
    String marks;

    @Embedded
    Address address;

    public TemporaryEmployee3() {
    }

    public TemporaryEmployee3(String name, int salary, String marks,Address address) {
        super(name, salary);
        this.marks=marks;
        this.address=address;
    }

    public String getMarks() {
        return marks;
    }

    public void setMarks(String Marks) {
        this.marks=marks;
    }

    @Override
    public String toString() {
        return "TemporaryEmployee{" +
                "marks='" + marks + '\'' +
                "} " + super.toString();
    }
}
