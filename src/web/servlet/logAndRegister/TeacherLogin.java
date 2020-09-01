package web.servlet.logAndRegister;

import tool.BasicTool;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//输入字段 teacher_name/email password  type: 0-邮箱登录 1-用户名登录
//输出字段：state  -0 登陆失败 -1 登录成功   -2 字段错误    -3 已经登录过   -4 教师用户名或邮箱不存在  -5 密码不正确
public class TeacherLogin extends HttpServlet{
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        BasicTool.setCharacterEncoding(req, resp);

    }
}
