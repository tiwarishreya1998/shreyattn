package com.bootcamp.shoppingApp.dto;

import javax.validation.constraints.NotNull;

public class ProductDto {
    @NotNull
    private String name;
    @NotNull
    private String brand;
    @NotNull
    private String description;
    @NotNull
    private Long categoryId;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }
}
