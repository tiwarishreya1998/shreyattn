package com.bootcamp.shoppingApp.dto;

import com.bootcamp.shoppingApp.Model.product.ProductVariation;

import java.util.List;
import java.util.Set;

public class ProductVarPlusImageDto {
    private Set<ProductVariation> productVariation;
    private List<String> images;

    public Set<ProductVariation> getProductVariation() {
        return productVariation;
    }

    public void setProductVariation(Set<ProductVariation> productVariation) {
        this.productVariation = productVariation;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }
}
