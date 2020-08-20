package web.servlet.teacherRegister;

import tool.BasicTool;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/uploadFile")
public class uploadFile extends HttpServlet {
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
        resp.getWriter().write("{\"state\":"+state+",\"append_inf\":\""+result+"\"}");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.getWriter().write("123");
    }
}
