package com.bootcamp.shoppingApp.service;

import com.bootcamp.shoppingApp.Model.categoryPack.CategoryMetaDataField;
import com.bootcamp.shoppingApp.exceptions.FieldAlreadyPresent;
import com.bootcamp.shoppingApp.repository.CategoryMetaDataFieldRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        return "Success"+categoryMetaDataFieldRepo.findByName(name).getId();

    }
}
