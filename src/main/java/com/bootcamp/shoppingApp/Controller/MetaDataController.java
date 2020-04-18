package com.bootcamp.shoppingApp.Controller;

import com.bootcamp.shoppingApp.Model.categoryPack.CategoryMetaDataField;
import com.bootcamp.shoppingApp.service.MetaDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/metadata")
public class MetaDataController {
    @Autowired
    private MetaDataService service;

    @PostMapping("/add")
    public String addMetaData(@RequestParam String name, HttpServletResponse response){
        String getMessage=service.addMetaData(name);
        if (getMessage.contains("Success")) {
            response.setStatus(HttpServletResponse.SC_CREATED);

        }
        else
        {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
        return  getMessage;
    }
    @GetMapping("/view")
    public List<CategoryMetaDataField> viewMetadata(@RequestParam(defaultValue = "0") String page, @RequestParam(defaultValue = "10") String size, @RequestParam(defaultValue = "id") String SortBy, @RequestParam(defaultValue = "ASC") String order, @RequestParam Optional<String> query) {
        return service.viewMetadata(page,size,SortBy,order,query);
    }
}
