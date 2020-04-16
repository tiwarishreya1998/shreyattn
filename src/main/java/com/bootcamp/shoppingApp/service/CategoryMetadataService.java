package com.bootcamp.shoppingApp.service;

import com.bootcamp.shoppingApp.Model.categoryPack.Category;
import com.bootcamp.shoppingApp.Model.categoryPack.CategoryMetaDataField;
import com.bootcamp.shoppingApp.Model.categoryPack.CategoryMetaDataFieldValues;
import com.bootcamp.shoppingApp.dto.CategoryMetaDataDto;
import com.bootcamp.shoppingApp.exceptions.ResourceNotFoundException;
import com.bootcamp.shoppingApp.repository.CategoryMetaDataFieldRepo;
import com.bootcamp.shoppingApp.repository.CategoryMetaDataFieldValueRepo;
import com.bootcamp.shoppingApp.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class CategoryMetadataService {
    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private CategoryMetaDataFieldRepo categoryMetaDataFieldRepo;

    @Autowired
    private CategoryMetaDataFieldValueRepo categoryMetaDataFieldValueRepo;

    public String addCategoryMetaData(CategoryMetaDataDto categoryMetaDataDto){
       // System.out.println(categoryMetaDataDto);
        Optional<Category> category=categoryRepository.findById(categoryMetaDataDto.getCategoryId());
        System.out.println(category.get()+"---");
        if (!category.isPresent()){
            throw new ResourceNotFoundException(categoryMetaDataDto.getCategoryId()+" not found");

        }
        HashMap<String, HashSet<String>> filedIdVal=categoryMetaDataDto.getFiledIdValues();
        Set<String>metaDataIds=filedIdVal.keySet();
        metaDataIds.forEach(id->{
            Optional<CategoryMetaDataField>metaDataField=categoryMetaDataFieldRepo.findById(Long.parseLong(id));
            if (!metaDataField.isPresent()){
                throw new ResourceNotFoundException(id+"  metaData filed does not exist");

            }
        });
        metaDataIds.forEach(id->{
            if (filedIdVal.get(id).isEmpty()){
                throw new ResourceNotFoundException("any one filed does not have values");
            }
        });

        CategoryMetaDataFieldValues fieldValues=new CategoryMetaDataFieldValues();
        fieldValues.setCategory(category.get());
         //System.out.println(metaDataIds);
        metaDataIds.forEach(id->{
            int count=0;
            Optional<CategoryMetaDataField>metaDataField=categoryMetaDataFieldRepo.findById(Long.parseLong(id));
            fieldValues.setCategoryMetaDataField(metaDataField.get());
            HashSet<String>values=filedIdVal.get(id);
            String value=String.join(",",values);
            fieldValues.setValue(value);
            metaDataField.get().getCategoryMetaDataFieldValuesSet().add(fieldValues);
            categoryMetaDataFieldRepo.save(metaDataField.get());
            count++;
                  //  System.out.println(count+"----------");
                }
                );
        category.get().getCategoryMetaDataFieldValues().add(fieldValues);
        categoryRepository.save(category.get());
        categoryMetaDataFieldValueRepo.save(fieldValues);

        return "Success";
    }
}
