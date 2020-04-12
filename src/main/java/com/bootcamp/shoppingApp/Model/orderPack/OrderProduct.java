package com.bootcamp.shoppingApp.Model.orderPack;

import com.bootcamp.shoppingApp.Model.Product.ProductVariation;
import com.bootcamp.shoppingApp.Model.utilPack.HashMapConverter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Map;
import java.util.Set;

@Entity
public class OrderProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private Integer Quantity;
    private BigDecimal price;

   @ManyToOne
   @JoinColumn(name = "order_id")
   private Order order;

   @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
   @JoinColumn(name = "product_variation_id")
   private ProductVariation productVariation;

   @Convert(converter = HashMapConverter.class)
   private Map<String,Object> productVariationMetaData;

    @OneToMany(mappedBy = "orderProduct",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private Set<OrderStatus> orderStatuses;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getQuantity() {
        return Quantity;
    }

    public void setQuantity(Integer quantity) {
        Quantity = quantity;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public ProductVariation getProductVariation() {
        return productVariation;
    }

    public void setProductVariation(ProductVariation productVariation) {
        this.productVariation = productVariation;
    }

    public Map<String, Object> getProductVariationMetaData() {
        return productVariationMetaData;
    }

    public void setProductVariationMetaData(Map<String, Object> productVariationMetaData) {
        this.productVariationMetaData = productVariationMetaData;
    }

    public Set<OrderStatus> getOrderStatuses() {
        return orderStatuses;
    }

    public void setOrderStatuses(Set<OrderStatus> orderStatuses) {
        this.orderStatuses = orderStatuses;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
