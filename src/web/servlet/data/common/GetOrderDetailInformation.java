package web.servlet.data.common;

import com.google.gson.Gson;
import tool.BasicTool;
import tool.FormatCheckTool;
import tool.PermissionCheck;
import web.dataBasePacket.Comment;
import web.dataBasePacket.Order;
import web.sessionPacket.TeacherSessionPacket;
import web.sessionPacket.UserSessionPacket;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

//简介：获取目标订单的所有详细信息（包括订单的评论）
//权限要求：对应订单的user/teacher或者admin
//输入：type：    -1 用户获取    -2 教师获取    -3 管理员获取
//     order_id:
//输出：state：    -1 成功    -2 字段错误   -3 无权限    -4 不存在的id   -5 服务器故障
//     order
//     comment
//流程：   1 字段检查   2 id检查   3 权限检查   4 查询
@WebServlet("/getOrderDetailInformation")
public class GetOrderDetailInformation extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        BasicTool.setCharacterEncoding(req, resp);
        GetOrderDetailInformationReturnPacket g = new GetOrderDetailInformationReturnPacket();
        int state = 0;
        String type = req.getParameter("type");
        int order_id = BasicTool.str2int(req.getParameter("order_id"));
        if(FormatCheckTool.checkNotNull(type)&&order_id!=-1){
            int type_int = BasicTool.str2int(type);
            Order order = new Order();
            Comment comment = new Comment();
            order.order_id = order_id;
            comment.order_id = order_id;
            if(order.set()&&comment.set()){
                if(typeIn123(type_int)){
                    if (checkPerAndFullReturnPacket(g, type_int, req.getSession(), order, comment)) {
                        state = 1;
                    } else {
                        state = 3;
                    }
                }
                else{
                    state = 2;
                }
            }
            else {
                state = 4;
            }
        }
        else{
            state = 2;
        }
        g.state = state;
        resp.getWriter().write(new Gson().toJson(g));
    }
    public boolean checkPerAndFullReturnPacket(GetOrderDetailInformationReturnPacket g,int type_int,HttpSession httpSession,Order order,Comment comment){
        switch (type_int){
            case 1:
                UserSessionPacket userSessionPacket = (UserSessionPacket) httpSession.getAttribute("user");
                if(userSessionPacket==null){
                    return false;
                }
                else{
                    if(userSessionPacket.user_name.equals(order.user_name)){
                        //权限检查通过
                        g.order = order;
                        g.comment = comment;
                    }
                    else{
                        return false;
                    }
                }
                break;
            case 2:
                TeacherSessionPacket teacherSessionPacket = (TeacherSessionPacket) httpSession.getAttribute("teacher");
                if(teacherSessionPacket==null){
                    return false;
                }
                else{
                    if(teacherSessionPacket.teacher_name.equals(order.teacher_name)){
                        //权限检查通过
                        g.order = order;
                        g.comment = comment;
                    }
                    else{
                        return false;
                    }
                }
                break;
            case 3:
                PermissionCheck permissionCheck = new PermissionCheck(httpSession);
                if(permissionCheck.checkAdmin()){
                    g.order = order;
                    g.comment = comment;
                }
                else {
                    return false;
                }
                break;
        }
        return false;
    }
    public boolean typeIn123(int type_int){
        return type_int == 1 || type_int == 2 || type_int == 3;
    }

    public class GetOrderDetailInformationReturnPacket{
        int state;
        Order order;
        Comment comment;
    }
}