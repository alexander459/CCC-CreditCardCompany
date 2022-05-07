package mainClasses;

import java.util.Map;

public class Customer extends User{
    private String exp_date;
    private double credit_limit;
    private double owed_amount;
    private double available_balance;
    private String customer_type;

    public Customer(Map<String, String> params){
        super(params);          //sets the username
        this.setExp_date(params.get("exp_date"));   //sets exp date
        this.setCredit_limit(Double.parseDouble(params.get("credit_limit")));   //sets the credit limit
        this.setOwed_amount(0);         //sets the owed amount 0. User has no dept at the beginning
        this.setAvailable_balance(this.getCredit_limit());      //balance will be equal to credit limit
        this.setCustomer_type(params.get("customer_type"));   //sets the customer type
    }

    public String getExp_date() {
        return exp_date;
    }
    public double getCredit_limit() {
        return credit_limit;
    }
    public double getOwed_amount() {
        return owed_amount;
    }
    public double getAvailable_balance() {
        return available_balance;
    }
    public String getCustomer_type() {
        return customer_type;
    }


    public void setExp_date(String exp_date) {
        this.exp_date = exp_date;
    }
    public void setCredit_limit(double credit_limit) {
        this.credit_limit = credit_limit;
    }
    public void setOwed_amount(double owed_amount) {
        this.owed_amount = owed_amount;
    }
    public void setAvailable_balance(double available_balance) {
        this.available_balance = available_balance;
    }
    public void setCustomer_type(String customer_type) {
        this.customer_type = customer_type;
    }


    @Override
    public String toString() {
        return "Customer{" +
                "exp_date='" + exp_date + '\'' +
                ", credit_limit=" + credit_limit +
                ", owed_amount=" + owed_amount +
                ", available_balance=" + available_balance +
                ", customer_type='" + customer_type + '\'' +
                ", account_id=" + account_id +
                ", username='" + username + '\'' +
                '}';
    }
}
