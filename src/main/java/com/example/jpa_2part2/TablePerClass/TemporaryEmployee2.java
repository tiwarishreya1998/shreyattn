package com.example.jpa_2part2.TablePerClass;

import javax.persistence.Entity;

@Entity
public class TemporaryEmployee2 extends EmployeeType2 {
    String test_score;

    public TemporaryEmployee2() {
    }

    public TemporaryEmployee2
            (String name, int salary, String test_score) {
        super(name, salary);
        this.test_score=test_score;
    }

    public String getTest_score() {
        return test_score;
    }

    public void setTest_score(String test_score) {
        this.test_score = test_score;
    }

    @Override
    public String toString() {
        return "TemporaryEmployee2{" +
                "test_score='" + test_score + '\'' +
                "} " + super.toString();
    }
}