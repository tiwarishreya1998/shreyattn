package com.bootcamp.shoppingApp.repository;

import com.bootcamp.shoppingApp.Model.product.ProductVariation;
import org.springframework.data.repository.CrudRepository;

public interface ProductVariationRepo extends CrudRepository<ProductVariation,Long> {
}
