package com.bootcamp.shoppingApp.Model.orderPack;

import com.bootcamp.shoppingApp.Model.product.ProductVariation;
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
    private Integer quantity;
    private BigDecimal price;

   @ManyToOne
   @JoinColumn(name = "order_t_id")
   private OrderT orderT;

   @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
   @JoinColumn(name = "product_variation_id")
   private ProductVariation productVariation;

   @Convert(converter = HashMapConverter.class)
   private Map<String,Object> productVariationMetadata;

    @OneToMany(mappedBy = "orderProduct",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private Set<OrderStatus> orderStatuses;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        quantity = quantity;
    }

    public OrderT getOrderT() {
        return orderT;
    }

    public void setOrderT(OrderT orderT) {
        this.orderT = orderT;
    }

    public ProductVariation getProductVariation() {
        return productVariation;
    }

    public void setProductVariation(ProductVariation productVariation) {
        this.productVariation = productVariation;
    }

    public Map<String, Object> getProductVariationMetadata() {
        return productVariationMetadata;
    }

    public void setProductVariationMetadata(Map<String, Object> productVariationMetadata) {
        this.productVariationMetadata = productVariationMetadata;
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
