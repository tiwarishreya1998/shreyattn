class Data {
    int msg;
    boolean valueSet=false;
    public synchronized void get() {
        try {Thread.sleep(300


        );}
        catch(Exception e) {
            e.printStackTrace();
        }
        while(!valueSet) {
            try {wait();}
            catch(Exception e) {
                e.printStackTrace();
            }
        }
        System.out.println("consumer:"+msg);
        valueSet=false;
        notify();

    }
    public synchronized void set(int msg) {
        try {Thread.sleep(300);}
        catch(Exception e) {
            e.printStackTrace();
        }
        while(valueSet) {
            try {wait();}
            catch(Exception e) {
                e.printStackTrace();
            }
        }
        this.msg=msg;
        System.out.println("producer:"+msg);
        valueSet=true;
        notify();
    }

}
 class Producer implements Runnable{
    Data d;

    Producer(Data d){
        this.d=d;
        new Thread(this,"producer").start();
    }
    public void run() {
        int i=1;
        while(true) {
            d.set(i++);
        }
    }

}
class Consumer implements Runnable {
    Data d;
    boolean ValueSet=false;
    Consumer(Data d){
        this.d=d;
        new Thread(this,"consumer").start();
    }
    public void run() {
        while(true) {
            d.get();
        }
    }

}
public class Q5 {

    public static void main(String[] args) {
        Data d=new Data();
        new Producer(d);
        new Consumer(d);

    }

}
