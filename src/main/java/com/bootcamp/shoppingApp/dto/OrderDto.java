package com.bootcamp.shoppingApp.dto;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

public class OrderDto {

    private BigDecimal amountPaid;
    private Date date;
    private String paymentMethod;
    private String customerAddressCity;
    private String customerAddressState;
    private String customerAddressCountry;
    private String customerAddressAddressLine;
    private Integer customerAddressZipCode;
    private String customerAddressLabel;
    private Set<OrderProductDto> orderProductDtoSet;

    public BigDecimal getAmountPaid() {
        return amountPaid;
    }

    public void setAmountPaid(BigDecimal amountPaid) {
        this.amountPaid = amountPaid;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getCustomerAddressCity() {
        return customerAddressCity;
    }

    public void setCustomerAddressCity(String customerAddressCity) {
        this.customerAddressCity = customerAddressCity;
    }

    public String getCustomerAddressState() {
        return customerAddressState;
    }

    public void setCustomerAddressState(String customerAddressState) {
        this.customerAddressState = customerAddressState;
    }

    public String getCustomerAddressCountry() {
        return customerAddressCountry;
    }

    public void setCustomerAddressCountry(String customerAddressCountry) {
        this.customerAddressCountry = customerAddressCountry;
    }

    public String getCustomerAddressAddressLine() {
        return customerAddressAddressLine;
    }

    public void setCustomerAddressAddressLine(String customerAddressAddressLine) {
        this.customerAddressAddressLine = customerAddressAddressLine;
    }

    public Integer getCustomerAddressZipCode() {
        return customerAddressZipCode;
    }

    public void setCustomerAddressZipCode(Integer customerAddressZipCode) {
        this.customerAddressZipCode = customerAddressZipCode;
    }

    public String getCustomerAddressLabel() {
        return customerAddressLabel;
    }

    public void setCustomerAddressLabel(String customerAddressLabel) {
        this.customerAddressLabel = customerAddressLabel;
    }

    public Set<OrderProductDto> getOrderProductDtoSet() {
        return orderProductDtoSet;
    }

    public void setOrderProductDtoSet(Set<OrderProductDto> orderProductDtoSet) {
        this.orderProductDtoSet = orderProductDtoSet;
    }
}
