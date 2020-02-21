import com.sun.jdi.Value;

import java.util.*;
import java.util.Map.Entry;

public class Q4 {
    public static void main(String[] args) {
        HashMap<String, Integer> h = new HashMap<String, Integer>();
        h.put("Shreya", 22);
        h.put("Ananya", 33);
        h.put("Divya", 43);
        h.put("Kashish", 61);
        h.put("Aditi", 34);
        h.put("Nidhi", 84);
        Map<String, Integer> h2 = sorting(h);

        for(Map.Entry<String,Integer> i: h2.entrySet())
        {
            System.out.println("key="+i.getKey()+"    "+"Value="+i.getValue());
        }

    }


    public static HashMap<String, Integer> sorting(HashMap<String, Integer> h) {
        //Creating a list from a hashmap to sort this by value
        List<Map.Entry<String, Integer>> l = new LinkedList<Map.Entry<String, Integer>>(h.entrySet());
        Collections.sort(l, new Comparator<Entry<String, Integer>>() {
            @Override
            public int compare(Map.Entry<String, Integer> t2, Map.Entry<String, Integer> t1) {
                return (t1.getValue()).compareTo(t2.getValue());
            }
        });
     HashMap<String,Integer> t=new LinkedHashMap<String,Integer>();
     for(Map.Entry<String,Integer> i: l)
     {
         t.put(i.getKey(),i.getValue());
     }
     return t;
    }
}