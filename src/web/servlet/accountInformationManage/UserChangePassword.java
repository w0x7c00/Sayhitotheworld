package web.servlet.accountInformationManage;

import tool.BasicTool;
import tool.FormatCheckTool;
import web.dataBasePacket.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//输入字段 user_name    old_password    new_password
//输出字段:  state: -0 预留     -1成功      -2 不正确用户名     -3 不正确old密码     -4 不正确new 密码格式    -5 不正确字段
//过程： 1.检查字段    2.检查new_password格式    3.验证用户名    4.验证old_password   5,修改密码   6.clear session
@WebServlet("/userChangePassword")
public class UserChangePassword extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        BasicTool.setCharacterEncoding(req, resp);
        String user_name = req.getParameter("user_name");
        String old_password = req.getParameter("old_password");
        String new_password = req.getParameter("new_password");
        int state =0 ;
        if(user_name==null||old_password==null||new_password==null){
            state = 5;
        }
        else{
            if(FormatCheckTool.checkPassword(new_password)){
                User user = new User();
                user.user_name = user_name;
                if(user.set()){
                    //存在这个用户名
                    if(user.password.equals(old_password)){
                        //密码验证通过
                        //更新密码
                        user.password = new_password;
                        user.update();
                        //删除session
                        BasicTool.clearSession(req.getSession());
                        state =1 ;
                    }
                }
                else{
                    //不存在这个用户名
                    state = 2;
                }
            }
            else{
                //新密码格式不正确
                state = 4;
            }
        }
    }
}