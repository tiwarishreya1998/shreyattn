import java.util.Scanner;
public class Qu7 {
    public static void main(String[] args) {
        Scanner sc=new Scanner(System.in);
        System.out.println("enter the seconds which you want to convert");
        int sec=sc.nextInt();
        float days,hours,minutes;
        System.out.println("Converting sec into Days: ");
        days=sec/(24*60*60);
        System.out.println("no of days are: "+days);
        System.out.println("Converting sec into hours");
        hours=sec/(60*60);
        System.out.println("no of days are: "+hours);
        System.out.println("Converting sec into minutes");
        minutes=sec/60;
        System.out.println("no of days are: "+minutes);

    }
}
