package web.servlet.data.common;

import com.google.gson.Gson;
import tool.BasicTool;
import tool.FormatCheckTool;
import tool.PermissionCheck;
import web.dataBasePacket.Teacher;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

//输入字段:  - teacher_name
//输出字段： - state     1:成功     2：字段错误    3：无法找到教师信息
// - 改变以后的Teacher    - 普通用户   屏蔽密码、balance 当state！=1时 无法获取     - admin   屏蔽密码     - super admin   不屏蔽
@WebServlet("/getTeacherInformation")
public class GetTeacherInformation extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        BasicTool.setCharacterEncoding(req, resp);
        int state = 0;
        Teacher teacher = null;
        String teacher_name = req.getParameter("teacher_name");
        if(FormatCheckTool.checkNotNull(teacher_name)){
             teacher = new Teacher();
            teacher.teacher_name = teacher_name;
            if(teacher.set()){
                this.mask(teacher,req.getSession());
                state = 1;
            }
            else{
                state = 3;
            }
        }
        else{
            state = 2;
        }
        Gson gson = new Gson();
        GetTeacherInformationReturnPacket getTeacherInformationReturnPacket = new GetTeacherInformationReturnPacket();
        getTeacherInformationReturnPacket.state = state;
        getTeacherInformationReturnPacket.teacher = teacher;
        resp.getWriter().write(gson.toJson(getTeacherInformationReturnPacket));
    }

    //根据不同的权限来进行返回数据的处理
    public void mask(Teacher teacher, HttpSession httpSession){
        PermissionCheck permissionCheck = new PermissionCheck(httpSession);
        if(permissionCheck.checkSuperAdmin()){
            //super
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
        public Teacher teacher;
        public int state;
    }
}