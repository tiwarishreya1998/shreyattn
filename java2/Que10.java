public class Que10
{
    public static void main(String[] args)
    {
        Cashier cashier = new Cashier();
        Bistro bistro = new Bistro();
        Customer customer = new Customer("himanshu",3456);
        customer.PlaceOrder();
        cashier.takeOrder(customer.getName());
        cashier.giveTokenNo(customer.getName());
        cashier.receivePayment(customer.getTokeno());
        cashier.addItToPendingQueue(customer.getTokeno());
        customer.checkOrderStatus(customer.getTokeno());
        bistro.getOrderFromPendingQueue();
        bistro.prepareOrder();
        bistro.inserOrderIntoCompletedOrderQueue();
    }
}
