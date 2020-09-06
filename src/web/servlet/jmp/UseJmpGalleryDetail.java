package web.servlet.jmp;

import tool.BasicTool;
import web.sessionPacket.JmpSessionPacket;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//输入输出字段要求见GetTeacherInformation
@WebServlet("/useJmpGalleryDetail")
public class UseJmpGalleryDetail extends HttpServlet{
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        BasicTool.setCharacterEncoding(req, resp);
        JmpSessionPacket j = ((JmpSessionPacket)req.getSession().getAttribute("jmp"));
        if(j==null){
            resp.getWriter().write(BasicTool.getStateStr(-1));
        }
        else{
            req.setAttribute("teacher_name",j.gallery_detail);
            req.getRequestDispatcher("/getTeacherInformation").forward(req,resp);
        }
    }
}