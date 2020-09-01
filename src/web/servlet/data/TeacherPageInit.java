package web.servlet.data;

import web.dataBasePacket.Teacher;
import web.sessionPacket.TeacherSessionPacket;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/teacherPageInit")
public class TeacherPageInit extends HttpServlet{
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }

    public class TeacherPageInitReturnPacket{
        public TeacherSessionPacket teacherSessionPacket;    //这个teacher必须要屏蔽password字段   在屏蔽之后 json化后要恢复
    }
}