package database.tables;

import com.google.gson.Gson;
import database.DB_Connection;
import mainClasses.Customer;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class EditCustomerTable {


    /**
     * returns a customer or null
     * @param account
     * @return
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public Customer getCustomer(int account) throws SQLException, ClassNotFoundException{
        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();

        ResultSet rs;
        try {
            rs = stmt.executeQuery("SELECT * FROM customer WHERE account_id= '" + account + "'");
            rs.next();
            String json=DB_Connection.getResultsToJSON(rs);
            Gson gson = new Gson();
            Customer customer = gson.fromJson(json, Customer.class);
            return customer;
        } catch (Exception e) {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }
        return null;
    }

    public void updateCustomerOwedAmount(int account, double amount, double balance) throws SQLException, ClassNotFoundException{
        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();
        System.out.println("amount " + amount);
        String update="UPDATE customer SET owed_amount='" + amount + "' WHERE account_id = '" + account + "'";
        stmt.executeUpdate(update);

        update="UPDATE customer SET available_balance='" + balance + "' WHERE account_id = '" + account + "'";
        stmt.executeUpdate(update);
        stmt.close();
        con.close();
    }


    /**
     * Adds a new customer
     * @param customer
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public void addNewCustomer(Customer customer) throws SQLException, ClassNotFoundException{
        //first create a user with username and account id
        EditUsersTable eut = new EditUsersTable();
        eut.addNewUser(customer);

        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();

        String sql = "INSERT INTO "
                + " customer (account_id,exp_date,credit_limit,owed_amount,available_balance,customer_type)"
                + " VALUES ("
                + "'" + customer.getAccountId() + "',"
                + "'" + customer.getExp_date() + "',"
                + "'" + customer.getCredit_limit() + "',"
                + "'" + customer.getOwed_amount() + "',"
                + "'" + customer.getAvailable_balance() + "',"
                + "'" + customer.getCustomer_type() + "')";

        try {
            stmt.executeUpdate(sql);

        }catch (SQLException e){
            System.out.println("adding new customer " + e);
        }
        stmt.close();
        con.close();
    }

    /**
     * creates the customer table
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public void createCustomerTable() throws SQLException, ClassNotFoundException {
        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();
        String sql = "CREATE TABLE customer "
                + "(customer_id INTEGER not NULL AUTO_INCREMENT,"
                + "account_id INTEGER not NULL, "
                + "exp_date DATE not NULL, "
                + "credit_limit DOUBLE, "
                + "owed_amount DOUBLE, "
                + "available_balance DOUBLE, "
                + "customer_type VARCHAR (30), "  //can bee either company or individual
                + "PRIMARY KEY (customer_id),"
                + "FOREIGN KEY (account_id) REFERENCES user(account_id))";
        stmt.execute(sql);
        stmt.close();
        con.close();
    }
}
