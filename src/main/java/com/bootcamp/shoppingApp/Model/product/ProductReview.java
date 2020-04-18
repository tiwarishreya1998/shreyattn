package com.bootcamp.shoppingApp.Model.product;

import com.bootcamp.shoppingApp.Model.user.Customer;
import com.bootcamp.shoppingApp.Model.utilPack.AuditingInfo;

import javax.persistence.*;

@Entity
public class ProductReview {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String review;
    private Integer rating;

    @Embedded
    private AuditingInfo auditingInfo;

    @ManyToOne
    @JoinColumn(name = "customer_user_id",insertable = false,updatable = false)
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "product_id",insertable = false,updatable = false)
    private Product product;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
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

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
