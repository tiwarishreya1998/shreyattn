package com.bootcamp.shoppingApp.Controller;

import com.bootcamp.shoppingApp.Model.product.Product;
import com.bootcamp.shoppingApp.Model.product.ProductVariation;
import com.bootcamp.shoppingApp.dto.ProductDto;
import com.bootcamp.shoppingApp.dto.ProductUpdateDto;
import com.bootcamp.shoppingApp.repository.ProductRepository;
import com.bootcamp.shoppingApp.repository.ProductVariationRepo;
import com.bootcamp.shoppingApp.service.ProductService;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductVariationRepo productVariationRepo;
    @Autowired
    private ProductService productService;


    @PostMapping("/add")
    public String addProduct(@RequestBody ProductDto productDto,HttpServletRequest request,HttpServletResponse response){
        String getMessage=productService.addProduct(productDto,request);
        if ("Success".contentEquals(getMessage)) {
            response.setStatus(HttpServletResponse.SC_CREATED);
        } else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
        return "Success";
    }

    @DeleteMapping("/delete/{id}")
    public String deleteProduct(@PathVariable Long id, HttpServletResponse response, HttpServletRequest request){
        String getMessage=productService.deleteProduct(id,request);
        if ("Success".contentEquals(getMessage)) {
            response.setStatus(HttpServletResponse.SC_CREATED);
        } else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
        return "Success";
    }
    @PutMapping("/update/{id}")
    public String updateProduct(@PathVariable Long id, ProductUpdateDto productUpdateDto,HttpServletResponse response,HttpServletRequest request){
        String getMessage=productService.updateProduct(id,productUpdateDto,request);
        if ("Success".contentEquals(getMessage)) {
            response.setStatus(HttpServletResponse.SC_CREATED);
        } else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
        return "Success";
    }

    @PatchMapping("/deactivate/{id}")
    public String deactivateProduct(@PathVariable Long id,HttpServletResponse response){
        String getMessage=productService.deactivateProduct(id);
        if ("Success".contentEquals(getMessage)) {
            response.setStatus(HttpServletResponse.SC_CREATED);
        } else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
        return "Success";
    }

    @PatchMapping("/activate/{id}")
    public String activateProduct(@PathVariable Long id,HttpServletResponse response){
        String getMessage=productService.activateProduct(id);
        if ("Success".contentEquals(getMessage)) {
            response.setStatus(HttpServletResponse.SC_CREATED);
        } else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
        return "Success";
    }



    @GetMapping("")
    public MappingJacksonValue getProduct(@RequestParam(defaultValue = "0")String page,@RequestParam(defaultValue = "10") String size,@RequestParam(defaultValue = "id")String SortBy){
        List<Product> productList=productRepository.findAll(PageRequest.of(Integer.parseInt(page),Integer.parseInt(size), Sort.by(SortBy)));
        SimpleBeanPropertyFilter filter=SimpleBeanPropertyFilter.filterOutAllExcept("id","name","brand","description","isActive");
        FilterProvider filterProvider=new SimpleFilterProvider().addFilter("ignoreCancelAndReturn",filter);
        MappingJacksonValue mappingJacksonValue=new MappingJacksonValue(productList);
        mappingJacksonValue.setFilters(filterProvider);
        return  mappingJacksonValue;
    }
    @GetMapping("/{id}")
    public MappingJacksonValue getProduct(@PathVariable Long id, @RequestParam(defaultValue = "0")String page, @RequestParam(defaultValue = "10") String size, @RequestParam(defaultValue = "id")String SortBy){
        List<ProductVariation> productVariation=productVariationRepo.findByProductId(id,PageRequest.of(Integer.parseInt(page),Integer.parseInt(size),Sort.by(SortBy)));
        SimpleBeanPropertyFilter filter=SimpleBeanPropertyFilter.filterOutAllExcept("id","price","quantity","metaData");
        FilterProvider filterProvider=new SimpleFilterProvider().addFilter("ignore",filter);
        MappingJacksonValue mappingJacksonValue=new MappingJacksonValue(productVariation);
        mappingJacksonValue.setFilters(filterProvider);
        return  mappingJacksonValue;
    }

}

