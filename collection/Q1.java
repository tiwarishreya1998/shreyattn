import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Q1 {
    public static void main(String[] args) {
        Float sum=0f;
        List<Float> li=  new ArrayList<Float>();
        li.add(4f);
        li.add(5f);
        li.add(13f);
        li.add(9f);
        li.add(1f);
        Iterator<Float> itr=li.iterator();

        while(itr.hasNext())
        {
             sum=sum+itr.next();
        }

        System.out.println("sum is:"+sum);
    }
}
