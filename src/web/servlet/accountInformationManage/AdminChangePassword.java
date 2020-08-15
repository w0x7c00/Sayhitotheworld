package web.servlet.accountInformationManage;

import tool.BasicTool;
import tool.FormatCheckTool;
import web.dataBasePacket.Admin;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//输入字段 admin_name    old_password new_password
//输出字段:  state: -0 预留     -1成功    -2 不正确字段   -3 不正确用户名     -4 不正确old密码
//过程： 1.检查字段    2.验证用户名    3.验证old_password   4,修改密码   5.clear session
@WebServlet("/adminChangePassword")
public class AdminChangePassword extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        BasicTool.setCharacterEncoding(req, resp);
        String admin_name = req.getParameter("admin_name");
        String old_password = req.getParameter("old_password");
        String new_password = req.getParameter("new_password");
        int state =0 ;
        if(FormatCheckTool.checkAdminName(admin_name)&&FormatCheckTool.checkPassword(old_password)&&FormatCheckTool.checkPassword(new_password)){
            Admin admin = new Admin();
            admin.admin_name = admin_name;
            if(admin.set()){
                //存在这个用户名
                if(admin.password.equals(old_password)){
                    //密码验证通过
                    //更新密码
                    admin.password = new_password;
                    admin.update();
                    //删除session
                    BasicTool.clearSession(req.getSession());
                    state =1;
                }
                else{
                    state = 4;
                }
            }
            else{
                //不存在这个用户名
                state = 3;
            }
        }
        else{
            state = 2;
        }
        resp.getWriter().write(BasicTool.getStateStr(state));
    }
}