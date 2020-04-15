package com.bootcamp.shoppingApp.Model.user;

import com.bootcamp.shoppingApp.Model.orderPack.Cart;
import com.bootcamp.shoppingApp.Model.orderPack.OrderT;
import com.bootcamp.shoppingApp.Model.product.ProductReview;
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
    private Set<OrderT> orderTS;

    @OneToOne(mappedBy = "customer",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private Cart cart;

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public Set<ProductReview> getProductReviews() {
        return productReviews;
    }

    public void setProductReviews(Set<ProductReview> productReviews) {
        this.productReviews = productReviews;
    }

    public Set<OrderT> getOrderTS() {
        return orderTS;
    }

    public void setOrderTS(Set<OrderT> orderTS) {
        this.orderTS = orderTS;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }
}
