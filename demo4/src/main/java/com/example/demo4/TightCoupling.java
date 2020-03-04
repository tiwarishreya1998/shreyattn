//package com.example.demo4;
//
//public class TightCoupling {
//    public static void main(String[] args) {
//        new Schools(new Dav(),new Dps(),new KV()).schoolInfo();
//    }
//}
//class Schools{
//    private Dav dav;
//    private Dps dps;
//    private KV kv;
//    public Schools(Dav dav,Dps dps,KV kv)
//    {
//        this.dav=dav;
//        this.dps=dps;
//        this.kv=kv;
//    }
//    public void schoolInfo(){
//        dav.schoolLocation();
//        dps.schoolLocation();
//        kv.schoolLocation();
//    }
//
//}
//
//class Dav{
//    public void schoolLocation(){
//        System.out.println("Dav School is located in Haridwar");
//
//    }
//}
//class Dps{
//    public void schoolLocation(){
//        System.out.println("Dav School is located in Delhi");
//
//    }
//}
//class KV{
//    public void schoolLocation(){
//        System.out.println("KV School is located in Lucknow");
//
//    }
//}