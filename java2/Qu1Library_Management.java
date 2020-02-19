interface lib
{
    public void getDetail();
    public void setDetail();
}
abstract class Abstract
{
    abstract void setDetails();
    abstract void getDetails();
}

class Library extends Abstract
{
    public String Library_Name;
    public int Library_id;

    @Override
    void setDetails()
    {
        Library_Name="Globus Library";
        Library_id=124542;
    }

    @Override
    void getDetails()
    {
        System.out.println(Library_Name);
        System.out.println(Library_id);
    }
}
class Librarian implements lib
{
    public String Librarian_Name;
    public int Librarian_id;
    public String Librarian_address;

    @Override
    public void setDetail() {
        Librarian_Name="Ram";
        Librarian_id=1234;
        Librarian_address="Delhi";

    }
    @Override
    public void getDetail() {
        System.out.println(Librarian_Name);
        System.out.println(Librarian_id);
        System.out.println(Librarian_address);

    }
}
class user extends Abstract
{
    public String user_name;
    public int user_id;
    public String user_address;
    @Override
    public void setDetails() {
        user_name="Shyam";
        user_id=1234565;
        user_address="Ghaziabad";

    }
    @Override
    public void getDetails() {
        System.out.println(user_name);
        System.out.println(user_id);
        System.out.println(user_address);

    }
}
class Book implements lib
{
    public String Book_Name;
    public String Book_Author;
    public int Book_Id;
    @Override
    public void setDetail() {
        Book_Name="Programming with C";
        Book_Author="K.N Korth";
        Book_Id=9869;

    }
    @Override
    public void getDetail() {
        System.out.println(Book_Name);
        System.out.println(Book_Id);
        System.out.println(Book_Author);

    }
}
class Qu1Library_Management
{
    public static void main(String arg[])
    {
        Library l=new Library();
        Librarian ll=new Librarian();
        user u=new  user();
        Book b=new Book();
        System.out.println("***LIBRARY****");
        l.setDetails();
        l.getDetails();
        System.out.println("*****LIBRARIAN*****");
        ll.setDetail();
        ll.getDetail();
        System.out.println("*****USER****");
        u.setDetails();
        u.getDetails();
        System.out.println("****BOOK***");
        b.setDetail();
        b.getDetail();
    }
}
