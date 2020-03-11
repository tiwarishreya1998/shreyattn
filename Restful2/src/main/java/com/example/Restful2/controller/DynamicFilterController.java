package com.example.Restful2.controller;

import com.example.Restful2.model.DynamicFilterUser;
import com.example.Restful2.service.DynamicFilterService;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class DynamicFilterController {
    @Autowired
    private DynamicFilterService dynamicFilterService;

    @PostMapping(path = "/dynamicadduser")
    public MappingJacksonValue addUser(@RequestBody DynamicFilterUser user){
        DynamicFilterUser user1 = dynamicFilterService.addUser(user);

        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("username");

        FilterProvider filters = new SimpleFilterProvider().addFilter("DynamicFiltering",filter);

        MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(user1);

        mappingJacksonValue.setFilters(filters);

        return mappingJacksonValue;
    }
}
