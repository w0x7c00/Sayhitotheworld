package web.servlet.data;

import com.google.gson.Gson;
import tool.BasicTool;
import tool.PermissionCheck;
import web.dataBasePacket.Teacher;
import web.servlet.data.sql.SQLRunnerForGetTeacherList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

//输入字段： type：1-state为1/用户或未登录及其以上权限/屏蔽password、balance   ------用于面向用户的信息展示
//               2-state为2/管理员及以上/屏蔽password     ------用于教师初次审核
//               3-state为3/管理员及以上/屏蔽password     ------用于教师再次审核
//               4-state为1/管理员以上权限/屏蔽password   ------用于面向管理员的教师信息展示
//               5-state为0123/admin及以上权限/屏蔽password   ------用于面向管理员的教师信息展示
//               6-state为0123/super admin及以上权限/不屏蔽password   --------用于超级管理员教师信息展示
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
        PermissionCheck permissionCheck = new PermissionCheck(httpSession);
        SQLRunnerForGetTeacherList sqlRunner = new SQLRunnerForGetTeacherList();
        List<Teacher> teacherList = new ArrayList<>();
        int state = 0;
        int max_length = -1;
        ResultSet rs =null;
        String preSQL = null;
        String maxLengthSQL = null;
        switch (type_int){
            case 1:
                preSQL = "select * from teacher where state = 1 limit ?,?";
                maxLengthSQL = "select count(*) as l from teacher where state = 1";
                rs = sqlRunner.getTeacherListRS(preSQL,begin_int,length_int);
                try{
                    while(rs!=null && rs.next()){
                        Teacher teacher_item = new Teacher();
                        teacher_item.setWithResultSet(rs);
                        teacher_item.password = null;
                        teacher_item.balance = -1;
                        teacherList.add(teacher_item);
                    }
                    state = 1;
                }
                catch (SQLException e){
                    state = 4;
                }
                break;
            case 2:
                if(permissionCheck.checkAdmin()){
                    preSQL = "select * from teacher where state = 2 limit ?,?";
                    maxLengthSQL = "select count(*) as l from teacher where state = 2";
                    rs = sqlRunner.getTeacherListRS(preSQL,begin_int,length_int);
                    try{
                        while(rs!=null && rs.next()){
                            Teacher teacher_item = new Teacher();
                            teacher_item.setWithResultSet(rs);
                            teacher_item.password = null;
                            teacherList.add(teacher_item);
                        }
                        state = 1;
                    }
                    catch (SQLException e){
                        state = 4;
                    }
                }
                else{
                    state = 3;
                }
                break;
            case 3:
                if(permissionCheck.checkAdmin()){
                    preSQL = "select * from teacher where state = 3 limit ?,?";
                    maxLengthSQL = "select count(*) as l from teacher where state = 3";
                    rs = sqlRunner.getTeacherListRS(preSQL,begin_int,length_int);
                    try{
                        while(rs!=null && rs.next()){
                            Teacher teacher_item = new Teacher();
                            teacher_item.setWithResultSet(rs);
                            teacher_item.password = null;
                            teacherList.add(teacher_item);
                        }
                        state = 1;
                    }
                    catch (SQLException e){
                        state = 4;
                    }
                }
                else{
                    state = 3;
                }
                break;
            case 4:
                if(permissionCheck.checkAdmin()){
                    preSQL = "select * from teacher where state = 1 limit ?,?";
                    maxLengthSQL = "select count(*) as l from teacher where state = 1";
                    rs = sqlRunner.getTeacherListRS(preSQL,begin_int,length_int);
                    try{
                        while(rs!=null && rs.next()){
                            Teacher teacher_item = new Teacher();
                            teacher_item.setWithResultSet(rs);
                            teacher_item.password = null;
                            teacherList.add(teacher_item);
                        }
                        state = 1;
                    }
                    catch (SQLException e){
                        state = 4;
                    }
                }
                else{
                    state = 3;
                }
                break;
            case 5:
                if(permissionCheck.checkAdmin()){
                    preSQL = "select * from teacher limit ?,?";
                    maxLengthSQL = "select count(*) as l from teacher";
                    rs = sqlRunner.getTeacherListRS(preSQL,begin_int,length_int);
                    try{
                        while(rs!=null && rs.next()){
                            Teacher teacher_item = new Teacher();
                            teacher_item.setWithResultSet(rs);
                            teacher_item.password = null;
                            teacherList.add(teacher_item);
                        }
                        state = 1;
                    }
                    catch (SQLException e){
                        state = 4;
                    }
                }
                else{
                    state = 3;
                }
                break;
            case 6:
                if(permissionCheck.checkSuperAdmin()){
                    preSQL = "select * from teacher limit ?,?";
                    maxLengthSQL = "select count(*) as l from teacher where state";
                    rs = sqlRunner.getTeacherListRS(preSQL,begin_int,length_int);
                    try{
                        while(rs!=null && rs.next()){
                            Teacher teacher_item = new Teacher();
                            teacher_item.setWithResultSet(rs);
                            teacherList.add(teacher_item);
                        }
                        state = 1;
                    }
                    catch (SQLException e){
                        state = 4;
                    }
                }
                else{
                    state = 3;
                }
                break;
            default:
                state = 2;
                break;
        }

        if(maxLengthSQL!=null){
            ResultSet rs_length = sqlRunner.unsafeQuery(maxLengthSQL);
            try{
                if(rs_length.next()&&rs_length!=null){
                    max_length=rs_length.getInt("l");
                }
                else{
                    //长度查询不正确
                    state = 4;
                }
            }
            catch (SQLException e){
                //长度查询不正确
                state = 4;
            }
        }
        sqlRunner.close();

        GetTeacherSimpleListReturnPacket g = new GetTeacherSimpleListReturnPacket();
        g.max_length = max_length;
        g.state = state;
        g.teacherList = teacherList;
        return g;
    }

    public class GetTeacherSimpleListReturnPacket{
        public int state;
        public List<Teacher> teacherList;
        public int max_length;
    }

    public boolean checkBegin(int begin_int){
        return begin_int >= 0;
    }

    public boolean checkLength(int length_int){
        return length_int >= 0;
    }

}