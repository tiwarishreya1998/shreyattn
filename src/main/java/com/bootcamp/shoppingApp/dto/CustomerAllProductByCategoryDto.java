package com.bootcamp.shoppingApp.dto;

import com.bootcamp.shoppingApp.Model.product.Product;

import java.util.List;

public class CustomerAllProductByCategoryDto {
    private List<Product> products;
    private List<String> image;

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public List<String> getImage() {
        return image;
    }

    public void setImage(List<String> image) {
        this.image = image;
    }
}
