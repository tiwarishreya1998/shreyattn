package com.bootcamp.shoppingApp.Model.user;

import com.bootcamp.shoppingApp.Model.orderPack.Cart;
import com.bootcamp.shoppingApp.Model.orderPack.Order;
import com.bootcamp.shoppingApp.Model.Product.ProductReview;
import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.Set;

@Entity
@PrimaryKeyJoinColumn(name = "user_id")
@JsonIgnoreProperties(ignoreUnknown = true)  // all the extra properties should be ignored.
@JsonFilter("ignoreAddressInCustomer")
public class Customer extends User {

    private String contact;

    @OneToMany(mappedBy = "customer",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private Set<ProductReview>productReviews;

    @OneToMany(mappedBy = "customer",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private Set<Order> orders;

    @OneToOne(mappedBy = "customer",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private Cart cart;

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }
}
