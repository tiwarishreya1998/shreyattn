import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Q3 {
    public static void main(String[] args) {
        Scanner sb=new Scanner(System.in);
        String ss=sb.next();
        HashMap<Character,Integer> h=new HashMap<Character, Integer>();
        char ch[]=ss.toCharArray();
        for(char c: ch)
        {
            if(h.containsKey(c))
            {
                h.put(c, h.get(c)+1);
            }
            else
                h.put(c,1);

        }
        System.out.println(ss+":"+h);
    }
}
