package com.bootcamp.shoppingApp.Model.product;

import com.bootcamp.shoppingApp.Model.utilPack.HashMapConverter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Map;

@Entity
public class ProductVariation {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private Long price;
    private Long quantityAvailable;
    private String primaryImageName;
    private Boolean isActive;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product  product;

    @Convert(converter = HashMapConverter.class)//to store meta data as json in db
    private Map<String, HashSet<String>>metadata;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public Long getQuantityAvailable() {
        return quantityAvailable;
    }

    public void setQuantityAvailable(Long quantityAvailable) {
        this.quantityAvailable = quantityAvailable;
    }

    public String getPrimaryImageName() {
        return primaryImageName;
    }

    public void setPrimaryImageName(String primaryImageName) {
        this.primaryImageName = primaryImageName;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Map<String, HashSet<String>> getMetadata() {
        return metadata;
    }

    public void setMetadata(Map<String, HashSet<String>> metadata) {
        this.metadata = metadata;
    }
}
