public class EmployeeQ4 {
    String name;
    int age;
    String city;
    EmployeeQ4(String name,int age,String city){
        this.name=name;
        this.age=age;
        this.city=city;
    }

    protected void show(){

        System.out.println("This is employee"+" :Name-> "+name+" :City->"+age+" :City->"+city+" ");
    }

    public static void main(String[] args) {
        EmployeeQ4 emp=new EmployeeQ4("Ram",23,"Haridwar");
        emp.show();
    }
}
interface constrefinterface{
    EmployeeQ4 auto (String name,int age,String city);
}