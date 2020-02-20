class Example3
{
    synchronized public void getExample3()
    {
        for (int i = 0; i < 3; i++)
        {
            System.out.println(i);
            try
            {
                Thread.sleep(400);
            }
            catch (Exception e)
            {
                System.out.println(e);
            }
        }
    }
}

class Let extends Thread
{

    Example3 x;

    Let(Example3 y)
    {
        this.x = y;
    }

    @Override
    public void run()
    {
        x.getExample3();
    }
}

class Q3
{
    public static void main(String[] args)
    {
        Example3 obj = new Example3();

        // we are creating two threads which share
        // same Object.
        Let train1 = new Let(obj);
        Let train2 = new Let(obj);

        // both threads start executing .
        train1.start();
        train2.start();
    }
}