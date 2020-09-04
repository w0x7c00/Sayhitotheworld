package web.servlet.data.common;

import com.google.gson.Gson;
import tool.BasicTool;
import tool.FormatCheckTool;
import web.dataBasePacket.Order;
import web.servlet.data.sql.SQLRunnerGetOrderInf;
import web.sessionPacket.TeacherSessionPacket;

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

//输入字段：    type：    -1 获取state为0的订单信息   未完成订单       都需要Admin权限或者与teacher_name对应的teacher权限
//                      -2  获取state为1的订单信息   已完成订单
//                      -3 获取state为01的订单信息  未完成+已完成
//                      -4 获取state为012的订单信息  所有订单
//             teacher_name:
//输出字段：    state： -1 成功    -2 输入字段不正确    -3 无相关权限   -4 教师名不存在   -5 服务器故障  （注意 -4 是不会出现的 此处预留）
//             data
//流程：     1 检查字段    2 检查权限（teacher或者admin）  3 进行查询
@WebServlet("/getTeacherOrder")
public class GetTeacherOrderList extends HttpServlet{
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        BasicTool.setCharacterEncoding(req, resp);
        String type = req.getParameter("type");
        String teacher_name = req.getParameter("teacher_name");
        List<Order> orderList = new ArrayList<>();
        int state = 0;
        int type_int = BasicTool.str2int(type);
        if(FormatCheckTool.checkNotNull(teacher_name)){
            if(checkTeacherPer(req.getSession(),teacher_name)){
                switch (type_int){
                    case 1:
                        state=fullDataList(orderList,teacher_name,1);
                        break;
                    case 2:
                        state=fullDataList(orderList,teacher_name,2);
                        break;
                    case 3:
                        state=fullDataList(orderList,teacher_name,3);
                        break;
                    case 4:
                        state=fullDataList(orderList,teacher_name,4);
                        break;
                    default:
                        state = 2;
                        break;
                }
            }
            else{
                //无权限
                state=3;
            }
        }
        else{
            state=2;
        }
        GetTeacherOrderListReturnPacket g = new GetTeacherOrderListReturnPacket();
        g.state = state;
        g.data = orderList;
        resp.getWriter().write(new Gson().toJson(g));
    }
    private boolean checkTeacherPer(HttpSession httpSession,String teacher_name){
        TeacherSessionPacket t = (TeacherSessionPacket) httpSession.getAttribute("teacher");
        if(t==null){
            return false;
        }
        else {
            return teacher_name.equals(t.teacher_name);
        }
    }

    private int fullDataList(List<Order> ls,String teacher_name,int type){
        int state = 0;
        SQLRunnerGetOrderInf sqlRunner = new SQLRunnerGetOrderInf();
        ResultSet rs = sqlRunner.getAllOrderByTeacherName(teacher_name);
        try{
            Order order;
            while (rs!=null&&rs.next()){
                switch (type){
                    case 1:
                        order = new Order();
                        order.setWithResultSet(rs);
                        if(order.state==0){
                            ls.add(order);
                        }
                        break;
                    case 2:
                        order = new Order();
                        order.setWithResultSet(rs);
                        if(order.state==1){
                            ls.add(order);
                        }
                        break;
                    case 3:
                        order = new Order();
                        order.setWithResultSet(rs);
                        if(order.state==0||order.state==1){
                            ls.add(order);
                        }
                        break;
                    case 4:
                        order = new Order();
                        order.setWithResultSet(rs);
                        ls.add(order);
                        break;
                }
            }
            state = 1;
        }
        catch (SQLException e){
            e.printStackTrace();
            state = 5;
        }
        sqlRunner.close();
        return state;
    }

    public class GetTeacherOrderListReturnPacket{
        public int state;
        public List<Order> data;
    }
}