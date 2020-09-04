package web.servlet.data.common;

import com.google.gson.Gson;
import tool.BasicTool;
import tool.FormatCheckTool;
import tool.PermissionCheck;
import web.dataBasePacket.User;
import web.sessionPacket.TeacherSessionPacket;
import web.sessionPacket.UserSessionPacket;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;


//输入：user_name
//输出： state：    -1 成功     -2 字段错误    -3 无权限      -4 无此user     -5 服务器故障
//      data
//权限：    SA/target_user: 所有      A:屏蔽密码       其他:屏蔽密码 balance
//流程：   1-字段检查    2-用户获取    3-权限检查
@WebServlet("/getUserInformation")
public class GetUserInformation extends HttpServlet{
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        BasicTool.setCharacterEncoding(req, resp);
        int state = 0;
        User user = null;
        String user_name = req.getParameter("user_name");
        if(FormatCheckTool.checkNotNull(user_name)){
            user = new User();
            user.user_name=user_name;
            if(user.set()) {
                this.mask(user,req.getSession());
                if(user==null){
                    state = 3;
                }
                else{
                    state =1;
                }
            }
            else {
                user=null;
                state = 4;
            }
        }
        else{
            state =2;
        }
        GetUserInformationReturnPacket g = new GetUserInformationReturnPacket();
        g.state = state;
        g.data = user;
        resp.getWriter().write(new Gson().toJson(g));
    }

    private void mask(User user, HttpSession httpSession){
        PermissionCheck permissionCheck = new PermissionCheck(httpSession);
        if(permissionCheck.checkSuperAdmin()||user.user_name.equals(((UserSessionPacket)httpSession.getAttribute("User")).user_name)){
            //super、target_teacher
            //do nothing
        }
        else if(permissionCheck.checkAdmin()){
            //admin
            user.password = null;
        }
        else{
            //普通
            user.balance = -1;
            user.password = null;
        }
    }

    public class GetUserInformationReturnPacket{
        public User data;
        public int state;
    }
}