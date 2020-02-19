public class Qu4
{
    static Qu4 s=null;
    String str;
    private Qu4()
    {
        str="The Taj Mahal is in agra";
    }
    public static Qu4 getInstance()
    {
        if(s==null)
        {
            s=new Qu4();
        }
        return s;
    }
    public static void main(String arg[])
    {
        Qu4 x=Qu4.getInstance();
        Qu4 y=Qu4.getInstance();

        x.str=(x.str).toUpperCase();
        System.out.println(x.str);
        System.out.println(y.str);


        y.str=(x.str).toLowerCase();

        System.out.println(x.str);
        System.out.println(y.str);

    }
}
