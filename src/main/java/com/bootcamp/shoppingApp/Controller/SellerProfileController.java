package com.bootcamp.shoppingApp.Controller;

import com.bootcamp.shoppingApp.dto.CategoryDto;
import com.bootcamp.shoppingApp.dto.SellerAddressDto;
import com.bootcamp.shoppingApp.dto.SellerProfileDto;
import com.bootcamp.shoppingApp.service.CategoryService;
import com.bootcamp.shoppingApp.service.SellerProfileService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("/seller/profile")
public class SellerProfileController {
    @Autowired
    SellerProfileService sellerProfileService;

    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private CategoryService categoryService;

    @GetMapping("")
    public SellerProfileDto viewProfile(HttpServletRequest request) {
        return sellerProfileService.viewProfile(request);
    }

    @PatchMapping("")
    public String updateProfile(@RequestBody SellerProfileDto sellerProfileDto, HttpServletRequest request, HttpServletResponse response) {
        String getMessage = sellerProfileService.updateSeller(sellerProfileDto, request);
        if ("Success".contentEquals(getMessage)) {
            response.setStatus(HttpServletResponse.SC_CREATED);
        } else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
        return getMessage;
    }

    @PutMapping("/updatePassword")
    public String updatePassword(@RequestParam String pass, @RequestParam String cPass, HttpServletRequest request, HttpServletResponse response) {
        String getMessage = sellerProfileService.updatePassword(pass, cPass, request);
        if ("Success".contentEquals(getMessage)) {
            response.setStatus(HttpServletResponse.SC_CREATED);
        } else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
        return getMessage;
    }

    @PutMapping("/updateAddress/{id}")
    public String updateAddress(@PathVariable Long id, @RequestBody SellerAddressDto sellerAddressDto, HttpServletResponse response, HttpServletRequest request) {
        String getMessage = sellerProfileService.updateAddress(id, sellerAddressDto, request);
        if ("Success".contentEquals(getMessage)) {
            response.setStatus(HttpServletResponse.SC_CREATED);
        } else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
        return getMessage;
    }

    @GetMapping("/categories")
    public List<CategoryDto> viewLeafCategories() {
        return categoryService.viewLeafCategories();
    }
}