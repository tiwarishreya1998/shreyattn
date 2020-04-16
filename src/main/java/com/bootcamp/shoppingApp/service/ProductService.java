package com.bootcamp.shoppingApp.service;

import com.bootcamp.shoppingApp.Model.categoryPack.Category;
import com.bootcamp.shoppingApp.Model.product.Product;
import com.bootcamp.shoppingApp.Model.user.Seller;
import com.bootcamp.shoppingApp.dto.ProductDto;
import com.bootcamp.shoppingApp.dto.ProductUpdateDto;
import com.bootcamp.shoppingApp.exceptions.FieldAlreadyPresent;
import com.bootcamp.shoppingApp.exceptions.ProductNotActive;
import com.bootcamp.shoppingApp.exceptions.ResourceNotFoundException;
import com.bootcamp.shoppingApp.repository.CategoryRepository;
import com.bootcamp.shoppingApp.repository.ProductRepository;
import com.bootcamp.shoppingApp.repository.ProductVariationRepo;
import com.bootcamp.shoppingApp.repository.SellerRepository;
import com.bootcamp.shoppingApp.utils.UserEmailFromToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private SellerRepository sellerRepository;
    @Autowired
    private UserEmailFromToken userEmailFromToken;
    @Autowired
    private ProductVariationRepo productVariationRepo;


    public String addProduct(ProductDto productDto, HttpServletRequest request) {
        Optional<Category> category = categoryRepository.findById(productDto.getCategoryId());
        if (!category.isPresent()) {
            throw new ResourceNotFoundException(productDto.getCategoryId() + " category does not exist");
        }

        Seller seller = sellerRepository.findByEmail(userEmailFromToken.getUserEmail(request));

        Category category1 = category.get();
        Product product = productRepository.findUniqueName(seller.getId(), productDto.getName(), productDto.getBrand(), productDto.getCategoryId());
        if (product != null) {
            throw new FieldAlreadyPresent(productDto.getName() + "is not unique");
        }
        product.setName(productDto.getName());
        product.setBrand(productDto.getBrand());
        product.setDescription(productDto.getDescription());
        product.setCategory(category1);
        product.setSeller(seller);
        productRepository.save(product);

        return "Success";
    }

    public Product viewProduct(Long id) {
        Optional<Product> productOptional = productRepository.findById(id);
        if (!productOptional.isPresent()) {
            throw new ResourceNotFoundException(id + " this id is not found");
        }
        Product product = productOptional.get();
        if (!product.getActive()) {
            throw new ProductNotActive("this product is not active");
        }
        return product;
    }

    public String activateProduct(Long id) {
        Optional<Product> productOptional = productRepository.findById(id);
        if (!productOptional.isPresent()) {
            throw new ResourceNotFoundException("product not found with id: " + id);
        }
        Product product = productOptional.get();
        product.setActive(true);
        productRepository.save(product);
        return "Success";
    }

    public String deactivateProduct(Long id) {
        Optional<Product> productOptional = productRepository.findById(id);
        if (!productOptional.isPresent()) {
            throw new ResourceNotFoundException("product not found with id: " + id);
        }
        Product product = productOptional.get();
        product.setActive(false);
        productRepository.save(product);
        return "Success";
    }

    public String deleteProduct(Long id, HttpServletRequest request) {
        Optional<Product> productOptional = productRepository.findById(id);
        if (!productOptional.isPresent()) {
            throw new ResourceNotFoundException(id + " this id is not found");
        }
        Product product = productOptional.get();
        Seller seller = sellerRepository.findByEmail(userEmailFromToken.getUserEmail(request));
        if (product.getSeller() != seller) {
            throw new ResourceNotFoundException("product not found with id: " + id);
        }
        if (productVariationRepo.findByProductId(id, PageRequest.of(0, 10, Sort.Direction.ASC)) != null) {
            productVariationRepo.deleteByProductId(product.getId());
            productRepository.deleteByProductId(id);
        }
        productRepository.deleteByProductId(id);
        return "Success";
    }

    public String updateProduct(Long id, ProductUpdateDto updateDto, HttpServletRequest request) {
        Optional<Product> productOptional = productRepository.findById(id);
        if (!productOptional.isPresent()) {
            throw new ResourceNotFoundException("product not found with id: " + id);
        }
        Product product = productOptional.get();
        Seller seller = product.getSeller();

        Seller localSeller = sellerRepository.findByEmail(userEmailFromToken.getUserEmail(request));
        if (!localSeller.equals(seller)) {
            throw new ResourceNotFoundException(id + " this id is not found");
        }
        if (updateDto.getName() != null) {
            Product product1 = productRepository.findUniqueName(seller.getId(), updateDto.getName(), product.getBrand(), product.getCategory().getId());
            if (product1 != null) {
                throw new FieldAlreadyPresent("name is not unique");

            }
            product.setName(updateDto.getName());
        }
        if (updateDto.getDescription() != null) {
            product.setDescription(updateDto.getDescription());
        }
        if (updateDto.getCancellable() != null) {
            product.setCancellable(updateDto.getCancellable());
        }
        if (updateDto.getReturnable() != null) {
            product.setReturnable(updateDto.getReturnable());
        }
        productRepository.save(product);

        return "Success";
    }
}
