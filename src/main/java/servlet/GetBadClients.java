package servlet;

import database.tables.EditUsersTable;
import mainClasses.User;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

@WebServlet(name = "GetBadClients", value = "/GetBadClients")
public class GetBadClients extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ArrayList<User> users;
        ArrayList<String> JSONUsers = new ArrayList<>();
        try{
            EditUsersTable eut = new EditUsersTable();
            users = eut.getClientsWithDept();
            for(int i=0; i<users.size(); i++){
                JSONUsers.add(eut.userToJSON(users.get(i)));
            }
            System.out.println(users);
            response.getWriter().println(JSONUsers);
        }catch (SQLException e){
            System.out.println("getting bad clients " + e);
            response.setStatus(500);
        }catch (ClassNotFoundException e){
            System.out.println("getting bad clients " + e);
            response.setStatus(500);
        }
    }


}
