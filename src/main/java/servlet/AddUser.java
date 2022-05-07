package servlet;

import database.tables.EditCustomerTable;
import database.tables.EditDealerTable;
import database.tables.EditEmployeeTable;
import database.tables.EditUsersTable;
import mainClasses.*;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "AddUser", value = "/AddUser")
public class AddUser extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        Map<String, String> params;

        String queryFormatParameters = request.getReader().lines().reduce("", (accumulator, actual)
                -> accumulator + actual);

        params = new MapConverter().queryToMap(queryFormatParameters);


        if(params.get("user_type").equalsIgnoreCase("customer")){
            //params contain username, expDate, creditLimit and customerType
            response.setStatus(this.addCustomer(params, out));
        }else if(params.get("user_type").equalsIgnoreCase("dealer")){
            //params contain username and supply
            response.setStatus(this.addDealer(params, out));
        }else{
            //params contain employee name and account id
            response.setStatus(this.addEmployee(params, out));
        }
    }

    /**
     * adds customer in the database and prints the customer into JSON format
     * @return the status code. 200 success, 500 exception
     */
    private int addCustomer(Map<String, String> params, PrintWriter out){
        Customer cstm = new Customer(params);
        EditUsersTable eut = new EditUsersTable();
        EditCustomerTable ect = new EditCustomerTable();
        int id;

        //get the account id
        try {
            id = eut.accountIdGenerator();
            cstm.setAccountId(id);
        } catch (SQLException e) {
            System.out.println("AddUser getting account id " + e);
            return 500;
        } catch (ClassNotFoundException e) {
            System.out.println("AddUser getting account id " + e);
            return 500;
        }

        //add the customer
        try {
            ect.addNewCustomer(cstm);
        } catch (SQLException e) {
            System.out.println("AddUser adding customer " + e);
            return 500;
        } catch (ClassNotFoundException e) {
            System.out.println("AddUser adding customer " + e);
            return 500;
        }

        out.println(new JSONConverter().customerToJSON(cstm));
        return 200;
    }

    /**
     * adds dealer in the database and prints the dealer into JSON format
     * @return the status code. 200 success, 500 exception
     */
    private int addDealer(Map<String, String> params, PrintWriter out){
        Dealer dlr = new Dealer(params);
        EditUsersTable eut = new EditUsersTable();
        EditDealerTable edt = new EditDealerTable();
        int id;

        //get the account id
        try {
            id = eut.accountIdGenerator();
            dlr.setAccountId(id);
        } catch (SQLException e) {
            System.out.println("AddUser getting account id for dealer " + e);
            return 500;
        } catch (ClassNotFoundException e) {
            System.out.println("AddUser getting account id for dealer " + e);
            return 500;
        }

        //add the dealer
        try {
            edt.addNewDealer(dlr);
        } catch (SQLException e) {
            System.out.println("AddUser adding dealer " + e);
            return 500;
        } catch (ClassNotFoundException e) {
            System.out.println("AddUser adding dealer " + e);
            return 500;
        }

        out.println(new JSONConverter().dealerToJSON(dlr));
        return 200;
    }


    /**
     * adds dealer in the database and prints the dealer into JSON format
     * @return the status code. 200 success, 500 exception
     */
    private int addEmployee(Map<String, String> params, PrintWriter out){
        Employee employee = new Employee(params);
        EditEmployeeTable eet = new EditEmployeeTable();
        int status;

        //add the employee
        try {
            status = eet.addNewEmployee(employee);
        } catch (SQLException e) {
            System.out.println("AddUser adding employee " + e);
            return 500;
        } catch (ClassNotFoundException e) {
            System.out.println("AddUser adding employee " + e);
            return 500;
        }

        out.println(new JSONConverter().employeeToJSON(employee));
        return status;
    }

}
