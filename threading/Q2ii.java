 class Example2 implements Runnable {

    public void run(){
        for (int i=0;i<9;i++){
            System.out.println("the iterartion:"+i);
            try{
                Thread.sleep(500);
            }
            catch(InterruptedException e){
                e.getStackTrace();
            }
        }
    }
}
public class Q2ii{
    public static void main(String[]args) throws InterruptedException{
      Example2 ex=new Example2();
      Thread t1=new Thread(ex);
              t1.start();
      Thread.sleep(30);

        }

}