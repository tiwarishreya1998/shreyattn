package com.bootcamp.shoppingApp.Controller;

import com.bootcamp.shoppingApp.Model.product.ProductVariation;
import com.bootcamp.shoppingApp.dto.ProductVariationDto;
import com.bootcamp.shoppingApp.service.ProductVariationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("productVariation/")
public class ProductVariationController {

    @Autowired
    ProductVariationService productVariationService;

    @PostMapping("/add")
    public String addProductVariation(@RequestBody ProductVariationDto productVariationDTO, HttpServletResponse response) {
        String getMessage = productVariationService.add(productVariationDTO);
        if ("Success".contentEquals(getMessage)) {
            response.setStatus(HttpServletResponse.SC_CREATED);
        } else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
        return getMessage;
    }

    @GetMapping("/view/{id}")
    public ProductVariation viewProduct(@PathVariable Long id, HttpServletRequest request) {
        return productVariationService.viewProduct(id, request);
    }

    @GetMapping("/view/ofProduct/{id}")
    public List<?> viewAllProduct(@PathVariable Long id, HttpServletRequest request, @RequestParam(defaultValue = "0") String page, @RequestParam(defaultValue = "10") String size, @RequestParam(defaultValue = "id") String SortBy, @RequestParam(defaultValue = "ASC") String order, @RequestParam Optional<String> query) {
        return productVariationService.viewAll(id, request, page, size, SortBy, order, query);
    }

    @PutMapping("/update")
    public String updateVariationById(@RequestBody ProductVariationDto productVariationDto, @RequestParam("isActive") Optional<Boolean> isActive, HttpServletRequest request, HttpServletResponse response) {
        String getMessage = productVariationService.updateProductVariation(request, productVariationDto, isActive);
        if ("Success".contentEquals(getMessage)) {
            response.setStatus(HttpServletResponse.SC_CREATED);
        } else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
        return getMessage;
    }
}