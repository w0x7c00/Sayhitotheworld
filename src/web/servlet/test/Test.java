package web.servlet.test;


import com.google.gson.Gson;
import sql.BasicSQLRunner;
import tool.BasicTool;
import tool.FormatCheckTool;
import tool.Logging;
import tool.ThreadBasic;
import web.email.BasicEmailTool;
import web.email.MultiSendTeacherEmailPacket;
import web.email.ThreadFunctionEmail;
import web.pay.AliPay;
import web.pay.AlipayQueryPacket;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

@WebServlet("/test")
public class Test extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        BasicTool.setGetPageEncoding(req,resp);
//        AliPay aliPay = new AliPay();
//        //String form = aliPay.createOrderForm(113,1000);
//        AlipayQueryPacket f2 = aliPay.queryOrderStatus(113);
//        System.out.println(new Gson().toJson(f2));
//        //resp.getWriter().write(form==null?"null":form);
        BasicTool.setCharacterEncoding(req, resp);
//        MultiSendTeacherEmailPacket data = new MultiSendTeacherEmailPacket();
//        data.title="title";
//        data.text="text";
//        List<String> list = new ArrayList<String>();
//        data.receivers= list;
//        list.add("user1");
//        list.add("user2");
//        new ThreadBasic(new ThreadFunctionEmail(),data,"email thread").start();
//        resp.getWriter().write("123");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String result = BasicTool.handlePicUpload(req);
        int state = 0;
        if(result!=null){
            state=1;
        }
        else{
            state=0;
        }
        System.out.println(null+"");
        resp.getWriter().write("{\"state\":"+state+",\"append_inf\":"+result+"}");
    }
}