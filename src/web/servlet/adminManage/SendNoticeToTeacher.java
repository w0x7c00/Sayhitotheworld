package web.servlet.adminManage;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import tool.BasicTool;
import tool.FormatCheckTool;
import tool.PermissionCheck;
import tool.ThreadBasic;
import web.email.MultiSendTeacherEmailPacket;
import web.email.ThreadFunctionEmail;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;


//输入字段： title：通知标题      text：通知内容
//          receivers：接收者列表字符串
//输出字段： state   -1 成功    -2 字段错误    -3 无权限
@WebServlet("/sendNoticeToTeacher")
public class SendNoticeToTeacher extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        BasicTool.setCharacterEncoding(req, resp);
        int state = 0;
        String title = req.getParameter("title");
        String text = req.getParameter("text");
        String receivers_str = req.getParameter("receivers");
        List<String> receivers = null;
        if (FormatCheckTool.checkNotNull(title)&&FormatCheckTool.checkNotNull(text)&&FormatCheckTool.checkNotNull(receivers_str)){
            receivers=parseReceiverList(receivers_str);
            if(receivers!=null){
                //检查是否为管理员
                if(new PermissionCheck(req.getSession()).checkAdmin()){
                    MultiSendTeacherEmailPacket data = new MultiSendTeacherEmailPacket();
                    data.title=title;
                    data.text=text;
                    data.receivers = receivers;
                    //执行线程
                    new ThreadBasic(new ThreadFunctionEmail(),data,"MULTI SEND EMAIL").start();
                    state = 1;
                }
                else{
                    state = 3;
                }
            }
            else{
                state = 2;
            }
        }
        else{
            state = 2;
        }
        resp.getWriter().write(BasicTool.getStateStr(state));
    }
    private List<String> parseReceiverList(String recs_str){
        recs_str = "["+recs_str;
        recs_str+="]";
        try{
            List<String> receivers = new Gson().fromJson(recs_str,new TypeToken<List<String>>() {}.getType());
            System.out.println(123);
            return receivers;
        }
        catch (Exception e){
            System.out.println(e);
            return null;
        }
    }
}