package web.servlet.logAndRegister;

import tool.BasicTool;
import tool.FormatCheckTool;
import web.sessionPacket.UserSessionPacket;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

//输入字段 user_name/email password  type: 0-邮箱登录 1-用户名登录
//输出字段：state  -0 登陆失败 -1 登录成功   -2 字段错误    -3 已经登录过   -4 用户名或邮箱不存在  -5 密码不正确
@WebServlet("/userLogin")
public class UserLogin extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        BasicTool.setCharacterEncoding(req,resp);
        int state = 0;
        String type = req.getParameter("type");
        if(req.getSession().getAttribute("user")==null){
            if("1".equals(type)){
                //用户名登陆
                String user_name = req.getParameter("user_name");
                String password = req.getParameter("password");
                if(FormatCheckTool.checkUserName(user_name)&&FormatCheckTool.checkPassword(password)){
                    HttpSession httpSession = req.getSession();
                    BasicTool.clearSession(httpSession);
                    UserSessionPacket userSessionPacket = new UserSessionPacket();
                    userSessionPacket.user_name = user_name;
                    if (userSessionPacket.set()) {
                        if (userSessionPacket.password.equals(password)) {
                            state = 1;
                            httpSession.setAttribute("user", userSessionPacket);
                        }
                        else {
                            state = 5;
                        }
                    }
                    else {
                        state = 4;
                    }
                }
                else{
                    state =2;
                }
            }
            else if("0".equals(type)){
                //邮箱登录
                String email = req.getParameter("email");
                String password = req.getParameter("password");
                if(FormatCheckTool.checkEmail(email)&&FormatCheckTool.checkPassword(password)){
                    HttpSession httpSession = req.getSession();
                    BasicTool.clearSession(httpSession);
                    UserSessionPacket userSessionPacket = new UserSessionPacket();
                    userSessionPacket.email = email;
                    if (userSessionPacket.setByEmail()) {
                        if (userSessionPacket.password.equals(password)) {
                            state = 1;
                            httpSession.setAttribute("user", userSessionPacket);
                        }
                        else {
                            state = 5;
                        }
                    }
                    else {
                        state = 4;
                    }
                }
                else{
                    state =2;
                }
            }
            else{
                state = 2;
            }
        }
        else{
            state = 3;
        }
        resp.getWriter().write(BasicTool.getStateStr(state));
    }
}