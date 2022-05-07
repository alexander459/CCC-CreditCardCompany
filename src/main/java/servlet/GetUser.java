package servlet;

import database.tables.EditCustomerTable;
import database.tables.EditDealerTable;
import database.tables.EditUsersTable;
import mainClasses.Customer;
import mainClasses.Dealer;
import mainClasses.JSONConverter;
import mainClasses.User;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

@WebServlet(name = "GetUser", value = "/GetUser")
public class GetUser extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String account_id = request.getParameter("account_id");
        User user;
        Customer customer;
        Dealer dealer;
        PrintWriter out = response.getWriter();
        EditUsersTable eut = new EditUsersTable();
        EditCustomerTable ect = new EditCustomerTable();
        EditDealerTable edt = new EditDealerTable();
        try{
            user = eut.getUser(Integer.parseInt(account_id));
            customer = ect.getCustomer(Integer.parseInt(account_id));
            dealer = edt.getDealer(Integer.parseInt(account_id));
            if(user == null) {
                response.setStatus(401);
                return;
            }

            if(dealer != null){
                dealer.setUsername(user.getUsername());
                dealer.setAccountId(user.getAccountId());
                out.println(new JSONConverter().dealerToJSON(dealer));
            }else{
                customer.setUsername(user.getUsername());
                customer.setAccountId(user.getAccountId());
                out.println(new JSONConverter().customerToJSON(customer));
            }
            response.setStatus(200);

        }catch (SQLException e){
            System.out.println("in get user " + e);
            response.setStatus(500);
        }catch (ClassNotFoundException e){
            System.out.println("in get user " + e);
            response.setStatus(500);
        }
    }


}
