package com.bootcamp.shoppingApp.Utils;

import org.springframework.stereotype.Service;



public class ValidGst {
    public static boolean checkGstValid(String gst){
        if(gst.length()!=15){
            return false;

        }
        if (gst.charAt(13)!='Z'){
            return false;

        }
        return true;
    }
}
