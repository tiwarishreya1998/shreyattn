public class Qu3i {

    public static void main(String args[]) {
        try
        {
            Class.forName("Helloworld");
        }
        catch (ClassNotFoundException ex)
        {
            ex.printStackTrace();
        }
    }
}

