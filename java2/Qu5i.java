public class Qu5i implements Cloneable {

        String first_name;
        String Last_name;

       Qu5i(String first_name,String Last_name){
            this.first_name=first_name;
            this.Last_name=Last_name;
        }

        public Object clone()throws CloneNotSupportedException{
            return super.clone();
        }

        public static void main(String args[]){
            try{
                Qu5i s1=new Qu5i("abc","xyz");

                Qu5i s2=(Qu5i)s1.clone();

                System.out.println(s1.first_name+" "+s1.Last_name);
                System.out.println(s2.first_name+" "+s2.Last_name);

            }catch(CloneNotSupportedException c){}

        }
}

