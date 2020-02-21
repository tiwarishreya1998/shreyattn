import java.util.*;

public class Q2 {
    public static void main(String[] args) {
        Scanner sc=new Scanner(System.in);
        String s1=sc.next();
        System.out.println(s1);
        Set<Character> charSet = new HashSet<>();
        char[] c = s1.toCharArray();
        for (char c1 : c) {
            if (!charSet.add(c1)) {

            }
          else  System.out.println("unique value:"+c1);
        }
    }
}
