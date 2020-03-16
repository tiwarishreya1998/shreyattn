package com.example.jpa_2part2.singleTableInheritence;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("trainee_employee")
public class TemporaryEmployee extends EmployeeType {
    String assessment_score;

    public TemporaryEmployee() {
    }

    public TemporaryEmployee(String name, int salary, String assessment_score) {
        super(name, salary);
        this.assessment_score=assessment_score;
    }

    public String getAssessment_score() {
        return assessment_score;
    }

    public void setAssessment_score(String assessment_score) {
        this.assessment_score = assessment_score;
    }

    @Override
    public String toString() {
        return "TemporaryEmployee{" +
                "assessment_score='" + assessment_score + '\'' +
                "} " + super.toString();
    }
}