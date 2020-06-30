package com.bootcamp.shoppingApp.dto;

import com.bootcamp.shoppingApp.Model.categoryPack.Category;

import java.util.HashMap;
import java.util.Set;

public class CategoryDto {
    private Category category;
    private Set<HashMap<String,String>> filedValuesSet;
    private Set<Category> childCategory;

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Set<HashMap<String, String>> getFiledValuesSet() {
        return filedValuesSet;
    }

    public void setFiledValuesSet(Set<HashMap<String, String>> filedValuesSet) {
        this.filedValuesSet = filedValuesSet;
    }

    public Set<Category> getChildCategory() {
        return childCategory;
    }

    public void setChildCategory(Set<Category> childCategory) {
        this.childCategory = childCategory;
    }
}
