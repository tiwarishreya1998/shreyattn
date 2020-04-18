package com.bootcamp.shoppingApp.service;

import com.bootcamp.shoppingApp.Model.categoryPack.CategoryMetadataField;
import com.bootcamp.shoppingApp.exceptions.FieldAlreadyPresent;
import com.bootcamp.shoppingApp.repository.CategoryMetadataFieldRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MetadataService {
    @Autowired
    private CategoryMetadataFieldRepo categoryMetadataFieldRepo;

    public String addMetadata(String name){
        if (categoryMetadataFieldRepo.findByName(name)!=null){
            throw new FieldAlreadyPresent(name+" already exist");
        }
        CategoryMetadataField metadataField=new CategoryMetadataField();
        metadataField.setName(name);
        categoryMetadataFieldRepo.save(metadataField);
        return "Success"+" "+categoryMetadataFieldRepo.findByName(name).getId();

    }
    public List<CategoryMetadataField> viewMetadata(String page, String size, String sortBy, String order, Optional<String> query) {
        if (query.isPresent()) {
            List<CategoryMetadataField> categoryMetadataFields = new ArrayList<>();
            categoryMetadataFields.add(categoryMetadataFieldRepo.findById(Long.parseLong(query.get())).get());
            return categoryMetadataFields;
        }
        List<CategoryMetadataField> categoryMetadataFields =  categoryMetadataFieldRepo.findAll(PageRequest.of(Integer.parseInt(page),Integer.parseInt(size), Sort.by(Sort.Direction.fromString(order),sortBy)));
        return categoryMetadataFields;
    }
}
