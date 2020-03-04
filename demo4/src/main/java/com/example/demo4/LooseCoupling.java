//package com.example.demo4;
//
//
//interface SchoolName{
//    public void schoolLocation();
//}
//
//public class LooseCoupling {
//    public static void main(String[] args) {
//        new Schools1(new Dav2()).schoolInfo();
//        new Schools1(new Dps1()).schoolInfo();
//        new Schools1(new KV1()).schoolInfo();
//
//    }
//}
//class Schools1{
//    private SchoolName schoolName;
//
//    public Schools1(SchoolName schoolName){
//        this.schoolName=schoolName;
//    }
//
//    public void schoolInfo(){
//        schoolName.schoolLocation();
//    }
//}
//class Dav2 implements SchoolName{
//    public void schoolLocation(){
//        System.out.println("Dav School is located in Haridwar");
//
//    }
//}
//class Dps1 implements SchoolName{
//    public void schoolLocation(){
//        System.out.println("Dav School is located in Delhi");
//
//    }
//}
//class KV1 implements SchoolName{
//    public void schoolLocation(){
//        System.out.println("KV School is located in Lucknow");
//
//    }
//}
