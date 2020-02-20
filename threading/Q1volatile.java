
import java.util.Scanner;

class Myvolatile extends Thread
{
    private volatile boolean status=true;
    public void run()
    {
        while(status)
        {
            System.out.println("The thread is running");
            try {
                Thread.sleep(1500);
            } catch (InterruptedException p) {
                p.printStackTrace();
            }
        }
    }

    public void Newstatus()
    {
        status=false;
    }
}
public class Q1volatile {

    //volatile keyword allows to multiple thread to modify value from the main memory instead of dedicated cache memory.

    public static void main(String[] args) {
        //Here I am starting the thread
        Myvolatile mt=new Myvolatile();
        mt.start();

        //and the main thread is also in execution phase and now two threads concurrently running
        Scanner sc=new Scanner(System.in);
        sc.nextLine();

        mt.Newstatus();
        System.out.println("Stop the process");
    }

}
