package servlet;

import database.tables.DbObserver;
import database.tables.EditCustomerTable;
import database.tables.EditDealerTable;
import mainClasses.Customer;
import mainClasses.Dealer;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "PayDept", value = "/PayDept")
public class PayDept extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String account_id = request.getParameter("account_id");

        //check if id is valid
        DbObserver check = new DbObserver();
        try{
            if(check.customerAccountExists(Integer.parseInt(account_id))){    //its a customer
                EditCustomerTable ect = new EditCustomerTable();
                Customer customer;
                customer=ect.getCustomer(Integer.parseInt(account_id));
                ect.updateCustomerOwedAmount(Integer.parseInt(account_id), 0, customer.getCredit_limit());
                response.setStatus(200);
            }else if(check.dealerAccountExists(Integer.parseInt(account_id))) {   //its a dealer
                EditDealerTable edt = new EditDealerTable();
                Dealer dealer;
                dealer = edt.getDealer(Integer.parseInt(account_id));
                edt.updateDealer(dealer.getTotal_profit(), 0, Integer.parseInt(account_id));
                response.setStatus(200);
            }else{                //invalid id
                response.setStatus(401);
            }
        }catch (SQLException e){
            System.out.println("Pay Dept checking id " + e);
            response.setStatus(500);
        }catch (ClassNotFoundException e){
            System.out.println("Pay Dept checking id " + e);
            response.setStatus(500);
        }
    }

}
