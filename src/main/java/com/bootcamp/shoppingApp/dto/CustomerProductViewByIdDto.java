package com.bootcamp.shoppingApp.dto;

import com.bootcamp.shoppingApp.Model.product.Product;

public class CustomerProductViewByIdDto {
    private Product product;
    private ProductVarPlusImageDto productVarPlusImageDto;

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public ProductVarPlusImageDto getProductVarPlusImageDto() {
        return productVarPlusImageDto;
    }

    public void setProductVarPlusImageDto(ProductVarPlusImageDto productVarPlusImageDto) {
        this.productVarPlusImageDto = productVarPlusImageDto;
    }
}
