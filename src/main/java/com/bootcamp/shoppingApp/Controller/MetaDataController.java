package com.bootcamp.shoppingApp.Controller;

import com.bootcamp.shoppingApp.service.MetaDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/metadata")
public class MetaDataController {
    @Autowired
    private MetaDataService service;

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
}
