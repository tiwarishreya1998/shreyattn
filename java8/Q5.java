import java.util.ListResourceBundle;
import java.util.Random;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

import static java.lang.StrictMath.random;

public class Q5 {
    public static void main(String[] args) {
        System.out.println("****************Consumer interface***************");
        Consumer<Integer> display=a-> System.out.println(a);
        display.accept(19);

        System.out.println("****************Supplier interface***************");
        Supplier<Double> supplier=() ->Math.random();
        System.out.println(supplier.get());


        System.out.println("****************Predicate interface***************");
        Predicate<Integer> lesserthan = i -> (i < 18);
        System.out.println(lesserthan.test(10));

        System.out.println("****************Function interface***************");
        Function<Long, Long> adder = (value) -> value + 3;
        Long result = adder.apply((long) 8);
        System.out.println("resultLambda = " + result);

    }



    private static Integer getNum(){
        Random random=new Random();
        return random.nextInt(1000);

    }

    static String fun(String fun1)
    {
        return "hello"+"  "+fun1;
    }


}
