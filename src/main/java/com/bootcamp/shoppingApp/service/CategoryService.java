package com.bootcamp.shoppingApp.service;

import com.bootcamp.shoppingApp.Model.categoryPack.Category;
import com.bootcamp.shoppingApp.exceptions.FieldAlreadyPresent;
import com.bootcamp.shoppingApp.exceptions.ResourceNotFoundException;
import com.bootcamp.shoppingApp.repository.CategoryRepository;
import com.bootcamp.shoppingApp.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductRepository productRepository;

    public String addCategory(String name, Optional<Long> parentId){
        if (categoryRepository.findByName(name)!=null){
            throw new FieldAlreadyPresent(name + "is already present as the category");
        }
        Category category=new Category();
        if (parentId.isPresent()) {
            if (productRepository.findByCategoryId(parentId.get()) != null) {
                return "parent id is already associated with some product";
            }
            else {
                category.setName(name);
                category.setParentId(categoryRepository.findById(parentId.get()).get());
                categoryRepository.save(category);
                return "Success " + categoryRepository.findByName(name).getId();
            }
        }
        if (!parentId.isPresent()) {
            category.setName(name);
            categoryRepository.save(category);
            return "Success " + categoryRepository.findByName(name).getId();
        }
        return "Success " + categoryRepository.findByName(name).getId();
    }

    @Transactional
    public String deleteCategory(Long id){
        if(!categoryRepository.findById(id).isPresent()) {
            throw new ResourceNotFoundException(id + "category does not exist");
        }
        if(productRepository.findByCategoryId(id)!=null){
        return "id is associated with some product, cannot delete";}
        if(categoryRepository.findByParentId(id).isPresent()){
            return "id is a parent id cannot delete";
        }
        categoryRepository.deleteById(id);
        return  "Success";
    }
    public String updateCategory(String name,Long id){
        if(!categoryRepository.findById(id).isPresent()){
            throw new ResourceNotFoundException(id+"category does not exist");
        }
        if(categoryRepository.findByName(name)!=null){
            throw new FieldAlreadyPresent(name+"category exists");
        }
        Optional<Category> category=categoryRepository.findById(id);
        Category update=category.get();
        update.setName(name);
        categoryRepository.save(update);
        return "Success";

    }
}
