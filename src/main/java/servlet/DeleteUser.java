package servlet;

import database.tables.EditUsersTable;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "DeleteUser", value = "/DeleteUser")
public class DeleteUser extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String account_id = request.getParameter("account_id");

        try{
            EditUsersTable eut = new EditUsersTable();
            response.setStatus(eut.deleteUser(Integer.parseInt(account_id)));
        }catch (SQLException e){
            System.out.println("int Delete user " + e);
            response.setStatus(500);
        }catch (ClassNotFoundException e){
            System.out.println("int Delete user " + e);
            response.setStatus(500);
        }
    }

}
