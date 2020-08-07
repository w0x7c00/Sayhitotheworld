package web.servlet.test;


import com.google.gson.Gson;
import tool.BasicTool;
import web.dataBasePacket.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.ResultSet;

@WebServlet("/test")
public class Test extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        BasicTool.setCharacterEncoding(req,resp);
        User user = new User();
        user.user_name = "davis";
        boolean result = user.set();
        user.user_name = "davis2,passwo='123'";
        user.insert();
        resp.getWriter().write(""+result);
    }
}