package web.servlet.data;

import com.google.gson.Gson;
import tool.BasicTool;
import web.sessionPacket.AdminSessionPacket;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//描述：当登录进入管理员界面以后 返回相应的管理员界面初始化信息
//无输入字段
//输出字段： state：  - 0 保留     -1 成功     -2 未登录
//         data:
@WebServlet("/adminBasicInformation")
public class AdminBasicInformation extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        BasicTool.setCharacterEncoding(req, resp);
        int state = 0;
        AdminSessionPacket adminSessionPacket =(AdminSessionPacket)req.getSession().getAttribute("admin");
        if(adminSessionPacket == null){
            //未登录
            state = 2;
        }
        else{
            //登录后
            state = 1;
        }
        AdminBasicInformationPacket adminBasicInformationPacket = new AdminBasicInformationPacket(adminSessionPacket,state);
        Gson gson = new Gson();
        resp.getWriter().write(gson.toJson(adminBasicInformationPacket));
    }

    public class AdminBasicInformationPacket{
        private int state;
        private String admin_name;
        private short type;
        private long create_time;
        public AdminBasicInformationPacket(AdminSessionPacket adminSessionPacket,int state){
            this.state = state;
            if(adminSessionPacket!=null){
                this.admin_name = adminSessionPacket.admin_name;
                this.type = adminSessionPacket.type;
                this.create_time = adminSessionPacket.create_time;
            }
        }
    }
}