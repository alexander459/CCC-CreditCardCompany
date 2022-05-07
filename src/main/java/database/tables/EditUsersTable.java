package database.tables;

import com.google.gson.Gson;
import database.DB_Connection;
import mainClasses.Customer;
import mainClasses.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.Random;

public class EditUsersTable {


    public User getUser(int id) throws SQLException, ClassNotFoundException{
        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();

        ResultSet rs;
        try {
            rs = stmt.executeQuery("SELECT * FROM user WHERE account_id= '" + id + "'");
            rs.next();
            String json=DB_Connection.getResultsToJSON(rs);
            Gson gson = new Gson();
            User user = gson.fromJson(json, User.class);
            return user;
        } catch (Exception e) {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }
        return null;
    }


    /**
     * Generates and returns a random account_id
     * @return the account id
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public int accountIdGenerator() throws SQLException, ClassNotFoundException{
        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();
        ResultSet rs;
        String sql;
        int id;

        do {
            Random random = new Random();
            id = random.nextInt(1000, 9999);
            sql = "SELECT * FROM user WHERE account_id='" + id + "'";
            rs = stmt.executeQuery(sql);
        }while(rs.next());
        stmt.close();
        con.close();
        return id;
    }

    /**
     * adds a new user in the database
     * @param user the new user
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public void addNewUser(User user) throws SQLException, ClassNotFoundException{
        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();
        String sql = "INSERT INTO "
                + " user (account_id,username)"
                + " VALUES ("
                + "'" + user.getAccountId() + "',"
                + "'" + user.getUsername() + "')";
        stmt.executeUpdate(sql);
        stmt.close();
        con.close();
    }

    /**
     * deletes the user with account id == account_id
     * @param account_id the account id of the user
     * @return returns the status. 200 on success, 500 on failure, 401 if user does not exist
     */
    public int deleteUser(int account_id) throws SQLException, ClassNotFoundException{
        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();
        String sql;

        sql = "SELECT * FROM user WHERE account_id='" + account_id + "'";
        ResultSet rs = stmt.executeQuery(sql);

        if(!rs.next()){
            stmt.close();
            con.close();
            return 401;         //does not exist
        }
        sql = "SET FOREIGN_KEY_CHECKS=0";
        stmt.executeUpdate(sql);

        sql = "DELETE FROM user WHERE account_id='" + account_id + "'";
        stmt.executeUpdate(sql);

        sql = "DELETE FROM customer WHERE account_id='" + account_id + "'";
        stmt.executeUpdate(sql);

        sql = "DELETE FROM dealer WHERE account_id='" + account_id + "'";
        stmt.executeUpdate(sql);

        sql = "DELETE FROM employee WHERE account_id='" + account_id + "'";
        stmt.executeUpdate(sql);

        sql = "SET FOREIGN_KEY_CHECKS=1";
        stmt.executeUpdate(sql);

        stmt.close();
        con.close();
        return 200;       //deleted
    }


    /** returns an arraylist with the users with amount <amount>
     * @return array list
     */
    public ArrayList<User> getClientsWithDept() throws SQLException, ClassNotFoundException{
        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();
        ResultSet rs;

        ArrayList<User> users = new ArrayList<>();
        String sql;
        sql = "SELECT * FROM user WHERE account_id IN (SELECT account_id FROM customer WHERE owed_amount>0 UNION SELECT account_id FROM dealer WHERE owed_amount>0 "
               + "ORDER BY owed_amount ASC)";

        /*sql = "SELECT account_id FROM "
               + "(SELECT account_id FROM customer WHERE owed_amount>'0') "
               + "UNION (SELECT account_id FROM dealer WHERE owed_amount>'0') "
               + "ORDER BY owed_amount";
        */
        rs = stmt.executeQuery(sql);
        rs.next();
        System.out.println(rs.getString("account_id"));
        while(rs.next()){
            EditUsersTable eut = new EditUsersTable();
            users.add(eut.getUser(Integer.parseInt(rs.getString("account_id"))));
        }
        stmt.close();
        con.close();

        if(users.size()!=0)
            return users;
        else
            return null;

    }

    /** returns an arraylist with the users with amount <amount></amount>
     * @param amount the amount
     * @return array list
     */
    public ArrayList<User> getClientsWithAmount(double amount) throws SQLException, ClassNotFoundException{
        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();
        ResultSet rs;

        ArrayList<User> users = new ArrayList<>();
        String sql;
        sql = "SELECT * FROM user WHERE account_id IN (SELECT account_id FROM customer "
                + "WHERE owed_amount='" + amount + "' UNION SELECT account_id FROM dealer WHERE owed_amount='" + amount + "')";
        rs = stmt.executeQuery(sql);

        while(rs.next()){
            EditUsersTable eut = new EditUsersTable();
            users.add(eut.getUser(Integer.parseInt(rs.getString("account_id"))));
        }
        stmt.close();
        con.close();

        if(users.size()!=0)
            return users;
        else
            return null;

    }


    public String userToJSON(User user){
        Gson gson = new Gson();

        String json = gson.toJson(user, User.class);
        return json;
    }

    /**
     * creates the user table
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public void createUserTable() throws SQLException, ClassNotFoundException {
        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();
        String sql = "CREATE TABLE user "
                + "(account_id INTEGER not NULL, "
                + "username VARCHAR (30) not NULL, "
                + "PRIMARY KEY (account_id))";
        stmt.execute(sql);
        stmt.close();
        con.close();
    }

}
