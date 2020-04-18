package com.bootcamp.shoppingApp.Controller;

import com.bootcamp.shoppingApp.dto.CategoryMetadataDto;
import com.bootcamp.shoppingApp.service.CategoryMetadataService;
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
    private CategoryMetadataService categoryMetadataService;

    @PostMapping("/add")
    public String addCategoryMetadata(@RequestBody CategoryMetadataDto categoryMetadataDto, HttpServletResponse response){
        String getMessage=categoryMetadataService.addCategoryMetadata(categoryMetadataDto);
        if ("Success".contentEquals(getMessage)) {
            response.setStatus(HttpServletResponse.SC_CREATED);
        } else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
        return getMessage;
    }

    @PostMapping("/update")
    public String updateCategoryMetadata(@RequestBody CategoryMetadataDto categoryMetadataDto, HttpServletResponse response){
        String getMessage=categoryMetadataService.updateCategory(categoryMetadataDto);
        if ("Success".contentEquals(getMessage)) {
            response.setStatus(HttpServletResponse.SC_CREATED);
        } else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
        return getMessage;
    }

}
