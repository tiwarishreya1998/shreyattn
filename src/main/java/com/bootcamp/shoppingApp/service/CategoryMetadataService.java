package com.bootcamp.shoppingApp.service;

import com.bootcamp.shoppingApp.Model.categoryPack.Category;
import com.bootcamp.shoppingApp.Model.categoryPack.CategoryMetadataField;
import com.bootcamp.shoppingApp.Model.categoryPack.CategoryMetadataFieldValues;
import com.bootcamp.shoppingApp.dto.CategoryMetadataDto;
import com.bootcamp.shoppingApp.exceptions.ResourceNotFoundException;
import com.bootcamp.shoppingApp.repository.CategoryMetadataFieldRepo;
import com.bootcamp.shoppingApp.repository.CategoryMetadataFieldValueRepo;
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
    private CategoryRepository categoryRepo;

    @Autowired
    private CategoryMetadataFieldRepo metadataRepo;

    @Autowired
    private CategoryMetadataFieldValueRepo valueRepo;

    public String addCategoryMetadata(CategoryMetadataDto categoryMetadataDto) {
        Optional<Category> category = categoryRepo.findById(categoryMetadataDto.getCategoryId());
        if (!category.isPresent()) {
            throw new ResourceNotFoundException(categoryMetadataDto.getCategoryId() + " category does not exist");
        }
        HashMap<String, HashSet<String>> filedIdValues = categoryMetadataDto.getFiledIdValues();
        Set<String> metadataIds = filedIdValues.keySet();
        metadataIds.forEach(id->{
            Optional<CategoryMetadataField> metadata = metadataRepo.findById(Long.parseLong(id));
            if (!metadata.isPresent()) {
                throw new ResourceNotFoundException(id + " metadata filed does not exist");
            }
        });
        metadataIds.forEach(id->{                      //checking values for every field
            if (filedIdValues.get(id).isEmpty()) {
                throw new ResourceNotFoundException("any one filed does not have values");
            }
        });
        CategoryMetadataFieldValues fieldValues = new CategoryMetadataFieldValues();
        fieldValues.setCategory(category.get());
        metadataIds.forEach(id->{
            Optional<CategoryMetadataField> metadata = metadataRepo.findById(Long.parseLong(id));
            fieldValues.setCategoryMetadataField(metadata.get());
            HashSet<String> values = filedIdValues.get(id);
            String value= String.join(",",values);
            fieldValues.setValue(value);
            metadata.get().getCategoryMetadataFieldValuesSet().add(fieldValues);
            metadataRepo.save(metadata.get());
        });
        return "Success";
    }

    public String updateCategory(CategoryMetadataDto categoryMetadataDto) {
        Optional<Category> category = categoryRepo.findById(categoryMetadataDto.getCategoryId());
        if (!category.isPresent()) {
            throw new ResourceNotFoundException(categoryMetadataDto.getCategoryId() + " category does not exist");
        }
        HashMap<String, HashSet<String>> filedIdValues = categoryMetadataDto.getFiledIdValues();
        Set<String> metadataIds = filedIdValues.keySet();
        metadataIds.forEach(id->{
            Optional<CategoryMetadataField> metadata = metadataRepo.findById(Long.parseLong(id));
            if (!metadata.isPresent()) {
                throw new ResourceNotFoundException(id + " metadata filed does not exist");
            }
            Optional<CategoryMetadataFieldValues> associationSet = valueRepo.findByMetadataId(categoryMetadataDto.getCategoryId(),Long.parseLong(id));
            if (!associationSet.isPresent()) {
                throw new ResourceNotFoundException("metadata filed is not associated with any category");
            }
            String value= String.join(",",categoryMetadataDto.getFiledIdValues().get(id));
            associationSet.get().setValue(value);
            metadata.get().getCategoryMetadataFieldValuesSet().add(associationSet.get());
            metadataRepo.save(metadata.get());
        });
        return "Success";
    }

}
