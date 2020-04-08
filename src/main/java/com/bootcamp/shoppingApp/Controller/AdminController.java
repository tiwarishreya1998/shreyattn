package com.bootcamp.shoppingApp.Controller;

import com.bootcamp.shoppingApp.Model.Customer;
import com.bootcamp.shoppingApp.Model.Seller;
import com.bootcamp.shoppingApp.Model.User;
import com.bootcamp.shoppingApp.Repository.CustomerRepo;
import com.bootcamp.shoppingApp.Repository.SellerRepository;
import com.bootcamp.shoppingApp.Repository.UserRepository;
import com.bootcamp.shoppingApp.Utils.SendEmail;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Optional;


@RestController
public class AdminController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SendEmail sendEmail;

    @Autowired
    private CustomerRepo customerRepo;

    @Autowired
    private SellerRepository sellerRepository;


    @PatchMapping("/admin/activate/customers/{id}")
    public String activateCustomer(@PathVariable Long id, HttpServletResponse httpServletResponse) {
        Optional<User> user = userRepository.findById(id);
        if (!user.isPresent()) {
            httpServletResponse.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return "No user of this id is present";
        }
        if (!user.get().isActive()) {
            user.get().setActive(true);
            userRepository.save(user.get());
            sendEmail.sendEmail("Activated", "Your account has been activated", user.get().getEmail());
            return "Success";
        }

        userRepository.save(user.get());
        System.out.println("Already Activated");
        return "Success";
    }

    @PatchMapping("admin/deactivate/customer/{id}")
    public String deactivateCustomer(@PathVariable Long id, HttpServletResponse httpServletResponse) {
        Optional<User> user = userRepository.findById(id);
        if (!user.isPresent()) {
            httpServletResponse.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return "No user found of this id";

        }
        if (user.get().isActive()) {
            user.get().setActive(false);
            userRepository.save(user.get());

            sendEmail.sendEmail("Deactivate Account", "your account has been Deactivated", user.get().getEmail());
            return "Success";
        }
        userRepository.save(user.get());
        System.out.println("Account is already deactivated");
        return "Success";

    }

    @PatchMapping("admin/activate/seller/{id}")
    public String activateSeller(@PathVariable Long id, HttpServletResponse httpServletResponse) {
        Optional<User> user = userRepository.findById(id);
        if (!user.isPresent()) {
            httpServletResponse.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return "No user is found of this id";
        }
        if (!user.get().isActive()) {
            user.get().setActive(true);
            userRepository.save(user.get());

            sendEmail.sendEmail("Activate seller", "Your account is activated", user.get().getEmail());
            return "Success";
        }
        userRepository.save(user.get());
        System.out.println("Already activated");
        return "Success";
    }

    @PatchMapping("admin/deactivate/seller/{id}")
    public String deactivateSeller(@PathVariable Long id,HttpServletResponse httpServletResponse){
        Optional<User> user=userRepository.findById(id);
        if(!user.isPresent()){
            httpServletResponse.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return "No user found with this id";
        }
        if (user.get().isActive()){
            user.get().setActive(false);
            userRepository.save(user.get());
            sendEmail.sendEmail("Deactivate account","Your account has been deactivated",user.get().getEmail());

            return "Success";
        }
        userRepository.save(user.get());
        System.out.println("Already deactivated");
        return "Success";
    }


    @GetMapping("admin/customer")
    public MappingJacksonValue getCustomer(@RequestParam(defaultValue = "0")String page,@RequestParam(defaultValue = "10")String size,@RequestParam(defaultValue = "id")String SortBy){
        List<Customer> customersList=customerRepo.findAll(PageRequest.of(Integer.parseInt(page),Integer.parseInt(size), Sort.by(SortBy)));
        SimpleBeanPropertyFilter filter=SimpleBeanPropertyFilter.filterOutAllExcept("id","firstName","middleName","lastName","email","active");
        FilterProvider filterProvider=new SimpleFilterProvider().addFilter("ignoreAddressInCustomer",filter);
        MappingJacksonValue mappingJacksonValue=new MappingJacksonValue(customersList);
        mappingJacksonValue.setFilters(filterProvider);

        return mappingJacksonValue;

    }

    @GetMapping("admin/seller")
    public MappingJacksonValue getSeller(@RequestParam(defaultValue = "0")String page,@RequestParam(defaultValue = "10") String size,@RequestParam(defaultValue = "id")String SortBy)
    {
        List<Seller> sellers=sellerRepository.findAll(PageRequest.of(Integer.parseInt(page),Integer.parseInt(size),Sort.by(SortBy)));
        SimpleBeanPropertyFilter filter=SimpleBeanPropertyFilter.filterOutAllExcept("id","firstName","middleName","lastName","email","active","addresses","companyName","companyContact");
        FilterProvider filterProvider=new SimpleFilterProvider().addFilter("ignoreAddressInCustomer",filter);
        MappingJacksonValue mappingJacksonValue=new MappingJacksonValue(sellers);

        return  mappingJacksonValue;

    }

}