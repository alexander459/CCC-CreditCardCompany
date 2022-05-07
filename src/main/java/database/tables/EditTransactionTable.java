package database.tables;

import com.google.gson.Gson;
import database.DB_Connection;
import mainClasses.Transaction;
import mainClasses.User;

import java.sql.*;
import java.util.ArrayList;

public class EditTransactionTable {

    /**
     * returns array list with all the transactions containing account id
     * @param account_id
     * @return
     */
    public ArrayList<Transaction> getTransactionsWithAccount(int account_id, String from, String to) throws SQLException, ClassNotFoundException{
        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();
        ArrayList<Transaction> transactions = new ArrayList<>();
        ResultSet rs;

        try {
            rs = stmt.executeQuery("SELECT * FROM transaction WHERE (customer_account= '" + account_id + "' "
                                    + "OR dealer_account= '" + account_id + "') AND (date>= CAST('" + from + "' as date) AND date<= CAST('" + to + "' as date))");
            while(rs.next()){
                String json=DB_Connection.getResultsToJSON(rs);
                Gson gson = new Gson();
                transactions.add(gson.fromJson(json, Transaction.class));
            }
            return transactions;
        } catch (Exception e) {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }
        return null;
    }


    /**
     * isolates the transactions from an employee
     * @param employee_id
     * @return
     */
    public ArrayList<Transaction> transactionsByEmployeeId(int account_id, String from, String to, int employee_id) throws SQLException, ClassNotFoundException{
        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();
        ArrayList<Transaction> transactions = new ArrayList<>();
        ResultSet rs;
        System.out.println(from);
        System.out.println(to);
        try {
            rs = stmt.executeQuery("SELECT * FROM transaction WHERE (customer_account= '" + account_id + "' "
                    + "OR dealer_account= '" + account_id + "') AND (date>= CAST('" + from + "' as date) AND date<= CAST('" + to + "' as date)) "
                    + "AND employee_id='" + employee_id + "'");
            while(rs.next()){
                String json=DB_Connection.getResultsToJSON(rs);
                Gson gson = new Gson();
                transactions.add(gson.fromJson(json, Transaction.class));
            }
            return transactions;
        } catch (Exception e) {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }
        return null;
    }

    /**
     * Adds a new transaction in the database
     * @param tr the transaction
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public void addNewTransaction(Transaction tr) throws SQLException, ClassNotFoundException{
        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();
        String sql;

        if(tr.getEmployee_id()==-1) {         //its not an employee
            sql = "INSERT INTO "
                    + " transaction (customer_account,dealer_account,date,amount,transaction_type)"
                    + " VALUES ("
                    + "'" + tr.getCustomer_account() + "',"
                    + "'" + tr.getDealer_account() + "',"
                    + "'" + tr.getDate() + "',"
                    + "'" + tr.getAmount() + "',"
                    + "'" + tr.getTransaction_type() + "')";
        }else{
            sql = "INSERT INTO "
                    + " transaction (employee_id,customer_account,dealer_account,date,amount,transaction_type)"
                    + " VALUES ("
                    + "'" + tr.getEmployee_id() + "',"
                    + "'" + tr.getCustomer_account() + "',"
                    + "'" + tr.getDealer_account() + "',"
                    + "'" + tr.getDate() + "',"
                    + "'" + tr.getAmount() + "',"
                    + "'" + tr.getTransaction_type() + "')";
        }
        stmt.executeUpdate(sql);
        stmt.close();
        con.close();
    }

    /**
     * creates the transaction table
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public void createTransactionsTable() throws SQLException, ClassNotFoundException {
        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();
        String sql = "CREATE TABLE transaction "
                + "(transaction_id INTEGER AUTO_INCREMENT, "
                + "employee_id INTEGER, "
                + "customer_account INTEGER,"
                + "dealer_account INTEGER, "
                + "date DATE not NULL, "
                + "amount DOUBLE, "
                + "transaction_type VARCHAR (30),"
                + "FOREIGN KEY (employee_id) REFERENCES employee(employee_id), "
                + "PRIMARY KEY (transaction_id))";
        stmt.execute(sql);
        stmt.close();
        con.close();
    }
}