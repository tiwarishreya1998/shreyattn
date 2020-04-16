package com.bootcamp.shoppingApp.dto;

import javax.validation.constraints.NotNull;
import java.util.Map;

public class ProductVariationDto {
    @NotNull
    private Long id;
    private Long price;
    private Long quantityAvailable;
    private Map<String, String> metadata;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


}