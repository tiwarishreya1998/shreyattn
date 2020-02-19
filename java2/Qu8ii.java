import java.util.Scanner;
public class Qu8ii {
    public static void main(String[] args) {
        Scanner key=new Scanner(System.in);
        String s1=key.next();

        do
        {
            if(s1.charAt(0)==s1.charAt(s1.length()-1))
                System.out.println("first and last character are same");
            else
                System.out.println("no first and last character are not same");
            s1=key.next();
        }while(!s1.equals("done"));
    }
}
