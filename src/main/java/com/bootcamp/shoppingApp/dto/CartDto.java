package com.bootcamp.shoppingApp.dto;

import com.bootcamp.shoppingApp.Model.product.ProductVariation;

public class CartDto {
    private Long productVariationId;

    private Boolean isWishListItem;
    private Integer quantity;
    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }


    public Long getProductVariationId() {
        return productVariationId;
    }

    public void setProductVariationId(Long productVariationId) {
        this.productVariationId = productVariationId;
    }

    public Boolean getWishListItem() {
        return isWishListItem;
    }

    public void setWishListItem(Boolean wishListItem) {
        isWishListItem = wishListItem;
    }
}
