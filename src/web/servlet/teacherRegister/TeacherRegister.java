package web.servlet.teacherRegister;

import tool.BasicTool;
import tool.FormatCheckTool;
import web.dataBasePacket.Teacher;
import web.dataBasePacket.User;
import web.sessionPacket.RegisterSessionPacket;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

//输入前提：需要在web端初次检查输入字段
//输入字段：user_name  password email emailCode name

//返回字段：state： -0 保留  -1 成功注册   -2 字段错误   -3 已存在的教师  -4 未发送过验证码（没有session）-5 验证码不正确/重新发送 -6 图片不符合要求 -7 数据库/服务器故障(不符合长度要求的字段)

//结构：
//1.检查输入字段    2.检查用户名可用性 3.检查是否有session  4.检查邮箱信息与邮箱码对应情况并且检查邮箱码是否正确     5.插入数据库    6.删除session

//Teacher字段
//public String teacher_name;
//public String password;
//public short sex;
//public String name;
//public int balance;
//public int price;  //半小时价格
//public long create_time;
//public short state;
//
//public String email;
//public String education;
//public String language;
//public String pic;
//public int age;
//public String self_introduction;

@WebServlet("/teacherRegister")
public class TeacherRegister extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        BasicTool.setCharacterEncoding(req,resp);
        int state = 0;
        String teacher_name = req.getParameter("teacher_name");
        String password = req.getParameter("password");
        String sex_str = req.getParameter("sex");
        String name = req.getParameter("name");
        String price_str = req.getParameter("price");
        String email = req.getParameter("email");
        String education = req.getParameter("education");
        String language_str = req.getParameter("language");
        String pic = req.getParameter("pic");
        String age_str = req.getParameter("age");
        String self_introduction = req.getParameter("self_introduction");
        String emailCode = req.getParameter("emailCode");

        short sex = BasicTool.str2short(sex_str);
        int price = BasicTool.str2int(price_str);
        int age = BasicTool.str2int(age_str);
        short language = BasicTool.str2short(language_str);

        //检查输入字段
        //email格式不用检查
        //name、user_name、password格式必须要检查
        if(FormatCheckTool.checkPassword(password)
                &&FormatCheckTool.checkEducation(education)
                &&FormatCheckTool.checkPic(pic)
                &&FormatCheckTool.checkSelfIntroduction(self_introduction)
                &&FormatCheckTool.checkName(name)
                &&FormatCheckTool.checkTeacherName(teacher_name)
                &&FormatCheckTool.checkTeacherEmail(email)
                &&FormatCheckTool.checkEmailCode(emailCode)
                &&FormatCheckTool.checkAge(age)
                &&FormatCheckTool.checkLanguage(language)
                &&FormatCheckTool.checkPrice(price)
                &&FormatCheckTool.checkSex(sex)
        ){
            //检查教师名是否存在
            Teacher teacher = new Teacher();
            teacher.teacher_name = teacher_name;
            if(teacher.set()){
                //已存在的教师名
                state = 3;
            }
            else {
                //检查email以及对应的邮箱码
                HttpSession httpSession = req.getSession();
                RegisterSessionPacket registerSessionPacket = (RegisterSessionPacket) httpSession.getAttribute("teacherRegister");
                if(registerSessionPacket==null){
                    //没有这个session
                    state = 4;
                }
                else{
                    //存在这个session
                    if(registerSessionPacket.checkEmailCode(email,emailCode)){
                        //验证码检查成功
                        //插入记录
                        teacher.price=price;
                        teacher.education=education;
                        teacher.language=language_str;
                        teacher.pic=pic;
                        teacher.age=age;
                        teacher.self_introduction=self_introduction;
                        teacher.name = name;
                        teacher.sex =sex;
                        teacher.teacher_name = teacher_name;
                        teacher.password = password;
                        teacher.email = email;
                        teacher.balance = 0;
                        teacher.create_time = System.currentTimeMillis();
                        if(teacher.insert()){
                            //插入数据成功
                            //删除session
                            httpSession.removeAttribute("teacherRegister");
                            state = 1;
                        }
                        else{
                            //插入数据失败 数据库故障
                            //此处一般是字段长度超范围
                            state = 7;
                        }
                    }
                    else{
                        state = 5;
                    }
                }
            }
        }
        else{
            //格式检查不通过
            state = 2;
        }
        resp.getWriter().write(BasicTool.getStateStr(state));
    }
}
