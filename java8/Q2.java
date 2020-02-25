public class Q2 {
    public static void main(String[] args) {
        Ques2 ob1=(a,b)->{
            return b;
        };
        System.out.println("returning one value : "+ob1.method1(9,3));
    }
}
interface Ques2{
    public int method1(int a,int b);
}