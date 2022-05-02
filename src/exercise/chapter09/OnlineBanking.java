package exercise.chapter09;

abstract class OnlineBanking {
    public void processCustomer(int id){
//        Customer c = Database.getCustomerWithId(id);
        Customer c = new Customer("OnePunchMan", 1);
        makeCustomerHappy(c);
    }
    abstract void makeCustomerHappy(Customer c);
}