package web.servlet.logAndRegister;

import tool.BasicTool;
import tool.FormatCheckTool;
import web.sessionPacket.AdminSessionPacket;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

//输入字段:admin_name     password
//输出字段: state  -0 登陆失败（账号或密码不正确/附加state 预留）    -1 登录成功   -2 字段错误    -3 已经登录过   -4 用户名不存在 -5 密码不正确
@WebServlet("/adminLogin")
public class AdminLogin extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        BasicTool.setCharacterEncoding(req, resp);
        String admin_name = req.getParameter("admin_name");
        String password = req.getParameter("password");
        int state = 0;
        //不要验证密码格式
        if(FormatCheckTool.checkAdminName(admin_name)&&FormatCheckTool.checkNotNull(password)){
            HttpSession httpSession = req.getSession();
            if(httpSession.getAttribute("admin")!=null){
                //已经登录过了
                state =3;
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
                        state = 5;
                    }
                }
                else{
                    state = 4;
                }
            }
        }
        else {
            state =2;
        }
        resp.getWriter().write(BasicTool.getStateStr(state));
    }
}