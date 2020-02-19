public class Cashier
{
    String nameOfCashier;
    int CashierId;


    public String getNameOfCashier() {
        return nameOfCashier;
    }

    public void setNameOfCashier(String nameOfCashier) {
        this.nameOfCashier = nameOfCashier;
    }

    public int getCashierId() {
        return CashierId;
    }

    public void setCashierId(int cashierId) {
        CashierId = cashierId;
    }

    public void takeOrder(String customerName)
    {
       //cashier will take the order
    }
    public void giveTokenNo(String customerName)
    {
        //this will be called when customer places an order so cashier will provide him with a tokenno
    }
    public void receivePayment(int tokenno)
    {
        //
    }
    public void addItToPendingQueue(int tokeno)
    {

    }



}
