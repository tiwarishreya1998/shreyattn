interface Greaterthaninterface{
    boolean greaterthan(int a,int b);
}

interface Incrementby1{
    int incrementby1(int a);
}

interface Concatenation{
    String concatefun(String a,String b);
}
interface Uppercase{
    String upper(String a);
}
class Q1{
    public static void main(String[] args) {
        Greaterthaninterface ob1=(a,b)->{
            return a>b;
        };
        System.out.println("Greater no by comparing first to second : "+ob1.greaterthan(3,8));

        Incrementby1 ob2=(a)->{
            return a+1;
        };
        System.out.println("incrementing no : "+ob2.incrementby1(8));

        Concatenation ob3=(a,b)->{
            return a+b;
        };
        System.out.println("concat two strings : "+ob3.concatefun("hello","everyone"));

        Uppercase ob4=(a)->{
          return  a.toUpperCase();
        };
        System.out.println("changing lowercase to uppercase : "+ob4.upper("hello everyone"));
    }
}