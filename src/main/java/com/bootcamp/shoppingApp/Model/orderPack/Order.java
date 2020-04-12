package com.bootcamp.shoppingApp.Model.orderPack;

import com.bootcamp.shoppingApp.Model.user.Customer;
import com.bootcamp.shoppingApp.Model.utilPack.AuditingInfo;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Set;

@Entity
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private BigDecimal amountPaid;
    private String paymentMethod;
    private String customerAddressCity;
    private String customerAddressState;
    private String customerAddressCountry;
    private String customerAddressAddressLine;
    private Integer customerAddressZipCode;
    private String customerAddressLabel;

    @Embedded
    private AuditingInfo auditingInfo;

    @ManyToOne
    @JoinColumn(name = "customer_user-id")
    private Customer customer;

    @OneToMany(mappedBy = "orderTable",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private Set<OrderProduct>orderProducts;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getAmountPaid() {
        return amountPaid;
    }

    public void setAmountPaid(BigDecimal amountPaid) {
        this.amountPaid = amountPaid;
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

    public AuditingInfo getAuditingInfo() {
        return auditingInfo;
    }

    public void setAuditingInfo(AuditingInfo auditingInfo) {
        this.auditingInfo = auditingInfo;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Set<OrderProduct> getOrderProducts() {
        return orderProducts;
    }

    public void setOrderProducts(Set<OrderProduct> orderProducts) {
        this.orderProducts = orderProducts;
    }
}
