import java.util.Scanner;
public class Qu13 {
    public static void main(String[] args) throws NotEligibleToVote {
        System.out.println("Enter your age if you want to vote");
        Scanner sc=new Scanner(System.in);
        int age= sc.nextInt();
        if(age<18)
        {
            throw new NotEligibleToVote("age not appropriate");
        }
        else
        {
            System.out.println("Moving forward enter your country name");
            String s=sc.next();
            if(!s.equalsIgnoreCase("india"))
            {
                throw new NotEligibleToVote("not indian");

            }
            else
                System.out.println("you are eligible to vote");
        }
    }

}
class NotEligibleToVote extends Exception{
    NotEligibleToVote(String s){
        super(s);
    }
}