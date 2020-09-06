package web.servlet.data.common;

import com.google.gson.Gson;
import tool.BasicTool;
import tool.FormatCheckTool;
import tool.PermissionCheck;
import web.dataBasePacket.Teacher;
import web.sessionPacket.TeacherSessionPacket;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

//输入字段:  - teacher_name
//输出字段： - state     1:成功     2：字段错误     3:无权限   4：无法找到教师信息    5:服务器故障
// - 改变以后的Teacher    - 普通用户   屏蔽密码、balance 当state！=1时 无法获取     - admin   屏蔽密码     - super admin   不屏蔽
//权限：    目标teacher、superAdmin：获取全部信息       普通用户  state为1的信息（屏蔽balance password）      admin  屏蔽password
//流程：    1-字段检查     2-教师查询     3-权限查询     4-屏蔽处理     5-返回信息
@WebServlet("/getTeacherInformation")
public class GetTeacherInformation extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        BasicTool.setCharacterEncoding(req, resp);
        int state = 0;
        Teacher teacher = null;
        String teacher_name = (String) req.getAttribute("teacher_name");
        //用于转发判断
        if(teacher_name==null){
            req.getParameter("teacher_name");
        }
        if(FormatCheckTool.checkNotNull(teacher_name)){
            teacher = new Teacher();
            teacher.teacher_name = teacher_name;
            if(teacher.set()){
                this.mask(teacher,req.getSession());
                if(teacher==null){
                    state =3;     //这种情况是不会存在的 但是必须要写
                }
                state = 1;
            }
            else{
                teacher = null;
                state = 4;
            }
        }
        else{
            state = 2;
        }
        Gson gson = new Gson();
        GetTeacherInformationReturnPacket getTeacherInformationReturnPacket = new GetTeacherInformationReturnPacket();
        getTeacherInformationReturnPacket.state = state;
        getTeacherInformationReturnPacket.data = teacher;
        resp.getWriter().write(gson.toJson(getTeacherInformationReturnPacket));
    }

    //根据不同的权限来进行返回数据的处理
    //如果是没有权限的话 teacher的值为null
    //权限的检查是由高到低的
    public void mask(Teacher teacher, HttpSession httpSession){
        PermissionCheck permissionCheck = new PermissionCheck(httpSession);
        boolean teacher_flag = true;
        if(httpSession.getAttribute("teacher")==null){
            teacher_flag=false;
        }
        else{
            if(teacher.teacher_name.equals(((TeacherSessionPacket)httpSession.getAttribute("teacher")).teacher_name)){

            }
            else{
                teacher_flag=false;
            }
        }
        if(permissionCheck.checkSuperAdmin()||teacher_flag){
            //super、target_teacher
            //do nothing
        }
        else if(permissionCheck.checkAdmin()){
            //admin
            teacher.password = null;
        }
        else{
            //普通
            if(teacher.state!=1){
                //当state不可用时 将不返回teacher
                teacher = null;
            }
            else{
                teacher.password = null;
                teacher.balance = -1;
            }
        }
    }

    public class GetTeacherInformationReturnPacket{
        public Teacher data;
        public int state;
    }
}