package database.tables;

import database.DB_Connection;
import mainClasses.Dealer;
import mainClasses.Employee;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class EditEmployeeTable {

    /**
     * adds a new employee if the account id id valid
     * @param employee the employe
     * @return 200 on success, 500 on exception or 402 if the account id is invalid (does not exist) or/and is not a company
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public int addNewEmployee(Employee employee) throws SQLException, ClassNotFoundException{
        //first check if account id exist and is a company

        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();
        String sql;
        ResultSet rs;

        sql = "SELECT * FROM customer WHERE account_id='" + employee.getAccount_id() + "' AND customer_type='company'";
        rs = stmt.executeQuery(sql);
        if(!rs.next()){
            stmt.close();
            con.close();
            return 402;
        }

        sql = "INSERT INTO "
                + " employee (account_id,employee_name)"
                + " VALUES ("
                + "'" + employee.getAccount_id() + "',"
                + "'" + employee.getEmployee_name() + "')";

        try {
            stmt.executeUpdate(sql);
            stmt.close();
            con.close();
            return 200;

        }catch (SQLException e){
            System.out.println("adding new employee " + e);
        }
        stmt.close();
        con.close();
        return 500;
    }



    /**
     * creates the employee table
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public void createEmployeeTable() throws SQLException, ClassNotFoundException {
        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();
        String sql = "CREATE TABLE employee "
                +"(employee_id INTEGER AUTO_INCREMENT, "
                + "account_id INTEGER, "
                + "employee_name VARCHAR (30), "
                + "PRIMARY KEY (employee_id), "
                + "FOREIGN KEY (account_id) REFERENCES user(account_id))";
        stmt.execute(sql);
        stmt.close();
        con.close();
    }
}
