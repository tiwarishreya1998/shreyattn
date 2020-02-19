public class Customer
{
    private String name;
    private double phone;
    private int tokeno;
    Customer(String name,double phone)
    {
        this.name=name;
        this.phone=phone;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPhone() {
        return phone;
    }

    public void setPhone(double phone) {
        this.phone = phone;
    }

    public int getTokeno() {
        return tokeno;
    }

    public void setTokeno(int tokeno) {
        this.tokeno = tokeno;
    }
    public void PlaceOrder()
    {

        //we will place order on the basis of name and phone number
    }
    public void checkOrderStatus(int tokeno)
    {

        // we will checkorderstatus on the basis of tokenno given to the customer

    }
    public void collectOrder(int tokeno)
    {
        // customer can collect the coffee after entering tokenno
    }
    public void payment()
    {
        //payment done through this function
    }



}
