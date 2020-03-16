package com.example.jpa_2part2.embedded;

import javax.persistence.Embeddable;

@Embeddable
public class EmployeeSalary {
    private Long basicSalary;
    private Long bonusSalary;
    private Long taxAmount;
    private Long specialAllowanceSalary;

    public EmployeeSalary(Long basicSalary, Long bonusSalary, Long taxAmount, Long specialAllowanceSalary) {
        this.basicSalary = basicSalary;
        this.bonusSalary = bonusSalary;
        this.taxAmount = taxAmount;
        this.specialAllowanceSalary = specialAllowanceSalary;
    }

    public EmployeeSalary() {
    }

    public Long getBasicSalary() {
        return basicSalary;
    }

    public void setBasicSalary(Long basicSalary) {
        this.basicSalary = basicSalary;
    }

    public Long getBonusSalary() {
        return bonusSalary;
    }

    public void setBonusSalary(Long bonusSalary) {
        this.bonusSalary = bonusSalary;
    }

    public Long getTaxAmount() {
        return taxAmount;
    }

    public void setTaxAmount(Long taxAmount) {
        this.taxAmount = taxAmount;
    }

    public Long getSpecialAllowanceSalary() {
        return specialAllowanceSalary;
    }

    public void setSpecialAllowanceSalary(Long specialAllowanceSalary) {
        this.specialAllowanceSalary = specialAllowanceSalary;
    }
}
