package servlet;

import com.google.gson.Gson;
import database.tables.EditCustomerTable;
import database.tables.EditDealerTable;
import database.tables.EditTransactionTable;
import database.tables.EditUsersTable;
import mainClasses.*;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Map;

@WebServlet(name = "GetTransactions", value = "/GetTransactions")
public class GetTransactions extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        Map<String, String> params;

        String queryFormatParameters = request.getReader().lines().reduce("", (accumulator, actual)
                -> accumulator + actual);

        params = new MapConverter().queryToMap(queryFormatParameters);

        EditUsersTable eut = new EditUsersTable();
        System.out.println(params);

        User user;

        try {
            user = eut.getUser(Integer.parseInt(params.get("account_id")));
            if(user == null) {
                System.out.println("no user");
                response.setStatus(401);
                return;
            }else {
                System.out.println(user);
            }
        } catch (SQLException e) {
            System.out.println("get user transactions getting user " + e);
            response.setStatus(500);
            return;
        } catch (ClassNotFoundException e) {
            System.out.println("get user transactions getting user " + e);
            response.setStatus(500);
            return;
        }


        int account_id = Integer.parseInt(params.get("account_id"));

        try {
            EditTransactionTable ett = new EditTransactionTable();
            ArrayList<Transaction> transactions;
            ArrayList<String> JSONTransactions = new ArrayList<>();

            Customer customer;
            EditCustomerTable ect = new EditCustomerTable();
            customer = ect.getCustomer(Integer.parseInt(params.get("account_id")));
            if(customer != null)      //user is customer
                if(customer.getCustomer_type().equalsIgnoreCase("company"))    //user is a company
                    if(params.containsKey("employee_id")) {              //find the employee
                        transactions = ett.transactionsByEmployeeId(account_id, params.get("start_date"), params.get("end_date"), Integer.parseInt(params.get("employee_id")));
                        if(transactions == null || transactions.size() == 0){
                            response.setStatus(411);
                            return;
                        }

                        JSONConverter jc = new JSONConverter();
                        for(int i=0; i<transactions.size(); i++){
                            JSONTransactions.add(jc.transactionToJSON(transactions.get(i)));
                        }
                        response.setStatus(200);
                        out.println(JSONTransactions);
                        return;
                    }

            transactions = ett.getTransactionsWithAccount(account_id, params.get("start_date"), params.get("end_date"));      //get transactions with this account id
            if(transactions == null || transactions.size() == 0){
                response.setStatus(411);
                return;
            }

            JSONConverter jc = new JSONConverter();
            for(int i=0; i<transactions.size(); i++){
                JSONTransactions.add(jc.transactionToJSON(transactions.get(i)));
            }
            out.println(JSONTransactions);
            response.setStatus(200);
        } catch (SQLException e) {
            System.out.println("Get transactinons getting" + e);
            response.setStatus(500);
        } catch (ClassNotFoundException e) {
            System.out.println("Get transactinons getting" + e);
            response.setStatus(500);
        }


    }

}
