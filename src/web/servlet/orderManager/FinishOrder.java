package web.servlet.orderManager;

import tool.BasicTool;
import tool.FormatCheckTool;
import web.dataBasePacket.Comment;
import web.dataBasePacket.Order;
import web.dataBasePacket.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

//输入端：订单用户
//输入字段： level comment order_id
//输出字段：state： 1-成功   2-未登录   3-字段错误   4-order_id或者level不是可获取的int型  5-level范围不合法   6-comment不合法（敏感词）  7-comment为空  8-订单不存在  9-订单信息不匹配（state=1或者与用户不匹配）  10-订单评论插入失败
//流程：    1 检查字段   2 检查是否登录  3 检查order_id/level是否符合要求（可获取的int）  4 检查level范围   4 检查comment是否合法    5.检查comment是否为空字符串   5 检查订单是否存在   6.检查订单对应信息   7.添加订单评论 8.修改订单状态
@WebServlet("/finishOrder")
public class FinishOrder extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        BasicTool.setCharacterEncoding(req,resp);
        String comment = req.getParameter("comment");
        String level = req.getParameter("level");
        String order_id = req.getParameter("order_id");
        int state = 0 ;
        HttpSession httpSession = req.getSession();
        if(comment==null||level==null||order_id==null){
            if(httpSession.getAttribute("user")==null){
                //未登录
                state=2;
            }
            else{
                //检查order_id与level
                int level_int = -1;
                int order_id_int =-1;
                try{
                    level_int = Integer.parseInt(level);
                    order_id_int = Integer.parseInt(order_id);
                }
                catch (NumberFormatException e){
                    //do nothing
                }
                if(level_int == -1||order_id_int==-1){
                    //字段不可获取的int类型
                    state = 4;
                }
                else{
                    if(FormatCheckTool.checkLevel(level_int)){
                        if(FormatCheckTool.checkComment(comment)){
                            if(comment.equals("")){
                                state = 7;
                            }
                            else{
                                Order order = new Order();
                                order.order_id = order_id_int;
                                if(order.set()){
                                    User user = (User)httpSession.getAttribute("user");
                                    if(order.user_name.equals(user.user_name)&&order.state==0){
                                        //添加评论。。。。
                                        Comment comment1 = new Comment();
                                        comment1.context = comment;
                                        comment1.order_id = order_id_int;
                                        comment1.level = (short)level_int;
                                        comment1.create_time = System.currentTimeMillis();
                                        if(comment1.insert()){
                                            order.state = 1;
                                            order.update();
                                            state = 1;
                                        }
                                        else{
                                            state =10;
                                        }
                                    }
                                    else{
                                        state = 9;
                                    }
                                }
                                else{
                                    //不存在的订单
                                    state = 8;
                                }
                            }
                        }
                        else{
                            state = 6;
                        }
                    }
                    else{
                        state = 5;
                    }
                }
            }
        }
        else{
            //字段为空
            state =3;
        }
        resp.getWriter().write("{state:"+state+"}");
    }
}