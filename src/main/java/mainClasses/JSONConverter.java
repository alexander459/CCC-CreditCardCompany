package mainClasses;

import com.google.gson.Gson;

public class JSONConverter {
    public String userToJSON(User user){
        Gson gson = new Gson();

        String json = gson.toJson(user, User.class);
        return json;
    }

    public String dealerToJSON(Dealer dealer){
        Gson gson = new Gson();

        String json = gson.toJson(dealer, Dealer.class);
        return json;
    }

    public String employeeToJSON(Employee employee){
        Gson gson = new Gson();

        String json = gson.toJson(employee, Employee.class);
        return json;
    }

    public String customerToJSON(Customer customer){
        Gson gson = new Gson();

        String json = gson.toJson(customer, Customer.class);
        return json;
    }
    public String transactionToJSON(Transaction transaction){
        Gson gson = new Gson();

        String json = gson.toJson(transaction, Transaction.class);
        return json;
    }

}
