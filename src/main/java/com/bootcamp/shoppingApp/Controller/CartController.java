package com.bootcamp.shoppingApp.Controller;

import com.bootcamp.shoppingApp.dto.CartDto;
import com.bootcamp.shoppingApp.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService service;


    @PostMapping("/addItem")
    public String addItem(@Valid @RequestBody CartDto cartDto, HttpServletResponse response, HttpServletRequest request){
        String  getMessage=service.addToCart(cartDto,request);
        if ("Success".contentEquals(getMessage)) {
            response.setStatus(HttpServletResponse.SC_CREATED);
        } else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
        return getMessage;
    }
    @GetMapping
    public List<?> getCartItem(HttpServletRequest request){
        return service.getCartProducts(request);
    }
    @PatchMapping("/incrementItem")
    public String incrementCart(@RequestBody Map<String,Long> payLoad, HttpServletRequest request,HttpServletResponse response){
        String getMessage=service.incrementItem(payLoad.get("productId"),request);
        if ("Success".contentEquals(getMessage)) {
            response.setStatus(HttpServletResponse.SC_CREATED);
        } else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
        return getMessage;
    }
    @PatchMapping("/decrementItem")
    public String decrementCart(@RequestBody Map<String,Long> payLoad, HttpServletRequest request,HttpServletResponse response){
        String getMessage=service.decrementItem(payLoad.get("productId"),request);
        if ("Success".contentEquals(getMessage)) {
            response.setStatus(HttpServletResponse.SC_CREATED);
        } else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
        return getMessage;
    }
    @DeleteMapping("/deleteItem")
    public String  deleteItem(@RequestBody Map<String,Long> payLoad, HttpServletRequest request,HttpServletResponse response ){
        String getMessage=service.deleteCartItem(payLoad.get("productId"),request);
        if ("Success".contentEquals(getMessage)) {
            response.setStatus(HttpServletResponse.SC_CREATED);
        } else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
        return getMessage;
    }

}