package web.servlet.logAndRegister;

import tool.BasicTool;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

//登录：state：1
//未登录：state：0
@WebServlet("/checkUserLogin")
public class CheckUserLogin extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        BasicTool.setCharacterEncoding(req,resp);
        HttpSession httpSession = req.getSession();
        if(httpSession.getAttribute("user")==null){
            resp.getWriter().write("{state:1}");
        }
        else{
            resp.getWriter().write("{state:0}");
        }
    }
}