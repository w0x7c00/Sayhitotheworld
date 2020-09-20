package web.servlet.logAndRegister;

import tool.BasicTool;
import tool.FormatCheckTool;
import web.dataBasePacket.Teacher;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpSession;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//输入字段 teacher_name/email password  type: 0-邮箱登录 1-用户名登录
//输出字段：state  -0 登陆失败 -1 登录成功   -2 字段错误    -3 已经登录过   -4 教师用户名或邮箱不存在  -5 密码不正确
@WebServlet("/teacherLogin")
public class TeacherLogin extends HttpServlet{
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        BasicTool.setCharacterEncoding(req, resp);
        String type = req.getParameter("type");
        HttpSession httpSession = req.getSession();
        int state = 0;
        String password = req.getParameter("passoword");
        if(httpSession.getAttribute("teacher")!=null){
            if(FormatCheckTool.checkNotNull(password)){
                if("1".equals(type)){
                    //用户名登录
                    String teacher_name = req.getParameter("teacher_name");
                    if(FormatCheckTool.checkNotNull(teacher_name)){
                        //存在---
                        Teacher teacher = new Teacher();
                        teacher.teacher_name = teacher_name;
                        if(teacher.set()){
                            if(teacher.password.equals(password)){
                                //登录成功
                                BasicTool.clearSession(httpSession);
                                httpSession.setAttribute("teacher",teacher);
                            }
                            else{
                                //密码不正确
                                state = 5;
                            }
                        }
                        else{
                            //不存在用户名
                            state = 4;
                        }
                    }
                    else{
                        state = 2;
                    }
                }
                else if("0".equals(type)){
                    String email = req.getParameter("email");
                    if(FormatCheckTool.checkNotNull(email)){
                        //存在---
                        Teacher teacher = new Teacher();
                        teacher.email = email;
                        if(teacher.setWithEmail()){
                            if(teacher.password.equals(password)){
                                //登录成功
                                BasicTool.clearSession(httpSession);
                                httpSession.setAttribute("teacher",teacher);
                            }
                            else{
                                //密码不正确
                                state = 5;
                            }
                        }
                        else{
                            //不存在用户名
                            state = 4;
                        }
                    }
                    else{
                        state = 2;
                    }
                }
                else{
                    state = 2;
                }
            }
            else {
                state =2;
            }
        }
        else{
            state = 3;
        }
        resp.getWriter().write(BasicTool.getStateStr(state));
    }
}
