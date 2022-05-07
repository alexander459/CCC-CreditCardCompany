package database.init;

import database.tables.*;
import java.time.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

import static database.DB_Connection.getInitialConnection;

public class InitDatabase {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        InitDatabase init = new InitDatabase();
        init.createDatabase();
        init.createTables();
    }

    /**
     * creates the database
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public void createDatabase() throws SQLException, ClassNotFoundException {
        Connection conn = getInitialConnection();
        Statement stmt = conn.createStatement();
        stmt.execute("CREATE DATABASE CCC");
        stmt.close();
        conn.close();
    }

    public void createTables() throws SQLException, ClassNotFoundException {
        EditUsersTable eut = new EditUsersTable();
        eut.createUserTable();

        EditCustomerTable ect = new EditCustomerTable();
        ect.createCustomerTable();

        EditDealerTable edt = new EditDealerTable();
        edt.createDealerTable();

        EditEmployeeTable eet = new EditEmployeeTable();
        eet.createEmployeeTable();

        EditTransactionTable ett = new EditTransactionTable();
        ett.createTransactionsTable();
    }

}
