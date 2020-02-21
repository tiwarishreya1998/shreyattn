import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

class Employee{
    double age,salary;
    String name;
    public Employee(double age,double salary,String name)
    {
        this.age=age;
        this.salary=salary;
        this.name=name;
    }
    public String toString()
    {
        return this.age+ " " + this.salary+" "+this.name;
    }
}
class sortbysalary implements Comparator<Employee>
{
    public int compare(Employee a,Employee b)
    {
        return (int) (a.salary-b.salary);
    }
}
class Q5{
    public static void main(String[] args) {
        ArrayList<Employee> em=new ArrayList<Employee>();
        em.add(new Employee(21,3000,"Radha"));
        em.add(new Employee(51,6000,"Neha"));
        em.add(new Employee(61,7000,"Vidya"));
        em.add(new Employee(31,3500,"Ram"));

        System.out.println("unsorted");
        for(int i=0;i<em.size();i++)
            System.out.println(em.get(i));

        Collections.sort(em,new sortbysalary());

        System.out.println("\nSorted by Salary");
        for(int i=0;i<em.size();i++)
            System.out.println(em.get(i));

    }
}