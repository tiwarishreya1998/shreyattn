package com.bootcamp.shoppingApp.service;

import com.bootcamp.shoppingApp.Model.user.Address;
import com.bootcamp.shoppingApp.Model.user.Seller;
import com.bootcamp.shoppingApp.dto.SellerAddressDto;
import com.bootcamp.shoppingApp.dto.SellerProfileDto;
import com.bootcamp.shoppingApp.exceptions.InvalidContactException;
import com.bootcamp.shoppingApp.exceptions.InvalidGstException;
import com.bootcamp.shoppingApp.exceptions.InvalidPasswordException;
import com.bootcamp.shoppingApp.exceptions.ResourceNotFoundException;
import com.bootcamp.shoppingApp.repository.AddressRepository;
import com.bootcamp.shoppingApp.repository.SellerRepository;
import com.bootcamp.shoppingApp.utils.SendEmail;
import com.bootcamp.shoppingApp.utils.UserEmailFromToken;
import com.bootcamp.shoppingApp.utils.ValidGst;
import com.bootcamp.shoppingApp.utils.ValidPassword;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;
import java.util.Set;

@Service
public class SellerProfileService {
    @Autowired
    private UserEmailFromToken userEmailFromToken;
    @Autowired
    private SellerRepository sellerRepository;
    @Autowired
    ModelMapper modelMapper;
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private SendEmail sendEmail;

    public SellerProfileDto viewProfile(HttpServletRequest request){
        String sellerEmail=userEmailFromToken.getUserEmail(request);
        Seller seller=sellerRepository.findByEmail(sellerEmail);
        SellerProfileDto sellerProfileDto=modelMapper.map(seller,SellerProfileDto.class);
        sellerProfileDto.setImage("image");
        Set<Address> addresses=seller.getAddresses();
        SellerAddressDto sellerAddressDto=modelMapper.map(addresses.stream().findFirst().get(),SellerAddressDto.class);
        sellerProfileDto.setAddress(sellerAddressDto);
        return sellerProfileDto;
    }
    public String updateSeller(SellerProfileDto sellerProfileDto,HttpServletRequest request) {
        if ((sellerProfileDto.getCompanyContact() != null) && sellerProfileDto.getCompanyContact().length()!=10) {
            throw new InvalidContactException("invalid contact");
        }
        if ((sellerProfileDto.getGst() != null) && (ValidGst.checkGstValid(sellerProfileDto.getGst())!=true)) {
            throw new InvalidGstException("gst format is invalid");
        }
        Seller seller = sellerRepository.findByEmail(userEmailFromToken.getUserEmail(request));
        try {
            if (sellerProfileDto.getFirstName() != null) {
                seller.setFirstName(sellerProfileDto.getFirstName());
            }
            if (sellerProfileDto.getLastName() != null) {
                seller.setLastName(sellerProfileDto.getLastName());
            }
            if (sellerProfileDto.getCompanyContact() != null) {
                Seller anotherLocalSeller = sellerRepository.findByCompanyContact(sellerProfileDto.getCompanyContact());
                try {
                    if (anotherLocalSeller.getCompanyContact().equalsIgnoreCase(sellerProfileDto.getCompanyContact())) {
                        return "company contact should be unique";
                    }
                } catch (NullPointerException ex) {

                }
                seller.setCompanyContact(sellerProfileDto.getCompanyContact());
            }
            if (sellerProfileDto.getCompanyName() != null) {
                Seller anotherLocalSeller = sellerRepository.findByCompanyName(sellerProfileDto.getCompanyName());
                try {
                    if (anotherLocalSeller.getCompanyName().equalsIgnoreCase(sellerProfileDto.getCompanyName())) {
                        return "company name should be unique";
                    }
                } catch (NullPointerException ex) {

                }
                seller.setCompanyName(sellerProfileDto.getCompanyName());
            }
            if (sellerProfileDto.getGst() != null) {
                seller.setGst(sellerProfileDto.getGst());
            }
            if (sellerProfileDto.getImage() != null) {
                // check image format and then update
            }
        } catch (NullPointerException ex) {}

        sellerRepository.save(seller);
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
        Seller seller = sellerRepository.findByEmail(userEmailFromToken.getUserEmail(request));
        seller.setPassword(passwordEncoder.encode(pass));

        sellerRepository.save(seller);

        sendEmail.sendEmail("PASSWORD CHANGED","Your password changed",seller.getEmail());

        return "Success";
    }

    public String updateAddress(Long id, SellerAddressDto addressDto, HttpServletRequest request) {
        Optional<Address> address = addressRepository.findById(id);
        if (!address.isPresent()) {
            throw  new ResourceNotFoundException("no address fount with id " + id);
        }
        Seller seller = sellerRepository.findByEmail(userEmailFromToken.getUserEmail(request));
        Set<Address> addresses = seller.getAddresses();
        addresses.forEach(a->{
            if (a.getId().equals(address.get().getId())){
                a.setAddress(addressDto.getAddress());
                a.setCity(addressDto.getCity());
                a.setCountry(addressDto.getCountry());
                a.setState(addressDto.getState());
                a.setZipCode(addressDto.getZipCode());
                a.setAddress(addressDto.getAddress());
            }
        });
        sellerRepository.save(seller);
        return "Success";
    }

}
