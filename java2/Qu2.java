
import java.util.Scanner;

public class Qu2 {
    public static void main(String[] args) {
        Scanner sc=new Scanner(System.in);
        System.out.println("Enter the string");
        String s1=sc.nextLine();
        char ch1[]=new char[s1.length()];

            ch1=s1.toCharArray();

        for(int i=0;i<ch1.length;i++)
        {
            for(int j=0;j<ch1.length;j++)
            {
               if(ch1[i]<ch1[j])
               {
                   char c=ch1[i];
                   ch1[i]=ch1[j];
                   ch1[j]=c;
               }
            }
        }
        System.out.println("sorted String is:");
        for(int i=0;i<ch1.length;i++)
        {
            System.out.print(ch1[i]);
        }
        System.out.println("\n");
    }
}
