package com.bootcamp.shoppingApp.Controller;

import com.bootcamp.shoppingApp.dto.AddressDto;
import com.bootcamp.shoppingApp.dto.CustomerProfileDto;
import com.bootcamp.shoppingApp.service.CustomerProfileService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Set;

@RestController
@RequestMapping("/customer/profile")
public class CustomerProfileController {

    @Autowired
    private CustomerProfileService customerProfileService;
    @Autowired
    ModelMapper modelMapper;

    @GetMapping("")

    public CustomerProfileDto viewProfile(HttpServletRequest request) {
        CustomerProfileDto customerProfileDTO = modelMapper.map(customerProfileService.viewProfile(request),CustomerProfileDto.class);//gettin obj of customer and is converted into customer dta... adn additionally do for image
        // check image format then set
        customerProfileDTO.setImage("image");
        return customerProfileDTO;
    }

    @PatchMapping("")
    public String updateProfile(@RequestBody CustomerProfileDto customerProfileDto, HttpServletRequest request, HttpServletResponse response) {
        String getMessage = customerProfileService.updateCustomer(customerProfileDto,request);
        if ("Success".contentEquals(getMessage)) {
            response.setStatus(HttpServletResponse.SC_CREATED);
        } else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
        return getMessage;
    }

    @PutMapping("/updatePassword")
    public String updatePassword(@RequestParam String pass,@RequestParam String cPass,HttpServletRequest request,HttpServletResponse response) {
        String getMessage = customerProfileService.updatePassword(pass,cPass,request);
        if ("Success".contentEquals(getMessage)) {
            response.setStatus(HttpServletResponse.SC_CREATED);
        } else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
        return getMessage;
    }

    @GetMapping("/addresses")
    public Set<AddressDto> viewAddresses(HttpServletRequest request) {
        return customerProfileService.viewAddress(request);
    }

    @PostMapping("/address")
    public String newAddress(@RequestBody AddressDto addressDto, HttpServletRequest request, HttpServletResponse response) {
        String getMessage = customerProfileService.newAddress(addressDto,request);
        if ("Success".contentEquals(getMessage)) {
            response.setStatus(HttpServletResponse.SC_CREATED);
        } else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
        return getMessage;
    }

    @DeleteMapping("/address/{id}")
    public String deleteAddress(@PathVariable Long id,HttpServletResponse response,HttpServletRequest request) {
        String getMessage = customerProfileService.deleteAddress(id,request);
        if ("Success".contentEquals(getMessage)) {
            response.setStatus(HttpServletResponse.SC_CREATED);
        } else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
        return getMessage;
    }

    @PutMapping("/updateAddress/{id}")
    public String updateAddress(@PathVariable Long id, @RequestBody AddressDto addressDto, HttpServletResponse response, HttpServletRequest request) {
        String getMessage = customerProfileService.updateAddress(id,addressDto,request);
        if ("Success".contentEquals(getMessage)) {
            response.setStatus(HttpServletResponse.SC_CREATED);
        } else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
        return getMessage;
    }
}