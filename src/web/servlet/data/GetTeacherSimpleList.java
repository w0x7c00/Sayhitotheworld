package web.servlet.data;

import com.google.gson.Gson;
import com.sun.org.apache.bcel.internal.generic.RET;
import sql.BasicSQLRunner;
import tool.BasicTool;
import tool.PermissionCheck;
import web.dataBasePacket.Teacher;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

//输入字段： type：1-state为1/用户或未登录及其以上权限/屏蔽password、balance   ------用于面向用户的信息展示
//               2-state为2/管理员及以上/屏蔽password     ------用于教师审核
//               3-state为1/admin以上权限/屏蔽password   ------用于面向管理员的教师信息展示
//               4-state为123/admin及以上权限/屏蔽password   ------用于面向管理员的教师信息展示
//               5-state为123/super admin及以上权限/不屏蔽password   --------用于超级管理员教师信息展示
//         begin: 起始索引（int 从0开始）
//         length: 要求长度(int)    当此为0是要做特别的优化

//输出字段： state:  1 成功   2 字段错误   3 无权限   4 服务器故障
//         max_length: 返回最长长度
//         data: Teacher对象为item的数组   返回内容长度直接使用js获取就行了
@WebServlet("/getTeacherSimpleList")
public class GetTeacherSimpleList extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        BasicTool.setCharacterEncoding(req, resp);
        String type = req.getParameter("type");
        int begin_int = BasicTool.str2int(req.getParameter("begin"));
        int length_int = BasicTool.str2int(req.getParameter("length"));
        if(checkBegin(begin_int)&&checkLength(length_int)){
            GetTeacherSimpleListReturnPacket g = this.createReturnPacket(BasicTool.str2int(type), req.getSession(),begin_int,length_int);
            resp.getWriter().write(new Gson().toJson(g));
        }
        else{
            resp.getWriter().write(BasicTool.getStateStr(2));   //字段格式不正确
        }
    }

    private GetTeacherSimpleListReturnPacket createReturnPacket(int type_int, HttpSession httpSession,int begin_int,int length_int){
        GetTeacherSimpleListReturnPacket g = new GetTeacherSimpleListReturnPacket();
        PermissionCheck permissionCheck = new PermissionCheck(httpSession);
        BasicSQLRunner sqlRunner = new BasicSQLRunner();
        switch (type_int){
            case 1:

        }

        sqlRunner.close();
        return null;
    }

    public class GetTeacherSimpleListReturnPacket{
        public int state;
        public List<Teacher> teacherList;
    }

    public boolean checkBegin(int begin_int){
        return begin_int >= 0;
    }

    public boolean checkLength(int length_int){
        return length_int >= 0;
    }
}