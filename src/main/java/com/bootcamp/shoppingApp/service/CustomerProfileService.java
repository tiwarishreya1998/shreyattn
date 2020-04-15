package com.bootcamp.shoppingApp.service;

import com.bootcamp.shoppingApp.Model.user.Address;
import com.bootcamp.shoppingApp.Model.user.Customer;
import com.bootcamp.shoppingApp.dto.AddressDto;
import com.bootcamp.shoppingApp.dto.CustomerProfileDto;
import com.bootcamp.shoppingApp.exceptions.InvalidContactException;
import com.bootcamp.shoppingApp.exceptions.InvalidPasswordException;
import com.bootcamp.shoppingApp.exceptions.ResourceNotFoundException;
import com.bootcamp.shoppingApp.repository.AddressRepository;
import com.bootcamp.shoppingApp.repository.CustomerRepo;
import com.bootcamp.shoppingApp.utils.SendEmail;
import com.bootcamp.shoppingApp.utils.UserEmailFromToken;
import com.bootcamp.shoppingApp.utils.ValidPassword;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class CustomerProfileService {
    @Autowired
    private UserEmailFromToken userEmailFromToken;
    @Autowired
    private CustomerRepo customerRepo;
    @Autowired
    ModelMapper modelMapper;
    @Autowired
    private SendEmail sendEmail;
    @Autowired
    private AddressRepository addressRepository;
    public Customer viewProfile(HttpServletRequest request) {
        String customerEmail = userEmailFromToken.getUserEmail(request);//request passed to get header to get email
        Customer customer = customerRepo.findByEmail(customerEmail);
        return customer;
    }

    public String updateCustomer(CustomerProfileDto customerProfileDto, HttpServletRequest request) {
        if (!(customerProfileDto.getContact() == null) && (customerProfileDto.getContact().length()!=10)) {
            throw new InvalidContactException("invalid contact");
        }
        Customer customer = customerRepo.findByEmail(userEmailFromToken.getUserEmail(request));
        try {
            if (!(customerProfileDto.getFirstName()  == null)){
                customer.setFirstName(customerProfileDto.getFirstName());
            }
            if (!(customerProfileDto.getLastName() == null)){
                customer.setLastName(customerProfileDto.getLastName());
            }
            if (!(customerProfileDto.getContact() == null)) {
                customer.setContact(customerProfileDto.getContact());
            }
            if (!(customerProfileDto.getImage() == null)) {
                //to do -> check image format then update image
            }
        } catch (NullPointerException ex) {
        }
//@DynamicUpdate
        customerRepo.save(customer);
        return "Success";
    }

    public String updatePassword(String pass,String cPass,HttpServletRequest request) {
        if (!pass.contentEquals(cPass)) {
            return "Password and confirm password does not match";
        }
        if (!ValidPassword.isValidPassword(pass)) {
            throw new InvalidPasswordException("password format invalid");
        }
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        Customer customer = customerRepo.findByEmail(userEmailFromToken.getUserEmail(request));
        customer.setPassword(passwordEncoder.encode(pass));

        customerRepo.save(customer);

        sendEmail.sendEmail("PASSWORD CHANGED","Your password changed",customer.getEmail());

        return "Success";
    }

    public String newAddress(AddressDto addressDto, HttpServletRequest request) {
        Customer customer = customerRepo.findByEmail(userEmailFromToken.getUserEmail(request));
        Address address = modelMapper.map(addressDto,Address.class);
        Set<Address> addresses = customer.getAddresses();
        addresses.add(address);
        customer.setAddresses(addresses);
        addresses.forEach(a -> {
            Address addressSave = a;
            addressSave.setUser(customer);
        });
        customerRepo.save(customer);
        return "Success";
    }

    public Set<AddressDto> viewAddress(HttpServletRequest request) {
        Customer customer = customerRepo.findByEmail(userEmailFromToken.getUserEmail(request));
        Set<Address> addresses = customer.getAddresses();
        Set<AddressDto> addressDtos = new HashSet<>();
        addresses.forEach(a -> {
            AddressDto addressDto = modelMapper.map(a,AddressDto.class);
            addressDtos.add(addressDto);
        });
        return addressDtos;
    }

    @Transactional
    public String deleteAddress(Long id,HttpServletRequest request) {
        Optional<Address> address = addressRepository.findById(id);
        if (!address.isPresent()) {
            throw  new ResourceNotFoundException("no address fount with id " + id);
        }
        addressRepository.deleteById(id);
        return "Success";
    }

    public String updateAddress(Long id,AddressDto addressDto,HttpServletRequest request) {
        Optional<Address> address = addressRepository.findById(id);
        if (!address.isPresent()) {
            throw  new ResourceNotFoundException("no address fount with id " + id);
        }
        Customer customer = customerRepo.findByEmail(userEmailFromToken.getUserEmail(request));
        Set<Address> addresses = customer.getAddresses();
        addresses.forEach(a->{
            if (a.getId() == address.get().getId()) {
                a.setAddress(addressDto.getAddress());
                a.setCity(addressDto.getCity());
                a.setCountry(addressDto.getCountry());
                a.setLabel(addressDto.getLabel());
                a.setState(addressDto.getState());
                a.setZipCode(addressDto.getZipCode());
                a.setAddress(addressDto.getAddress());
            }
        });
        customerRepo.save(customer);
        return "Success";
    }


}