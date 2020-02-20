class Example extends Thread {
    public void run()
    {
        for(int i=0;i<9;i++)
        {
            System.out.println("iterating: "+i);
            try{
                Thread.sleep(1000);
            }
            catch(InterruptedException p) {
                p.printStackTrace();
            }
        }
    }
}
public class Q2i{
    public static void main(String[] args)throws InterruptedException {
        Example ex=new Example();
        ex.start();
        Thread.sleep(20);
    }
        }