package com.bootcamp.shoppingApp.Controller;

import com.bootcamp.shoppingApp.dto.CategoryMetaDataDto;
import com.bootcamp.shoppingApp.service.CategoryMetadataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/categoryMetaData")
public class CategoryMetaDataController {

    @Autowired
    private CategoryMetadataService categoryMetadataService;
    public String addCategoryMetaData(@RequestBody CategoryMetaDataDto categoryMetaDataDto, HttpServletResponse response){
        String getMessage=categoryMetadataService.addCategoryMetaData(categoryMetaDataDto);
        if("Success".contentEquals(getMessage)){
            response.setStatus(HttpServletResponse.SC_CREATED);
        }
        else{
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
       return getMessage;
    }
}
