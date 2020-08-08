package web.servlet.logAndRegister;

import tool.BasicTool;
import web.dataBasePacket.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//输入前提：需要在web端初次检查输入字段
//输入字段：user_name  password email emailCode

//返回字段：state： -0 验证码不正确/重新发送    -1 成功注册   -2 格式不正确   -3 已存在的用户名    -4 未发送过验证码（没有session）   -5 数据库/服务器故障

//结构：
//1.检查输入字段    2.检查用户名可用性  3.检查邮箱信息与邮箱码对应情况并且检查邮箱码是否正确     4.插入数据库    5.删除session
@WebServlet("/userRegister")
public class UserRegister extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int state = 0;
        BasicTool.setCharacterEncoding(req,resp);
        String user_name = req.getParameter("user_name");


        //检查输入字段



        //检查用户名可用性
        User user = new User();
        user.user_name = user_name;
        if(user.set()){
            //已存在的用户名
            state = 3;
        }
        else {
            //可用用户名
        }
    }
}