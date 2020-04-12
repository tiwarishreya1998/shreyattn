package com.bootcamp.shoppingApp.Model.Product;

import com.bootcamp.shoppingApp.Model.user.Customer;
import com.bootcamp.shoppingApp.Model.utilPack.AuditingInfo;

import javax.persistence.*;

@Entity
public class ProductReview {

    @EmbeddedId
    private CustomerProductReviewId customerProductReviewId;

    private String review;
    private Integer rating;

    @Embedded
    private AuditingInfo auditingInfo;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    public CustomerProductReviewId getCustomerProductReviewId() {
        return customerProductReviewId;
    }

    public void setCustomerProductReviewId(CustomerProductReviewId customerProductReviewId) {
        this.customerProductReviewId = customerProductReviewId;
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

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}
