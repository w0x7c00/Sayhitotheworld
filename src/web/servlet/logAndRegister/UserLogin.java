package web.servlet.logAndRegister;

import tool.BasicTool;
import web.sessionPacket.UserSessionPacket;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

//输入字段 user_name/email password  type: 0-邮箱登录 1-用户名登录
//输出字段：state  -0 登陆失败（账号或密码不正确/附加state 预留）    -1 登录成功     -2 登录失败（已经登录过了）   -3 登录失败（用户名不正确） -4 登录失败（密码不正确）   -5 登陆失败 （输入字段不正确）
//-6 邮箱不正确   -7 不正确的type值
@WebServlet("/userLogin")
public class UserLogin extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        BasicTool.setCharacterEncoding(req,resp);
        int state = 0;
        String type = req.getParameter("type");
        if("1".equals(type)){
            //用户名登陆
            String user_name = req.getParameter("user_name");
            String password = req.getParameter("password");
            if(user_name == null || password ==null){
                state = 5;
            }
            else{
                HttpSession httpSession = req.getSession();
                if(httpSession.getAttribute("user")!=null){
                    state = 2;
                }
                else {
                    BasicTool.clearSession(httpSession);
                    UserSessionPacket userSessionPacket = new UserSessionPacket();
                    userSessionPacket.user_name = user_name;
                    if(userSessionPacket.set()){
                        if(userSessionPacket.password.equals(password)){
                            state =1;
                            httpSession.setAttribute("user",userSessionPacket);
                        }
                        else{
                            state =4;
                        }
                    }
                    else{
                        state = 3;
                    }
                }
            }
        }
        else if("0".equals(type)){
            //邮箱登录
            String email = req.getParameter("email");
            String password = req.getParameter("password");
            if(email==null||password==null){
                state = 5;
            }
            else{
                HttpSession httpSession = req.getSession();
                if(httpSession.getAttribute("user")!=null){
                    state = 2;
                }
                else{
                    BasicTool.clearSession(httpSession);
                    UserSessionPacket userSessionPacket = new UserSessionPacket();
                    userSessionPacket.email = email;
                    if(userSessionPacket.setByEmail()){
                        if(userSessionPacket.password.equals(password)){
                            state =1;
                            httpSession.setAttribute("user",userSessionPacket);
                        }
                        else{
                            state =4;
                        }
                    }
                    else{
                        state = 6;
                    }
                }
            }
        }
        else{
            state = 7;
        }
        resp.getWriter().write("{state:"+state+"}");
    }
}