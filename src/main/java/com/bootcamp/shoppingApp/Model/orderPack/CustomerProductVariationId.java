package com.bootcamp.shoppingApp.Model.orderPack;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class CustomerProductVariationId {
    @Column(name = "customer_user_id")
    private Long customerUserId;
    @Column(name="product_variation_id")
    private Long productVariationId;

    public Long getCustomerUserId() {
        return customerUserId;
    }

    public void setCustomerUserId(Long customerUserId) {
        this.customerUserId = customerUserId;
    }

    public Long getProductVariationId() {
        return productVariationId;
    }

    public void setProductVariationId(Long productVariationId) {
        this.productVariationId = productVariationId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CustomerProductVariationId that = (CustomerProductVariationId) o;
        return Objects.equals(customerUserId, that.customerUserId) &&
                Objects.equals(productVariationId, that.productVariationId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(customerUserId, productVariationId);
    }
}
