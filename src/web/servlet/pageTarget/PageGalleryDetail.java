package web.servlet.pageTarget;

import tool.BasicTool;
import web.sessionPacket.JmpSessionPacket;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/pageGalleryDetail")
public class PageGalleryDetail extends HttpServlet{
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        BasicTool.setGetPageEncoding(req, resp);
        String teacher_name = req.getParameter("teacher_name");
        if(teacher_name==null){
            resp.getWriter().write("<a href=\"/\">SayHiToTheWorld</a>:您的链接不正确");
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
            req.getRequestDispatcher("/gallery_detail.html").forward(req,resp);
        }
    }
}