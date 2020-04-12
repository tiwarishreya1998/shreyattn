package com.bootcamp.shoppingApp.Model.categoryPack;



import javax.persistence.*;
import java.util.Set;

@Entity
public class CategoryMetaDataField {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String name;

    @OneToMany(mappedBy ="categoryMetaDataField",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private Set<CategoryMetaDataFieldValues> categoryMetaDataFieldValuesSet;

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

    public Set<CategoryMetaDataFieldValues> getCategoryMetaDataFieldValuesSet() {
        return categoryMetaDataFieldValuesSet;
    }

    public void setCategoryMetaDataFieldValuesSet(Set<CategoryMetaDataFieldValues> categoryMetaDataFieldValuesSet) {
        this.categoryMetaDataFieldValuesSet = categoryMetaDataFieldValuesSet;
    }
}
