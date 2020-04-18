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
public class CategoryMetaDataService {
    @Autowired
    private CategoryRepository categoryRepo;

    @Autowired
    private CategoryMetaDataFieldRepo metaDataRepo;

    @Autowired
    private CategoryMetaDataFieldValueRepo valueRepo;

    public String addCategoryMetadata(CategoryMetaDataDto categoryMetaDataDto) {
        Optional<Category> category = categoryRepo.findById(categoryMetaDataDto.getCategoryId());
        if (!category.isPresent()) {
            throw new ResourceNotFoundException(categoryMetaDataDto.getCategoryId() + " category does not exist");
        }
        HashMap<String, HashSet<String>> filedIdValues = categoryMetaDataDto.getFiledIdValues();
        Set<String> metadataIds = filedIdValues.keySet();
        metadataIds.forEach(id->{
            Optional<CategoryMetaDataField> metadata = metaDataRepo.findById(Long.parseLong(id));
            if (!metadata.isPresent()) {
                throw new ResourceNotFoundException(id + " metadata filed does not exist");
            }
        });
        metadataIds.forEach(id->{                      //checking values for every field
            if (filedIdValues.get(id).isEmpty()) {
                throw new ResourceNotFoundException("any one filed does not have values");
            }
        });
        CategoryMetaDataFieldValues fieldValues = new CategoryMetaDataFieldValues();
        fieldValues.setCategory(category.get());
        metadataIds.forEach(id->{
            Optional<CategoryMetaDataField> metadata = metaDataRepo.findById(Long.parseLong(id));
            fieldValues.setCategoryMetaDataField(metadata.get());
            HashSet<String> values = filedIdValues.get(id);
            String value= String.join(",",values);
            fieldValues.setValue(value);
            metadata.get().getCategoryMetaDataFieldValuesSet().add(fieldValues);
            metaDataRepo.save(metadata.get());
        });
        return "Success";
    }

    public String updateCategory(CategoryMetaDataDto categoryMetadataDTO) {
        Optional<Category> category = categoryRepo.findById(categoryMetadataDTO.getCategoryId());
        if (!category.isPresent()) {
            throw new ResourceNotFoundException(categoryMetadataDTO.getCategoryId() + " category does not exist");
        }
        HashMap<String, HashSet<String>> filedIdValues = categoryMetadataDTO.getFiledIdValues();
        Set<String> metadataIds = filedIdValues.keySet();
        metadataIds.forEach(id->{
            Optional<CategoryMetaDataField> metadata = metaDataRepo.findById(Long.parseLong(id));
            if (!metadata.isPresent()) {
                throw new ResourceNotFoundException(id + " metadata filed does not exist");
            }
            Optional<CategoryMetaDataFieldValues> associationSet = valueRepo.findByMetadataId(categoryMetadataDTO.getCategoryId(),Long.parseLong(id));
            if (!associationSet.isPresent()) {
                throw new ResourceNotFoundException("metadata filed is not associated with any category");
            }
            String value= String.join(",",categoryMetadataDTO.getFiledIdValues().get(id));
            associationSet.get().setValue(value);
            metadata.get().getCategoryMetaDataFieldValuesSet().add(associationSet.get());
            metaDataRepo.save(metadata.get());
        });
        return "Success";
    }

}
