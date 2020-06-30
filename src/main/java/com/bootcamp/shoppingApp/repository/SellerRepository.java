package com.bootcamp.shoppingApp.repository;

import com.bootcamp.shoppingApp.Model.user.Seller;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface SellerRepository extends CrudRepository<Seller,Long> {

    Seller findByEmail(String email);
    List<Seller> findByGst(String gst);
    Seller findByCompanyName(String companyName);
    List<Seller> findAll(Pageable pageable);
    Seller findByCompanyContact(String contact);
}
