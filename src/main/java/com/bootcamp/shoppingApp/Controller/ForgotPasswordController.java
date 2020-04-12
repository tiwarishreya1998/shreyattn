package com.bootcamp.shoppingApp.Controller;

import com.bootcamp.shoppingApp.service.ForgotPasswordTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@RestController
public class ForgotPasswordController {
    @Autowired
    private TokenStore tokenStore;

    @Autowired
    private ForgotPasswordTokenService forgotPasswordTokenService;

    @PostMapping("/token/{email}")
    public String getToken(@PathVariable String email, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse){

        String getMessage=forgotPasswordTokenService.sendToken(email);
        System.out.println(getMessage);
        if(getMessage.equals("Success")){
        String authHeader=httpServletRequest.getHeader("Authorization");
        if (authHeader!=null){

            String tokenValue=authHeader.replace("Bearer","").trim();
            OAuth2AccessToken accessToken=tokenStore.readAccessToken(tokenValue);
            tokenStore.removeAccessToken(accessToken);
        }

        httpServletResponse.setStatus(HttpServletResponse.SC_CREATED);}
        else {
            httpServletResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
        return getMessage;
    }

    @PatchMapping("resetPassword")
    public String resetPassword(@RequestParam String email,@RequestParam String token,@RequestParam String pass,@RequestParam String cPass,HttpServletResponse httpServletResponse){
        String getMessage= forgotPasswordTokenService.resetPassword(email,token,pass,cPass);

        if(getMessage.equals("Success")){
            httpServletResponse.setStatus(HttpServletResponse.SC_CREATED);
        }
        else{
            httpServletResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);

        }
        return getMessage;

    }
}

