package com.bootcamp.shoppingApp.service;

import com.bootcamp.shoppingApp.Model.categoryPack.Category;
import com.bootcamp.shoppingApp.Model.product.Product;
import com.bootcamp.shoppingApp.Model.user.Seller;
import com.bootcamp.shoppingApp.dto.ProductDto;
import com.bootcamp.shoppingApp.exceptions.FieldAlreadyPresent;
import com.bootcamp.shoppingApp.exceptions.ResourceNotFoundException;
import com.bootcamp.shoppingApp.repository.CategoryRepository;
import com.bootcamp.shoppingApp.repository.ProductRepository;
import com.bootcamp.shoppingApp.repository.SellerRepository;
import com.bootcamp.shoppingApp.utils.SendEmail;
import com.bootcamp.shoppingApp.utils.UserEmailFromToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

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
    public String activateProduct(Long productId) {
        Optional<Product> product = productRepository.findById(productId);
        if (!product.isPresent()) {
            throw new ResourceNotFoundException(productId+" not found");
        }
        if (product.get().getActive()) {
            throw new ResourceNotFoundException(productId+" already active");
        }
        product.get().setActive(true);
        sendEmail.sendEmail("PRODUCT ACTIVATED",productId+" product activated",product.get().getSeller().getEmail());
        productRepository.save(product.get());
        return "Success";
    }

    public String deactivateProduct(Long productId) {
        Optional<Product> product = productRepository.findById(productId);
        if (!product.isPresent()) {
            throw new ResourceNotFoundException(productId+" not found");
        }
        if (!product.get().getActive()) {
            throw new ResourceNotFoundException(productId+" already de-active");
        }
        product.get().setActive(false);
        sendEmail.sendEmail("PRODUCT DE-ACTIVATED",productId+" product deactivated",product.get().getSeller().getEmail());
        productRepository.save(product.get());
        return "Success";
    }
    public ProductDto viewProduct(Long id, HttpServletRequest request) {
        String userEmail = userEmailFromToken.getUserEmail(request);
        Optional<Product> product = productRepository.findById(id);
        if (!product.isPresent()) {
            throw new ResourceNotFoundException(id+" product not found");
        }
        Seller seller = sellerRepository.findByEmail(userEmail);
        if (product.get().getSeller().getId() != seller.getId()) {
            throw new  ResourceNotFoundException("invalid seller");
        }
        if (product.get().getDeleted()) {
            throw new ResourceNotFoundException(id+" product is deleted");
        }

        ProductDto productDto = new ProductDto();
        productDto.setActive(product.get().getActive());
        productDto.setBrand(product.get().getBrand());
        productDto.setCancellable(product.get().getCancellable());
        productDto.setCategory(product.get().getCategory());
        productDto.setDescription(product.get().getDescription());
        productDto.setName(product.get().getName());
        productDto.setReturnable(product.get().getReturnable());
        productDto.setSeller(product.get().getSeller().getId());
        productDto.setId(id);

        return productDto;
    }
    public List<?> viewAllProducts(HttpServletRequest request,String page, String size, String sortBy, String order, Optional<String> query) {
        if (query.isPresent()) {
            ProductDto productDto =viewProduct(Long.parseLong(query.get()),request);
            List<ProductDto> productDtos = new ArrayList<>();
            productDtos.add(productDto);
            return productDtos;
        }
        String sellerEmail = userEmailFromToken.getUserEmail(request);
        Seller seller = sellerRepository.findByEmail(sellerEmail);
        List<Product> products = productRepository.productsOfSeller(seller.getId(), PageRequest.of(Integer.parseInt(page),Integer.parseInt(size), Sort.by(Sort.Direction.fromString(order),sortBy)));
        return products;
    }
    public String updateProductById(HttpServletRequest request, Long id, Optional<String> name, Optional<String> desc, Optional<Boolean> isCancellable, Optional<Boolean> isReturnable) {

        String userEmail = userEmailFromToken.getUserEmail(request);
        Optional<Product> product = productRepository.findById(id);
        if (!product.isPresent()) {
            throw new ResourceNotFoundException(id+" product not found");
        }
        Seller seller = sellerRepository.findByEmail(userEmail);
        if (product.get().getSeller().getId() != seller.getId()) {
            throw new  ResourceNotFoundException("invalid seller");
        }
       System.out.println(product.get().getBrand()+name.get()+seller.getId()+product.get().getCategory());
        if (name.isPresent()) {
            Optional<Product> checkUniqueName = productRepository.checkUniqueProductName(product.get().getBrand(),name.get(),seller.getId(),product.get().getCategory().getId());
            if (checkUniqueName.isPresent()) {
                throw new FieldAlreadyPresent(name.get() + " product already exist");
            }
            product.get().setName(name.get());
        }
        if (desc.isPresent()) {
            product.get().setDescription(desc.get());
        }
        if (isCancellable.isPresent()) {
            product.get().setCancellable(isCancellable.get());
        }
        if (isReturnable.isPresent()) {
            product.get().setReturnable(isReturnable.get());
        }
        productRepository.save(product.get());
        return "Success";
    }

    public String deleteProductById(Long id, HttpServletRequest request) {
        String userEmail = userEmailFromToken.getUserEmail(request);
        Optional<Product> product = productRepository.findById(id);
        if (!product.isPresent()) {
            throw new ResourceNotFoundException(id+" product not found");
        }
        Seller seller = sellerRepository.findByEmail(userEmail);
        if (product.get().getSeller().getId() != seller.getId()) {
            throw new  ResourceNotFoundException("invalid seller");
        }
        product.get().setDeleted(true);
        productRepository.save(product.get());
        return "Success";
    }


}
