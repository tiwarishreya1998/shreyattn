package com.bootcamp.shoppingApp.service;

import com.bootcamp.shoppingApp.Model.orderPack.Cart;
import com.bootcamp.shoppingApp.Model.product.ProductVariation;
import com.bootcamp.shoppingApp.Model.user.Customer;
import com.bootcamp.shoppingApp.dto.CartDto;
import com.bootcamp.shoppingApp.exceptions.FieldAlreadyPresent;
import com.bootcamp.shoppingApp.exceptions.ResourceNotFoundException;
import com.bootcamp.shoppingApp.repository.CartRepo;
import com.bootcamp.shoppingApp.repository.CustomerRepo;
import com.bootcamp.shoppingApp.repository.ProductVariationRepo;
import com.bootcamp.shoppingApp.utils.UserEmailFromToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;


@Service
public class CartService {

    @Autowired
    private CustomerRepo customerRepo;

    @Autowired
    private CartRepo cartRepo;

    @Autowired
    private UserEmailFromToken userEmailFromToken;
    @Autowired
    private ProductVariationRepo productVariationRepo;


    public String addToCart(CartDto cartDto, HttpServletRequest request){
        Customer customer=customerRepo.findByEmail(userEmailFromToken.getUserEmail(request));
        Optional<ProductVariation> productVariation=productVariationRepo.findById(cartDto.getProductVariationId());
        if(!productVariation.isPresent()){
            throw new ResourceNotFoundException("PrdoductVariation not found"+cartDto.getProductVariationId());

        }

        if(cartRepo.findByVariationIdAndCustomerID(cartDto.getProductVariationId(),customer.getId()).isPresent()){
            throw  new FieldAlreadyPresent(cartDto.getProductVariationId()+" is already present");
        }
        Cart cart=new Cart();
        cart.setCustomer(customer);
        cart.setProductVariation(productVariation.get());
        cart.setQuantity(cartDto.getQuantity());
        cart.setWishListItem(cartDto.getWishListItem());
        customerRepo.save(customer);
        cartRepo.save(cart);
        return "Success";

    }
    public List<?> getCartProducts(HttpServletRequest request){
        Customer customer=customerRepo.findByEmail(userEmailFromToken.getUserEmail(request));
        List<Cart> carts=cartRepo.findByCustomer(customer);
        if(carts.isEmpty()){
            return new ArrayList<>(Collections.singleton("cart is empty"));

        }
        return carts;
    }


   @Transactional
   public String deleteCartItem(Long id,HttpServletRequest request){
        Customer customer=customerRepo.findByEmail(userEmailFromToken.getUserEmail(request));
        Optional<Cart> cart=cartRepo.findByVariationIdAndCustomerID(id,customer.getId());
        if(!cart.isPresent()){
            throw new ResourceNotFoundException("Product "+id+" doesnt exist");
        }
        cartRepo.deleteByCartId(cart.get().getId());
        return "Success";
   }

   public String incrementItem(Long id,HttpServletRequest request){
        Customer customer=customerRepo.findByEmail(userEmailFromToken.getUserEmail(request));
        Optional<Cart> cart=cartRepo.findByVariationIdAndCustomerID(id,customer.getId());
        if(!cart.isPresent()){
            throw new ResourceNotFoundException("Product "+id+"doesnot exit in cart");
        }
        cart.get().setQuantity(cart.get().getQuantity()+1);

        cartRepo.save(cart.get());
        return "Success";
   }
   @Transactional
   public String decrementItem(Long id,HttpServletRequest request){
        Customer customer=customerRepo.findByEmail(userEmailFromToken.getUserEmail(request));
       Optional<Cart> cart=cartRepo.findByVariationIdAndCustomerID(id,customer.getId());
       if(!cart.isPresent()){
           throw new ResourceNotFoundException("Product "+id+"doesnot exit in cart");
       }
       if (cart.get().getQuantity().compareTo(1)==0){
           deleteCartItem(id,request);
       }
       cart.get().setQuantity(cart.get().getQuantity()-1);
       cartRepo.save(cart.get());
       return "Success";
   }

}
