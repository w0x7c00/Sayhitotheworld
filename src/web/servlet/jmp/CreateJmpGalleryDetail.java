package web.servlet.jmp;

import tool.BasicTool;
import web.sessionPacket.JmpSessionPacket;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import javax.servlet.http.HttpSession;

//输入字段:  teacher_name
//输出字段： state    - 1 成功   -2 字段错误

@WebServlet("/createJmpGalleryDetail")
public class CreateJmpGalleryDetail extends HttpServlet{
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        BasicTool.setCharacterEncoding(req, resp);
        int state = 0;
        String teacher_name = req.getParameter("teacher_name");
        if(teacher_name==null){
            state = 2;
        }
        else{
            HttpSession httpSession = req.getSession();
            JmpSessionPacket jmpSessionPacket = (JmpSessionPacket)httpSession.getAttribute("jmp");
            if(jmpSessionPacket==null){
                //生成新的jmp
                jmpSessionPacket = new JmpSessionPacket();
                jmpSessionPacket.gallery_detail = teacher_name;
                httpSession.setAttribute("jmp",jmpSessionPacket);
            }
            else{
                jmpSessionPacket.gallery_detail = teacher_name;
            }
            state = 1;
        }
        resp.getWriter().write(BasicTool.getStateStr(state));
    }
}