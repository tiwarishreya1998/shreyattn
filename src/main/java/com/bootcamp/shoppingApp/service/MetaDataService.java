package com.bootcamp.shoppingApp.service;

import com.bootcamp.shoppingApp.Model.categoryPack.CategoryMetaDataField;
import com.bootcamp.shoppingApp.exceptions.FieldAlreadyPresent;
import com.bootcamp.shoppingApp.repository.CategoryMetaDataFieldRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MetaDataService {
    @Autowired
    private CategoryMetaDataFieldRepo categoryMetaDataFieldRepo;

    public String addMetaData(String name){
        if (categoryMetaDataFieldRepo.findByName(name)!=null){
            throw new FieldAlreadyPresent(name+" already exist");
        }
        CategoryMetaDataField metaDataField=new CategoryMetaDataField();
        metaDataField.setName(name);
        categoryMetaDataFieldRepo.save(metaDataField);
        return "Success"+" "+categoryMetaDataFieldRepo.findByName(name).getId();

    }
    public List<CategoryMetaDataField> viewMetadata(String page, String size, String sortBy, String order, Optional<String> query) {
        if (query.isPresent()) {
            List<CategoryMetaDataField> categoryMetadataFields = new ArrayList<>();
            categoryMetadataFields.add(categoryMetaDataFieldRepo.findById(Long.parseLong(query.get())).get());
            return categoryMetadataFields;
        }
        List<CategoryMetaDataField> categoryMetadataFields =  categoryMetaDataFieldRepo.findAll(PageRequest.of(Integer.parseInt(page),Integer.parseInt(size), Sort.by(Sort.Direction.fromString(order),sortBy)));
        return categoryMetadataFields;
    }
}
