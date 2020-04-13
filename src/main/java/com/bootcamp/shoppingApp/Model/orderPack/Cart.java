package com.bootcamp.shoppingApp.Model.orderPack;

import com.bootcamp.shoppingApp.Model.product.ProductVariation;
import com.bootcamp.shoppingApp.Model.user.Customer;

import javax.persistence.*;

@Entity
public class Cart {

    @EmbeddedId
    private CustomerProductVariationId customerProductVariationId;

    @OneToOne
    @JoinColumn(name = "customer_user_id",updatable = false,insertable=false)
    private Customer customer;

    private Integer quantity;
    private Boolean isWishListItem;

    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinColumn(name = "product_variation_id",updatable = false,insertable = false)
    private ProductVariation productVariation;

    public ProductVariation getProductVariation() {
        return productVariation;
    }

    public void setProductVariation(ProductVariation productVariation) {
        this.productVariation = productVariation;
    }

    public CustomerProductVariationId getCustomerProductVariationId() {
        return customerProductVariationId;
    }

    public void setCustomerProductVariationId(CustomerProductVariationId customerProductVariationId) {
        this.customerProductVariationId = customerProductVariationId;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Boolean getWishListItem() {
        return isWishListItem;
    }

    public void setWishListItem(Boolean wishListItem) {
        isWishListItem = wishListItem;
    }
}
