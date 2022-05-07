package servlet;

import com.google.gson.Gson;
import database.DB_Connection;
import mainClasses.Transaction;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@WebServlet(name = "GetDealerOfMonth", value = "/GetDealerOfMonth")
public class GetDealerOfMonth extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        int month = now.getMonth().getValue();
        int year = now.getYear();

        if(month == 1) {
            month = 12;
            year--;
        }else
            month--;
        String startDate;
        String endDate;
        if(month<10){
            startDate = year + "-0" + month + "-01";
            endDate = year + "-0" + month + "-31";
        }else{
            startDate = year + "-" + month + "-01";
            endDate = year + "-" + month + "-31";
        }

        try{
            Connection con = DB_Connection.getConnection();
            Statement stmt = con.createStatement();
            ResultSet rs;
            rs = stmt.executeQuery("SELECT temp.account_id, temp.total"
                                    + " FROM (SELECT T.account_id, SUM(T.account_id) AS total"
                                    + " FROM transaction T"
                                    + " WHERE T.date>'" + startDate + "' AND T.date<'" + endDate + "'"
                                    + " GROUP BY T.account_id) AS temp"
                                    + " WHERE temp.total=(SELECT MAX(temp.total) FROM temp)");
            rs.next();
            System.out.println(rs.getString(1));
        }catch (SQLException e){
            System.out.println("dealer of month " + e);
            response.setStatus(500);
        }catch (ClassNotFoundException e){
            System.out.println("dealer of month " + e);
            response.setStatus(500);
        }
    }


}
