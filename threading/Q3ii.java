class Hello
{
    String name = "";
    public int count = 0;

    public void alpha(String s, String y)
    {
        synchronized(this)
        {
            name = s;
            count++;
        }
        y=s;
    }
}

class Q3ii
{
    public static void main (String[] args)
    {
        Hello gk = new Hello();

        gk.alpha("XYZ", "ABC");
        System.out.println(gk.name);

    }
}
