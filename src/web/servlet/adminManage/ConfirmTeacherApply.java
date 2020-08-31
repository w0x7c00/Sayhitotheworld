package web.servlet.adminManage;

import tool.BasicTool;
import tool.FormatCheckTool;
import web.dataBasePacket.Teacher;
import web.email.BasicEmailTool;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//方法：post
//输入字段： teacher_name：用于索引教师   state：   1/2        1---通过      2---不通过       append_inf 当为2时必须要append_inf
//输出字段：state   -1 成功     -2 字段错误     -3 查询不到此教师   -4 邮箱通知发送失败    -5 服务器/数据库问题

@WebServlet("/confirmTeacherApply")
public class ConfirmTeacherApply extends HttpServlet{
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int state = 0;
        BasicTool.setCharacterEncoding(req, resp);
        String state_str = req.getParameter("state");
        String teahcer_name = req.getParameter("teacher_name");
        Teacher teacher = new Teacher();
        teacher.teacher_name = teahcer_name;
        if(teacher.set()){
            if("1".equals(state_str)){
                teacher.state = 1;
                teacher.update();
                //发送邮箱的通知消息
                String msg = "<h1>Sayhitotheworld:<h1/> Congratulations on your passing the teacher application";
                if(BasicEmailTool.sendMail(teacher.email,msg)){
                    state = 1;
                }
                else {
                    state = 4;
                }
            }
            else if("2".equals(state_str)){
                String append_inf = req.getParameter("append_inf");
                if(FormatCheckTool.checkTeacherApplyAppendInf(append_inf)){
                    teacher.state =2;
                    teacher.append_inf = append_inf;
                    teacher.update();
                    //发送邮箱通知消息
                    String msg = "<h1>Sayhitotheworld:<h1/> You didn't pass the teacher application because of:<br/>"+append_inf;
                    if(BasicEmailTool.sendMail(teacher.email,msg)){
                        state = 1;
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
        else {
            state = 3;
        }
        resp.getWriter().write(BasicTool.getStateStr(state));
    }
}