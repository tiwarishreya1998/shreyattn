package com.bootcamp.shoppingApp.service;

import com.bootcamp.shoppingApp.Model.categoryPack.Category;
import com.bootcamp.shoppingApp.Model.product.Product;
import com.bootcamp.shoppingApp.Model.user.Seller;
import com.bootcamp.shoppingApp.exceptions.FieldAlreadyPresent;
import com.bootcamp.shoppingApp.exceptions.ResourceNotFoundException;
import com.bootcamp.shoppingApp.repository.CategoryRepository;
import com.bootcamp.shoppingApp.repository.ProductRepository;
import com.bootcamp.shoppingApp.repository.SellerRepository;
import com.bootcamp.shoppingApp.utils.SendEmail;
import com.bootcamp.shoppingApp.utils.UserEmailFromToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private UserEmailFromToken userEmailFromToken;

    @Autowired
    private SellerRepository sellerRepository;

    @Autowired
    private SendEmail sendEmail;

    public String addProduct(HttpServletRequest request, String name, String brand, Long categoryId, Optional<String> desc,Optional<Boolean> isCancellable,Optional<Boolean> isReturnable ){
       //checking for the leaf category
        List<Object>parentIds=categoryRepository.findLeafCategories();
        List<Object>leafCategoryIds=categoryRepository.findCategoryId();
        leafCategoryIds.removeAll(parentIds);
        Set<Long> leaf=new HashSet<>();
        leafCategoryIds.forEach(i->
        {
            leaf.add(Long.parseLong(i.toString()));    //changing object to long..
        });
        if (!(leaf.contains(categoryId))){
            throw new ResourceNotFoundException(categoryId+"not a leaf category");
        }
        Seller seller=sellerRepository.findByEmail(userEmailFromToken.getUserEmail(request));
        Optional<Product> checkUniqueName=productRepository.checkUniqueProductName(brand,name,seller.getId(),categoryId);
        if (checkUniqueName.isPresent()){
            throw new FieldAlreadyPresent(name + " product already exist");
        }
        Product product = new Product();
        Optional<Category> category = categoryRepository.findById(categoryId);
        product.setName(name);
        product.setBrand(brand);
        product.setActive(false);
        product.setDeleted(false);
        product.setCategory(category.get());
        product.setSeller(seller);
        if (desc.isPresent()) {
            product.setDescription(desc.get());
        }
        if (isCancellable.isPresent()) {
            product.setCancellable(isCancellable.get());
        }
        if (!isCancellable.isPresent()) {
            product.setCancellable(true);
        }
        if (isReturnable.isPresent()) {
            product.setReturnable(isReturnable.get());
        }
        if (!isReturnable.isPresent()) {
            product.setReturnable(true);
        }
        seller.getProducts().add(product);
        category.get().getProducts().add(product);

        productRepository.save(product);
        categoryRepository.save(category.get());
        sellerRepository.save(seller);
        sendEmail.sendEmail("ACTIVATE ADDED PRODUCT",name+" " +categoryId+" "+brand,"shreya@admin.com");

        return "Success";
    }

}
