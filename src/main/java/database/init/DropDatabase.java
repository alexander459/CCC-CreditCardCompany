package database.init;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import static database.DB_Connection.getInitialConnection;

public class DropDatabase {
    public static void main(String[] args) throws SQLException, ClassNotFoundException{
        DropDatabase drop = new DropDatabase();
        drop.drop();
    }

    public void drop() throws SQLException, ClassNotFoundException{
        Connection conn = getInitialConnection();
        Statement stmt = conn.createStatement();
        stmt.execute("DROP DATABASE CCC");
        stmt.close();
        conn.close();
    }
}
