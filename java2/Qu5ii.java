public class Qu5ii{
    int x,y;
    Qu5ii(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public static void main(String[] args) {
        Qu5ii c=new Qu5ii(5,7);
        Qu5ii cc=c;
        System.out.println(cc.x+" "+cc.y+" "+c.x+" "+c.y);
    }
}