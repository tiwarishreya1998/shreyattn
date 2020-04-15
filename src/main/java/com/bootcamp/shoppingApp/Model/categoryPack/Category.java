package com.bootcamp.shoppingApp.Model.categoryPack;

import com.bootcamp.shoppingApp.Model.product.Product;
import com.bootcamp.shoppingApp.Model.utilPack.AuditingInfo;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(unique = true,nullable =false)
    private String name;

    @Embedded
    private AuditingInfo auditingInfo;

    @OneToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinColumn(name = "parent_id",referencedColumnName = "id",nullable = true)
    private Category parentId;

    @OneToMany(mappedBy = "category",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private Set<Product>products;

    @OneToMany(mappedBy = "category",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private Set<CategoryMetaDataFieldValues> categoryMetaDataFieldValues;

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

    public AuditingInfo getAuditingInfo() {
        return auditingInfo;
    }

    public void setAuditingInfo(AuditingInfo auditingInfo) {
        this.auditingInfo = auditingInfo;
    }

    public Category getParentId() {
        return parentId;
    }

    public void setParentId(Category parentId) {
        this.parentId = parentId;
    }

    public Set<Product> getProducts() {
        return products;
    }

    public void setProducts(Set<Product> products) {
        this.products = products;
    }

    public Set<CategoryMetaDataFieldValues> getCategoryMetaDataFieldValues() {
        return categoryMetaDataFieldValues;
    }

    public void setCategoryMetaDataFieldValues(Set<CategoryMetaDataFieldValues> categoryMetaDataFieldValues) {
        this.categoryMetaDataFieldValues = categoryMetaDataFieldValues;
    }
}
