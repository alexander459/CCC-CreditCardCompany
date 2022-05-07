package servlet;

import database.tables.DbObserver;
import database.tables.EditCustomerTable;
import database.tables.EditDealerTable;
import database.tables.EditTransactionTable;
import mainClasses.Customer;
import mainClasses.Dealer;
import mainClasses.MapConverter;
import mainClasses.Transaction;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Map;

@WebServlet(name = "PerformReturn", value = "/PerformReturn")
public class PerformReturn extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        Map<String, String> params;

        String queryFormatParameters = request.getReader().lines().reduce("", (accumulator, actual)
                -> accumulator + actual);

        params = new MapConverter().queryToMap(queryFormatParameters);
        Transaction tr = new Transaction(params);
        System.out.println(tr);

        int dealer_account = tr.getDealer_account();
        int customer_account = tr.getCustomer_account();
        DbObserver check = new DbObserver();

        //check if the dealer account exists
        try {
            if(!check.dealerAccountExists(dealer_account)){
                response.setStatus(403);
                return;
            }
        } catch (SQLException e) {
            System.out.println("perform purchase checking dealer " + e);
            response.setStatus(500);
            return;
        } catch (ClassNotFoundException e) {
            System.out.println("perform purchase checking dealer " + e);
            response.setStatus(500);
            return;
        }

        //check if the customer account exists
        try{
            if(!check.customerAccountExists(customer_account)){
                response.setStatus(405);
                return;
            }
        }catch (SQLException e){
            System.out.println("perform purchase checking customer " + e);
            response.setStatus(500);
            return;
        }catch (ClassNotFoundException e){
            System.out.println("perform purchase checking customer " + e);
            response.setStatus(500);
            return;
        }


        //check if the customer can afford the purchase
        try{
            if(!check.canDealerAffordReturn(tr.getAmount(), dealer_account)){
                response.setStatus(409);
                return;
            }
        }catch (SQLException e){
            System.out.println("perform transaction checks if customer is company " + e);
            response.setStatus(500);
            return;
        }catch (ClassNotFoundException e){
            System.out.println("perform transaction checks if customer is company " + e);
            response.setStatus(500);
            return;
        }


        //check if the customer is a company
        try {
            if(check.isCompany(customer_account)){
                //is a company so the employee id is required
                if(tr.getEmployee_id()==-1){
                    response.setStatus(406);
                    return;
                }
            }
        } catch (SQLException e) {
            System.out.println("perform transaction checks if customer is company " + e);
            response.setStatus(500);
            return;
        } catch (ClassNotFoundException e) {
            System.out.println("perform transaction checks if customer is company " + e);
            response.setStatus(500);
            return;
        }

        //check if employee id is valid
        try{
            if(tr.getEmployee_id()!=-1) {  //if exists check if it is valid
                if (!check.employeeExists(tr.getEmployee_id())) {
                    response.setStatus(406);
                    return;
                }
            }
        }catch (SQLException e){
            System.out.println("perform purchase checking employee id " + e);
            response.setStatus(500);
            return;
        }catch (ClassNotFoundException e){
            System.out.println("perform purchase checking employee id " + e);
            response.setStatus(500);
            return;
        }


        EditTransactionTable ett = new EditTransactionTable();
        try {
            tr.setTransaction_type("credit");
            ett.addNewTransaction(tr);
            response.setStatus(200);
            EditCustomerTable ect = new EditCustomerTable();
            EditDealerTable edt = new EditDealerTable();
            Customer customer;
            Dealer dealer;
            customer = ect.getCustomer(customer_account);
            dealer = edt.getDealer(dealer_account);

            double owed_amount = customer.getOwed_amount();
            double balance = customer.getAvailable_balance();
            if(owed_amount-tr.getAmount()<=0)
                ect.updateCustomerOwedAmount(customer_account, 0, (balance+tr.getAmount()));
            else
                ect.updateCustomerOwedAmount(customer_account, (owed_amount-tr.getAmount()), (balance+tr.getAmount()));

            owed_amount = dealer.getOwed_amount();
            double profit = dealer.getTotal_profit();

            edt.updateDealer((profit-tr.getAmount()), (owed_amount-tr.getAmount()*(dealer.getSupply()/100)), dealer.getAccountId());
            return;
        } catch (SQLException e) {
            System.out.println("perform purchase adding transaction " + e);
            response.setStatus(500);
            return;
        } catch (ClassNotFoundException e) {
            System.out.println("perform purchase adding transaction " + e);
            response.setStatus(500);
            return;
        }

    }



}
