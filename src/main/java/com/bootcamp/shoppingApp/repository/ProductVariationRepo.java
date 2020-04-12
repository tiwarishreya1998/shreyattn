package com.bootcamp.shoppingApp.repository;

import com.bootcamp.shoppingApp.Model.Product.ProductVariation;
import org.springframework.data.repository.CrudRepository;

public interface ProductVariationRepo extends CrudRepository<ProductVariation,Long> {
}
