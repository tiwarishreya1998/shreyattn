package com.bootcamp.shoppingApp.Model.categoryPack;

import javax.persistence.*;

@Entity
public class CategoryMetaDataFieldValues {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "category_metadata_field_id")
    private CategoryMetaDataField categoryMetaDataField;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public CategoryMetaDataField getCategoryMetaDataField() {
        return categoryMetaDataField;
    }

    public void setCategoryMetaDataField(CategoryMetaDataField categoryMetaDataField) {
        this.categoryMetaDataField = categoryMetaDataField;
    }
}
