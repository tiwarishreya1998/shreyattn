public class Q8 implements Myinterface {

    public void sum(int a,int b)
    {
        System.out.println("sum is : "+(a+b));
    }
    public static void main(String[] args) {
        Q8 ques=new Q8();
        ques.sum(1,7);
        ques.def();

    }
}
interface Myinterface{
    public void sum(int a,int b);
    default void def()
    {
        System.out.println("this is a default method");
    }
}