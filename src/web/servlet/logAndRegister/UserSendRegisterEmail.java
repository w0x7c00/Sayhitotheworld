package web.servlet.logAndRegister;

import tool.BasicTool;
import tool.FormatCheckTool;
import web.email.BasicEmailTool;
import web.servlet.logAndRegister.sql.UserSearchEmail;
import web.sessionPacket.RegisterSessionPacket;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

//前提：浏览器本地检查字段格式并且通过
//输入字段：email
//输出字段： state     -0 发送邮件失败，检查邮箱格式或者检查邮箱是否存在        -1 发送邮件成功    -2 请求间隔时间过短    -3 邮件格式错误    -4 已经绑定过的邮箱
//         appendInf     当state为2时值为间隔的时间/ms
@WebServlet("/userSendRegisterEmail")
public class UserSendRegisterEmail extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        BasicTool.setCharacterEncoding(req,resp);
        HttpSession httpSession = req.getSession();
        BasicTool.clearSession(httpSession);
        int state = 0;
        long appendInf = 0;
        String email = req.getParameter("email");
        if(email==null||(!FormatCheckTool.checkEmail(email))){
            state = 3;
        }
        else{
            UserSearchEmail userSearchEmail = new UserSearchEmail();
            boolean result = userSearchEmail.checkEmailIsAvailable(email);
            userSearchEmail.close();
            if(!result){
                //不可用的邮箱
                state = 4;
            }
            else {
                if(httpSession.getAttribute("userRegister")==null){
                    //没有发送邮件记录即可发送邮件并且创建session
                    String emailCode = BasicTool.generateRandomEmailCode();
                    if(BasicEmailTool.sendMail(email,emailCode)){
                        //发送成功
                        state = 1;
                        //创建session
                        RegisterSessionPacket registerSessionPacket = new RegisterSessionPacket();
                        registerSessionPacket.set(email,emailCode);
                        httpSession.setAttribute("userRegister",registerSessionPacket);
                    }
                    else{
                        //发送失败
                        state = 0;
                    }
                }
                else{
                    //有发送记录则检查时间间隔
                    RegisterSessionPacket registerSessionPacket = (RegisterSessionPacket) httpSession.getAttribute("userRegister");
                    String emailCode = BasicTool.generateRandomEmailCode();
                    long divide_time = registerSessionPacket.set(email,emailCode);
                    if(divide_time == -1){
                        //成功重置
                        if(BasicEmailTool.sendMail(email,emailCode)){
                            //发送成功
                            state = 1;
                        }
                        else{
                            //发送失败
                            state = 0;
                        }
                    }
                    else{
                        //重置失败 时间不够
                        //设置state 并且添加附加信息为时间间隔
                        state = 2;
                        appendInf = divide_time;
                    }
                }
            }
        }
        resp.getWriter().write("{state:"+state+",appendInf:"+appendInf+"}");
    }
}