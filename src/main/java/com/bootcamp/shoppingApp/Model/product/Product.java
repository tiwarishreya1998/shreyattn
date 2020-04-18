package com.bootcamp.shoppingApp.Model.product;

import com.bootcamp.shoppingApp.Model.categoryPack.Category;
import com.bootcamp.shoppingApp.Model.user.Seller;
import com.bootcamp.shoppingApp.Model.utilPack.AuditingInfo;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String name;
    private String description;
    private Boolean isCancellable;
    private Boolean isReturnable;
    private String brand;
    private  Boolean isActive;
    private Boolean isDeleted;

    public Boolean getDeleted() {
        return isDeleted;
    }

    public void setDeleted(Boolean deleted) {
        isDeleted = deleted;
    }

    @Embedded
    private AuditingInfo auditingInfo;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "seller_user_id")
    private Seller seller;

    @OneToMany(mappedBy = "product",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private Set<ProductVariation> productVariations;

    @OneToMany(mappedBy = "product",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private Set<ProductReview> productReviews;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getCancellable() {
        return isCancellable;
    }

    public void setCancellable(Boolean cancellable) {
        isCancellable = cancellable;
    }

    public Boolean getReturnable() {
        return isReturnable;
    }

    public void setReturnable(Boolean returnable) {
        isReturnable = returnable;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public AuditingInfo getAuditingInfo() {
        return auditingInfo;
    }

    public void setAuditingInfo(AuditingInfo auditingInfo) {
        this.auditingInfo = auditingInfo;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Seller getSeller() {
        return seller;
    }

    public void setSeller(Seller seller) {
        this.seller = seller;
    }

    public Set<ProductVariation> getProductVariations() {
        return productVariations;
    }

    public void setProductVariations(Set<ProductVariation> productVariations) {
        this.productVariations = productVariations;
    }

    public Set<ProductReview> getProductReviews() {
        return productReviews;
    }

    public void setProductReviews(Set<ProductReview> productReviews) {
        this.productReviews = productReviews;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", isCancellable=" + isCancellable +
                ", isReturnable=" + isReturnable +
                ", brand='" + brand + '\'' +
                ", isActive=" + isActive +
                ", auditingInfo=" + auditingInfo +
                ", category=" + category +
                ", seller=" + seller +
                ", productVariations=" + productVariations +
                ", productReviews=" + productReviews +
                '}';
    }
}
