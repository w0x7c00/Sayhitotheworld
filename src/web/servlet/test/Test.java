package web.servlet.test;


import com.google.gson.Gson;
import tool.BasicTool;
import web.dataBasePacket.User;
import web.email.BasicEmailTool;

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
        //BasicEmailTool.sendMail("3126044501@qq.com","TEST");
        int a =9;
        try{
            a = Integer.parseInt("abc123");
        }
        catch (NumberFormatException e){
            a = 999111;
        }
        resp.getWriter().write(a+"");
    }
}