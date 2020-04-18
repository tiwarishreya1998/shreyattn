package com.bootcamp.shoppingApp.Controller;

import com.bootcamp.shoppingApp.dto.CategoryMetaDataDto;
import com.bootcamp.shoppingApp.service.CategoryMetaDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/categoryMetadata")
public class CategoryMetadataController {

    @Autowired
    private CategoryMetaDataService categoryMetaDataService;

    @PostMapping("/add")
    public String addCategoryMetaData(@RequestBody CategoryMetaDataDto categoryMetaDataDto, HttpServletResponse response){
        String getMessage=categoryMetaDataService.addCategoryMetadata(categoryMetaDataDto);
        if ("Success".contentEquals(getMessage)) {
            response.setStatus(HttpServletResponse.SC_CREATED);
        } else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
        return getMessage;
    }

    @PostMapping("/update")
    public String updateCategoryMetaData(@RequestBody CategoryMetaDataDto categoryMetaDataDto,HttpServletResponse response){
        String getMessage=categoryMetaDataService.updateCategory(categoryMetaDataDto);
        if ("Success".contentEquals(getMessage)) {
            response.setStatus(HttpServletResponse.SC_CREATED);
        } else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
        return getMessage;
    }

}
