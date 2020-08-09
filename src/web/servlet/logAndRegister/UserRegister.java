package web.servlet.logAndRegister;

import tool.BasicTool;
import tool.FormatCheckTool;
import web.dataBasePacket.User;
import web.sessionPacket.RegisterSessionPacket;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

//输入前提：需要在web端初次检查输入字段
//输入字段：user_name  password email emailCode name

//返回字段：state： -0 验证码不正确/重新发送    -1 成功注册   -2 格式不正确   -3 已存在的用户名    -4 未发送过验证码（没有session）   -5 数据库/服务器故障

//结构：
//1.检查输入字段    2.检查用户名可用性 3.检查是否有session  4.检查邮箱信息与邮箱码对应情况并且检查邮箱码是否正确     5.插入数据库    6.删除session
@WebServlet("/userRegister")
public class UserRegister extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        BasicTool.setCharacterEncoding(req,resp);
        int state = 0;
        String email = req.getParameter("email");
        String user_name = req.getParameter("user_name");
        String password = req.getParameter("password");
        String emailCode = req.getParameter("emailCode");
        String name = req.getParameter("name");
        //检查输入字段
        //email格式不用检查
        //name、user_name、password格式必须要检查
        if(user_name==null||password==null||emailCode==null||name==null||email==null){
            state = 2;//不正确的字段或者格式
        }
        else if(FormatCheckTool.checkPassword(password)&&FormatCheckTool.checkName(name)&&FormatCheckTool.checkUserName(user_name)){
            //检查用户名是否存在
            User user = new User();
            user.user_name = user_name;
            if(user.set()){
                //已存在的用户名
                state = 3;
            }
            else {
                //可用用户名
                //检查email以及对应的邮箱码
                HttpSession httpSession = req.getSession();
                RegisterSessionPacket registerSessionPacket = (RegisterSessionPacket) httpSession.getAttribute("userRegister");
                if(registerSessionPacket==null){
                    //没有这个session
                    state = 4;
                }
                else{
                    //存在这个session
                    if(registerSessionPacket.checkEmailCode(email,emailCode)){
                        //验证码检查成功
                        //插入记录
                        user.name = name;
                        user.user_name = user_name;
                        user.password = password;
                        user.email = email;
                        user.balance = 0;
                        user.create_time = System.currentTimeMillis();
                        if(user.insert()){
                            //插入数据成功
                            //删除session
                            httpSession.removeAttribute("userRegister");
                            state = 1;
                        }
                        else{
                            //插入数据失败 数据库故障
                            state = 5;
                        }
                    }
                    else{
                        state = 0;
                    }
                }
            }
        }
        else{
            //格式检查不通过
            state = 2;
        }
        resp.getWriter().write("{state:"+state+"}");
    }
}