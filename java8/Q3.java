interface Mysum{
     int exercise(int a,int b);

}

 class Exercise {
    static int multiply(int a, int b){
        return a*b;}

    int add(int a,int b){
        return a+b;
    }

    int sub(int a,int b){
        return a-b;
    }
}
 public class Q3{
     public static void main(String[] args) {
         Mysum obj=Exercise::multiply;
         System.out.println(("multiplication by static method is "+obj.exercise(9,3)));

         obj=new Exercise()::add;
         System.out.println("Addition with non static method is : "+obj.exercise(3,5));

         obj=new Exercise()::sub;
         System.out.println("Addition with non static method is : "+obj.exercise(9,6));
     }

}
