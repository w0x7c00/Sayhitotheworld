package web.servlet.adminManage;

import com.google.gson.Gson;
import sql.BasicSQLRunner;
import tool.BasicTool;
import tool.PermissionCheck;
import web.dataBasePacket.Teacher;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

//输入字段：空
//输出字段：state:    -1 成功     -2  无相关权限或者登录过期
//        data:  [{},{},{}]形式
@WebServlet("/getTeacherApply")
public class GetTeacherApply extends HttpServlet{
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        BasicTool.setCharacterEncoding(req, resp);
        int state = 0;
        List<Teacher> teacher_list = new ArrayList<Teacher>();
        //检查权限
        PermissionCheck permissionCheck = new PermissionCheck(req.getSession());
        if(permissionCheck.checkAdmin()){
            BasicSQLRunner basicSQLRunner = new BasicSQLRunner();
            ResultSet rs = basicSQLRunner.unsafeQuery("select * from teacher where state = 0");
            while(true){
                Teacher item = new Teacher();
                if(item.setWithResultSet(rs)){
                    item.password = null;
                    item.pic = "/uploadImage/"+item.pic;
                    teacher_list.add(item);
                }
                else{
                    break;
                }
            }
            basicSQLRunner.close();
            state = 1;
        }
        else{
            state = 2;
        }
        GetTeacherApplyReturnPacket data = new GetTeacherApplyReturnPacket();
        data.state = state;
        data.data = teacher_list;
        Gson gson = new Gson();
        resp.getWriter().write(gson.toJson(data));
    }
    public class GetTeacherApplyReturnPacket{
        public int state;
        public List<Teacher> data;
    }
}