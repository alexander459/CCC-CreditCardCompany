package mainClasses;

import java.util.Map;

public class Transaction {
    private int transaction_id;
    private int employee_id;
    private int customer_account;
    private int dealer_account;
    private String date;
    private double amount;
    private String transaction_type;



    public Transaction(Map<String, String> params){
        this.setAmount(Double.parseDouble(params.get("amount")));
        this.setDate(params.get("date"));
        this.setCustomer_account(Integer.parseInt(params.get("customer_account")));
        this.setDealer_account(Integer.parseInt(params.get("dealer_account")));
        if(params.containsKey("employee_id"))
            this.setEmployee_id(Integer.parseInt(params.get("employee_id")));
        else
            this.setEmployee_id(-1);  //-1 indicates an empty employee id input
    }

    public int getTransaction_id() {
        return transaction_id;
    }
    public int getEmployee_id() {
        return employee_id;
    }
    public int getCustomer_account() {
        return customer_account;
    }
    public int getDealer_account() {
        return dealer_account;
    }
    public String getDate() {
        return date;
    }
    public double getAmount() {
        return amount;
    }
    public String  getTransaction_type() {
        return transaction_type;
    }


    public void setTransaction_id(int transaction_id) {
        this.transaction_id = transaction_id;
    }
    public void setEmployee_id(int employee_id) {
        this.employee_id = employee_id;
    }
    public void setCustomer_account(int customer_account) {
        this.customer_account = customer_account;
    }
    public void setDealer_account(int dealer_account) {
        this.dealer_account = dealer_account;
    }
    public void setDate(String date) {
        this.date = date;
    }
    public void setAmount(double amount) {
        this.amount = amount;
    }
    public void setTransaction_type(String transaction_type) {
        this.transaction_type = transaction_type;
    }


    @Override
    public String toString() {
        return "Transaction{" +
                "transaction_id=" + transaction_id +
                ", employee_id=" + employee_id +
                ", customer_account=" + customer_account +
                ", dealer_account=" + dealer_account +
                ", date='" + date + '\'' +
                ", amount=" + amount +
                ", transaction_type='" + transaction_type + '\'' +
                '}';
    }
}
