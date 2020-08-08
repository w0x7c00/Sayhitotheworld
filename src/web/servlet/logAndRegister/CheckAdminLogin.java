package web.servlet.logAndRegister;

import tool.BasicTool;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//返回字段 state： 1-登录     0-未登录
@WebServlet("/checkAdminLogin")
public class CheckAdminLogin extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        BasicTool.setCharacterEncoding(req,resp);
        if(req.getSession().getAttribute("admin")!=null){
            resp.getWriter().write("{state:1}");
        }
        else{
            resp.getWriter().write("{state:0}");
        }
    }
}