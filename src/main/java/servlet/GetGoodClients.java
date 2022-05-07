package servlet;

import database.tables.EditUsersTable;
import mainClasses.User;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

@WebServlet(name = "GetGoodClients", value = "/GetGoodClients")
public class GetGoodClients extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ArrayList<User> users;
        ArrayList<String> JSONUsers = new ArrayList<>();
        try{
            EditUsersTable eut = new EditUsersTable();
            users = eut.getClientsWithAmount(0);
            if(users==null){
                response.setStatus(401);
                return;
            }
            for(int i=0; i<users.size(); i++){
                JSONUsers.add(eut.userToJSON(users.get(i)));
            }
            response.getWriter().println(JSONUsers);
        }catch (SQLException e){
            System.out.println("getting good clients " + e);
            response.setStatus(500);
        }catch (ClassNotFoundException e){
            System.out.println("getting good clients " + e);
            response.setStatus(500);
        }
    }

}
