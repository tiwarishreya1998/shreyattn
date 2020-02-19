import java.util.Scanner;

interface Furniture {
    public void stressTest();
    public void fireTest();
}

abstract class Chair implements Furniture {
    public abstract String chairType();
}


abstract class Table implements Furniture {
    public abstract String tableType();
}

class MetalChair extends Chair {
    @Override
    public void stressTest() {
        System.out.println("Passed Stress Test");

    }

    @Override
    public void fireTest() {
        System.out.println("Passed fire Test");

    }

    @Override
    public String chairType() {
        String s = "This is a metal Chair";
        return s;
    }
}

class MetalTable extends Table {
    @Override
    public void stressTest() {

        System.out.println("Passed Stress Test");

    }

    @Override
    public void fireTest() {

        System.out.println("Passed Fire Test");
    }

    @Override
    public String tableType() {

        String s = "This is a metal Table";
        return s;
    }
}


class WoodenTable extends Table {
    @Override
    public void stressTest() {

        System.out.println("Failed Stress Test");
    }

    @Override
    public void fireTest() {

        System.out.println("Failed Fire Test");
    }

    @Override
    public String tableType() {

        String s = "This is a wooden Table";
        return s;
    }
}


class WoodenChair extends Chair {
    @Override
    public void stressTest() {
        System.out.println("Failed stress test");

    }

    @Override
    public void fireTest() {
        System.out.println("Failed Stress Test");

    }

    @Override
    public String chairType() {
        String s = "This is a wooden chair";
        return s;
    }
}


public class Qu9{
    public static void main(String[] args){
        Table table = null;
        Scanner input =  new Scanner(System.in);
        System.out.println("Enter the table type from wooden and metal");
        String str = input.next();
        if(str.equalsIgnoreCase("wooden")){
            table = new WoodenTable();


        }   else if (str.equalsIgnoreCase("metal")){
            table = new MetalTable();


        }

        System.out.println(table.tableType());
        table.stressTest();
        table.fireTest();

        Chair chair = null;
        System.out.println("Enter the chair type from wooden and metal");
        String strg = input.next();
        if(strg.equals("wooden")){
            chair = new WoodenChair();


        }   else if (strg.equals("metal")){
            chair = new MetalChair();


        }

        System.out.println(chair.chairType());
        chair.stressTest();
        chair.fireTest();

    }
}