package com.bootcamp.shoppingApp.Model.categoryPack;



import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Set;

@Entity
public class CategoryMetadataField {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String name;

    @OneToMany(mappedBy ="categoryMetadataField",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private Set<CategoryMetadataFieldValues> categoryMetadataFieldValuesSet;

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

    @JsonIgnore
    public Set<CategoryMetadataFieldValues> getCategoryMetadataFieldValuesSet() {
        return categoryMetadataFieldValuesSet;
    }

    public void setCategoryMetadataFieldValuesSet(Set<CategoryMetadataFieldValues> categoryMetadataFieldValuesSet) {
        this.categoryMetadataFieldValuesSet = categoryMetadataFieldValuesSet;
    }
}
