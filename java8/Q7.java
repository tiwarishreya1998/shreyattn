public class Q7 implements Myinterface1 {
    public void sum(int x,int y){
        System.out.println("sum here in abstract overidden method is : "+(x+y));
    }

    @Override
    public void method(int a, int b, int c) {
        int mul=a*b*c;
        System.out.println("multiplication : "+mul);
        Myinterface1.super.method(4,7,8);

    }

    public static void main(String[] args) {
        Q7 ques=new Q7();
        ques.method(2,3,4);
        ques.sum(6,9);

    }

}
interface Myinterface1{
    public void sum(int a, int b);
    default void method(int a,int b,int c){
        System.out.println("sum is : "+(a+b+c));
    }
}