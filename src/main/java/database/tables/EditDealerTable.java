package database.tables;

import com.google.gson.Gson;
import database.DB_Connection;
import mainClasses.Customer;
import mainClasses.Dealer;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class EditDealerTable {

    /**
     * returns a dealer or null
     * @param account
     * @return
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public Dealer getDealer(int account) throws SQLException, ClassNotFoundException{
        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();

        ResultSet rs;
        try {
            rs = stmt.executeQuery("SELECT * FROM dealer WHERE account_id= '" + account + "'");
            rs.next();
            String json=DB_Connection.getResultsToJSON(rs);
            Gson gson = new Gson();
            Dealer dealer = gson.fromJson(json, Dealer.class);
            return dealer;
        } catch (Exception e) {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }
        return null;
    }

    /**
     *
     * @param profit
     * @param owed_amount
     * @param account
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public void updateDealer(double profit, double owed_amount, int account) throws SQLException, ClassNotFoundException{
        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();
        String update="UPDATE dealer SET total_profit='" + profit + "' WHERE account_id = '" + account + "'";
        stmt.executeUpdate(update);

        update="UPDATE dealer SET owed_amount='" + owed_amount + "' WHERE account_id = '" + account + "'";
        stmt.executeUpdate(update);
        stmt.close();
        con.close();
    }

    /**
     * Adds a nw dealer
     * @param dealer
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public void addNewDealer(Dealer dealer) throws SQLException, ClassNotFoundException{
        //first create a user with username and account id
        EditUsersTable eut = new EditUsersTable();
        eut.addNewUser(dealer);

        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();

        String sql = "INSERT INTO "
                + " dealer (account_id,supply,total_profit,owed_amount)"
                + " VALUES ("
                + "'" + dealer.getAccountId() + "',"
                + "'" + dealer.getSupply() + "',"
                + "'" + dealer.getTotal_profit() + "',"
                + "'" + dealer.getOwed_amount() + "')";

        try {
            stmt.executeUpdate(sql);

        }catch (SQLException e){
            System.out.println("adding new dealer " + e);
        }
        stmt.close();
        con.close();
    }


    /**
     * creates the dealer table
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public void createDealerTable() throws SQLException, ClassNotFoundException {
        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();
        String sql = "CREATE TABLE dealer "
                + "(dealer_id INTEGER not NULL AUTO_INCREMENT,"
                + "account_id INTEGER, "
                + "supply DOUBLE, "
                + "total_profit DOUBLE, "
                + "owed_amount DOUBLE, "
                + "PRIMARY KEY (dealer_id),"
                + "FOREIGN KEY (account_id) REFERENCES user(account_id))";
        stmt.execute(sql);
        stmt.close();
        con.close();

    }
}