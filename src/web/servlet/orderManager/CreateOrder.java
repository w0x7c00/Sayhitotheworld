package web.servlet.orderManager;

import tool.BasicTool;
import tool.FormatCheckTool;
import web.dataBasePacket.Order;
import web.dataBasePacket.Teacher;
import web.dataBasePacket.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

//输入字段： teacher_name  time   Email（联系team使用的email）
//输出字段： state：   -0 保留    -1 成功创建订单    -2 teacher_name不正确    -3 未登录   -4 order创建错误   -5 余额不足   -6 不正确字段   -7 email格式不正确
//流程： 1.检查是否登录    2.检查是否为空字段   3.检查邮箱格式    3.检查teacher是否存在        5.检查余额    6.扣除余额，创建订单     7.发送教师通知信息（可以使用email的方式） 或者发送通知信息
@WebServlet("/createOrder")
public class CreateOrder extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        BasicTool.setCharacterEncoding(req, resp);
        int state = 0;
        HttpSession httpSession = req.getSession();
        if(httpSession.getAttribute("user")==null){
            //未登录
            state=3;
        }
        else{
            String teacher_name = req.getParameter("teacher_name");
            int time = 0;
            try{
                time=Integer.parseInt(req.getParameter("time"));
            }
            catch (NumberFormatException e){
                time=0;
            }
            String email = req.getParameter("email");
            if(teacher_name == null || time == 0 || email == null){
                state = 6;
            }
            else{
                if(FormatCheckTool.checkEmail(email)){
                    //合法邮箱
                    Teacher teacher = new Teacher();
                    teacher.teacher_name = teacher_name;
                    if(teacher.set()){
                        //teacher存在
                        User user = (User) httpSession.getAttribute("user");
                        int amount = teacher.price*time;
                        if(amount>user.balance){
                            //余额不足
                            state=5;
                        }
                        else{
                            //创建订单
                            Order order = new Order();
                            order.email = email;
                            order.teacher_name = teacher_name;
                            order.state = 0; //未处理订单
                            order.amount = amount;
                            order.create_time = System.currentTimeMillis();
                            order.user_name = user.user_name;
                            if(order.insert()){
                                //创建成功
                                user.balance = user.balance-amount;
                                user.update();
                                //发送订单信息通知
                                this.sendOrderMsgToTeacher(order);
                                state = 1;
                            }
                            else{
                                state = 4;
                            }
                        }
                    }
                    else{
                        //teacher不存在
                        state =2;
                    }
                }
                else{
                    //不合法email
                    state = 7;
                }
            }
        }
        resp.getWriter().write("{state:"+state+"}");
    }

    private void sendOrderMsgToTeacher(Order order){
        return;
    }
}