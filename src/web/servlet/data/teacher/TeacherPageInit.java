package web.servlet.data.teacher;

import com.google.gson.Gson;
import tool.BasicTool;
import web.dataBasePacket.Comment;
import web.dataBasePacket.Order;
import web.servlet.data.sql.SQLRunnerGetOrderInf;
import web.sessionPacket.TeacherSessionPacket;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

//注意 在这个pageinit中要自动检查超时未确认的订单 自动完成订单申请
//输入：无
//输出：state：   1-成功    2-未登录
//     data：
//流程：  1.检测登录状态   2.订单超时检测    3.屏蔽password并且json化   4.恢复session password
@WebServlet("/teacherPageInit")
public class TeacherPageInit extends HttpServlet{
    //订单超时时间检测
    final private long overTimeMs = 36000000;    //10小时
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        BasicTool.setCharacterEncoding(req, resp);
        TeacherSessionPacket teacherSessionPacket = (TeacherSessionPacket)req.getSession().getAttribute("teacher");
        TeacherPageInitReturnPacket g = new TeacherPageInitReturnPacket();
        String re_str = null;
        if(teacherSessionPacket==null){
            //未登录
            re_str = BasicTool.getStateStr(2);
        }
        else{
            //超时处理
            overTimeOrderHandle(teacherSessionPacket.teacher_name);
            String temp_psw = teacherSessionPacket.password;
            teacherSessionPacket.password=null;
            g.teacherSessionPacket = teacherSessionPacket;
            g.state = 1;
            re_str  = new Gson().toJson(g);
            teacherSessionPacket.password = temp_psw;
        }
        resp.getWriter().write(re_str);
    }

    public void overTimeOrderHandle(String teacher_name){
        SQLRunnerGetOrderInf sqlRunner = new SQLRunnerGetOrderInf();
        ResultSet rs = sqlRunner.getAllOrderByTeacherName(teacher_name);
        try{
            while(rs!=null&&rs.next()){
                Order order = new Order();
                if(order.setWithResultSet(rs)){
                    if(order.state==0){
                        if(System.currentTimeMillis()-order.create_time>=overTimeMs){
                            order.state=1;
                            order.update();
                            setComment(order.order_id);
                        }
                    }
                }
            }
        }
        catch (SQLException e){
            //出现无法查询的情况要打印日志 但是不对其他造成影响
            e.printStackTrace();
        }
        sqlRunner.close();
    }

    public boolean setComment(int order_id){
        Comment comment = new Comment();
        comment.order_id = order_id;
        comment.context = BasicTool.generateDefaultCommentText();
        comment.create_time = System.currentTimeMillis();
        comment.level = 5;//好评
        comment.update();
        return true;
    }

    public class TeacherPageInitReturnPacket{
        public TeacherSessionPacket teacherSessionPacket;    //这个teacher必须要屏蔽password字段   在屏蔽之后 json化后要恢复
        public int state;
    }
}