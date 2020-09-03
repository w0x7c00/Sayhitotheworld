package web.servlet.adminManage;

import tool.BasicTool;
import tool.FormatCheckTool;
import tool.PermissionCheck;
import web.dataBasePacket.Teacher;
import web.email.BasicEmailTool;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//输入字段： self_video   ----- 254位以内的视频url地址    非空
//         teacher_name  ---- 教师名    非空
//输出字段： state   -1 成功    -2 字段错误    -3 无权限   -4 教师名不存在   -5 通知邮件发送失败  -6 服务器故障、字段超长
//流程：   1 检测字段    2 检测权限    3 检测教师存在    4 检测数据库修改情况
@WebServlet("/activeTeacherApply")
public class ActiveTeacherApply extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        BasicTool.setCharacterEncoding(req, resp);
        int state = 1;
        String self_video = req.getParameter("self_video");
        String teacher_name = req.getParameter("teacher_name");
        if(FormatCheckTool.checkNotNull(self_video)
        &&FormatCheckTool.checkMaxLength(self_video,254)
        &&FormatCheckTool.checkNotNull(teacher_name)){
            PermissionCheck permissionCheck = new PermissionCheck(req.getSession());
            if(permissionCheck.checkAdmin()){
                Teacher teacher = new Teacher();
                teacher.teacher_name= teacher_name;
                if(teacher.set()){
                    teacher.self_video = self_video;
                    teacher.state = 1;
                    if(teacher.update()){
                        if(BasicEmailTool.sendMail(teacher.email,"Sayhitotheworld:Your teacher account have been active!")){
                            state =1;
                        }
                        else{
                            state =5;
                        }
                    }
                    else{
                        state =6;
                    }
                }
                else{
                    state =4;
                }
            }
            else{
                //无权限
                state =3;
            }
        }
        else{
            state = 2;
        }
        resp.getWriter().write(BasicTool.getStateStr(state));
    }
}