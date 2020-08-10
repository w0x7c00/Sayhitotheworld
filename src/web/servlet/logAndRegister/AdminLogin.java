package web.servlet.logAndRegister;

import tool.BasicTool;
import web.sessionPacket.AdminSessionPacket;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

//输入字段:admin_name     password
//输出字段: state  -0 登陆失败（账号或密码不正确/附加state 预留）    -1 登录成功     -2 登录失败（已经登录过了）   -3 登录失败（用户名不正确） -4 登录失败（密码不正确）   -5 登陆失败 （输入字段不正确）
@WebServlet("/adminLogin")
public class AdminLogin extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int state = 0;
        BasicTool.setCharacterEncoding(req, resp);
        String admin_name = req.getParameter("admin_name");
        String password = req.getParameter("password");
        if(admin_name == null || password == null){
            state = 5; //输入字段不正确
        }
        else {
            //不进行格式检查
            HttpSession httpSession = req.getSession();
            if(httpSession.getAttribute("admin")!=null){
                //已经登录过了
                state =2;
            }
            else{
                AdminSessionPacket adminSessionPacket = new AdminSessionPacket();
                adminSessionPacket.admin_name = admin_name;
                if(adminSessionPacket.set()){
                    //存在这个admin_name
                    if(adminSessionPacket.password.equals(password)){
                        httpSession.setAttribute("admin",adminSessionPacket);
                        state = 1;
                    }
                    else{
                        state = 4;
                    }
                }
                else{
                    state = 3;
                }
            }
        }
        resp.getWriter().write("{state:"+state+"}");
    }
}