import java.util.*;

public class Q7 {
    public static void main(String[] args) {
        int[] arr = new int[]{1,1,5,9,5,3,2,5,2,7,7};
        LinkedHashMap<Integer, Integer> hashMap = new LinkedHashMap<>();
        for (int i=0;i<arr.length;i++) {
            if (hashMap.containsKey(arr[i])){
                hashMap.put(arr[i],hashMap.get(arr[i])+1);
            } else {
                hashMap.put(arr[i],1);
            }
        }
        List<Map.Entry<Integer,Integer>> list = new LinkedList<Map.Entry<Integer, Integer>>(hashMap.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<Integer, Integer>>() {
            @Override
            public int compare(Map.Entry<Integer, Integer> o1, Map.Entry<Integer, Integer> o2) {
                if (o1.getValue() > o2.getValue()) {
                    return -1;
                }
                if (o2.getValue() > o1.getValue()) {
                    return 1;
                }
                return 0;
            }
        });

        LinkedHashMap<Integer, Integer> map = new LinkedHashMap<>();
        for(Map.Entry<Integer,Integer> entry:list) {
            map.put(entry.getKey(),entry.getValue());
        }

        for(Map.Entry<Integer,Integer> entry: map.entrySet()) {
            System.out.println("Value: "+entry.getKey() + " Frequency: " + entry.getValue());
        }
    }
}