package database.tables;

import database.DB_Connection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DbObserver {
    /**
     * checks if the account belongs to a dealer
     * @param account the account
     * @return true if there is dealer with this account, false if not
     */
    public boolean dealerAccountExists(int account) throws SQLException, ClassNotFoundException{
        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();
        ResultSet rs;

        String sql = "SELECT * FROM dealer WHERE account_id='" + account + "'";

        rs = stmt.executeQuery(sql);
        if(!rs.next()) {
            stmt.close();
            con.close();
            return false;
        }

        stmt.close();
        con.close();
        return true;
    }

    /**
     * checks if the account belongs to a customer
     * @param account the account
     * @return true if there is dealer with this account, false if not
     */
    public boolean customerAccountExists(int account) throws SQLException, ClassNotFoundException{
        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();
        ResultSet rs;

        String sql = "SELECT * FROM customer WHERE account_id='" + account + "'";

        rs = stmt.executeQuery(sql);
        if(!rs.next()) {
            stmt.close();
            con.close();
            return false;
        }

        stmt.close();
        con.close();
        return true;
    }


    /**
     * checks if the employee id exists
     * @param id the employee id
     * @return true if employee exists, false if not
     */
    public boolean employeeExists(int id) throws SQLException, ClassNotFoundException{
        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();
        ResultSet rs;

        String sql = "SELECT * FROM employee WHERE employee_id='" + id + "'";

        rs = stmt.executeQuery(sql);
        if(!rs.next()) {
            stmt.close();
            con.close();
            return false;
        }

        stmt.close();
        con.close();
        return true;
    }

    /**
     * checks if the id belong to a company
     * @param id the id
     * @return true if exist false if not
     */
    public boolean isCompany(int id) throws SQLException, ClassNotFoundException{
        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();
        ResultSet rs;

        String sql = "SELECT * FROM customer WHERE account_id='" + id + "'";

        rs = stmt.executeQuery(sql);
        rs.next();
        return rs.getString("customer_type").equalsIgnoreCase("company");
    }

    /**
     * returns true if the customer can afford the purchase and false if not
     * @param amount the amount
     * @param customer_account the customer account
     * @return
     */
    public boolean canCustomerAffordPurchase(double amount, int customer_account) throws SQLException, ClassNotFoundException{
        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();
        ResultSet rs;

        String sql = "SELECT * FROM customer WHERE account_id='" + customer_account + "'";

        rs = stmt.executeQuery(sql);
        rs.next();
        double balance = Double.parseDouble(rs.getString("available_balance"));
        return balance>=amount;
    }

    /**
     * returns true if the dealer can afford the return and false if not
     * @param amount the amount
     * @param dealer_account the dealer account
     * @return
     */
    public boolean canDealerAffordReturn(double amount, int dealer_account) throws SQLException, ClassNotFoundException{
        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();
        ResultSet rs;

        String sql = "SELECT * FROM dealer WHERE account_id='" + dealer_account + "'";

        rs = stmt.executeQuery(sql);
        rs.next();
        double balance = Double.parseDouble(rs.getString("total_profit"));
        return balance>=amount;
    }
}
