public class Q6 implements Myinterface3{
    @Override
    public void sum(int a, int b, int c) {
        System.out.println("This is the sum of abstract method:"+(a+b+c));
    }

    @Override
    public void sum1(int a, int b) {
        System.out.println("This is the sum of default "+(a+b));
    }

    public static void main(String[] args) {
        Q6 ques= new Q6();
        ques.sum(4,5,8);
        ques.sum1(4,9);
        Myinterface3.show();
    }

}
interface Myinterface3
{
    public void sum(int a,int b,int c);
    default void sum1(int a,int b){
        System.out.println("The sum : "+(a+b));
    }
    static void show()
    {
        System.out.println("Static Method Executed");
    }
}