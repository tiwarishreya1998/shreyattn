public class Qu6
{
    public static void main(String[] args)
    {
        try
        {
            int a=5,b=0;
            int c=a/b;
        }
        catch(NullPointerException n)
        {
            System.out.println("exception is"+n);
        }
        catch(IllegalArgumentException p)
        {
            System.out.println("exception is"+p);
        }
        catch(ArithmeticException a)
        {
            System.out.println("exception is"+a);
        }
        catch(Exception e)
        {
            System.out.println("exception is there"+e);
        }
        finally {
            System.out.println("This statement will definitely execute");
        }
    }
}
