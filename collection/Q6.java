import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

class Student {
    String name;
    double score, age;
    public Student(String name,double score,double age){
        this.name=name;
        this.score=score;
        this.age=age;

    }
    public String toString(){
        return this.name+" "+this.score+" "+this.age;
    }
}

class sortbyScore implements Comparator<Student>
{
    public int compare (Student a,Student b){
        if(a.score>b.score){
            return 1;
        }
        else if(a.score<b.score)
    {
        return -1;
    }
      return a.name.compareTo(b.name);
    }

}

class Q6
{
    public static void main(String[] args) {
        ArrayList<Student> ar=new ArrayList<Student>();
        ar.add(new Student("radha",44,21));
        ar.add(new Student("priya",43,22));
        ar.add(new Student("kriti",41,22));
        ar.add(new Student("mina",43,23));

        System.out.println("Unsorted");

        for(int i=0;i<ar.size();i++)
        {
            System.out.println(ar.get(i));
        }

        Collections.sort(ar,new sortbyScore());

        System.out.println("Sorted by score and then by name");

        for(int i=0;i<ar.size();i++)
        {
            System.out.println(ar.get(i));
        }

    }
}