package web.servlet.test;


import tool.BasicTool;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet("/test")
public class Test extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        BasicTool.setGetPageEncoding(req,resp);
        boolean result ="123".equals(null);
        System.out.println(result);
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