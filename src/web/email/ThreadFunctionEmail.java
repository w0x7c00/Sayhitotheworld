package web.email;

import tool.Logging;
import tool.ThreadFunction;
import web.dataBasePacket.Teacher;

import java.util.List;

//Email的data格式：title(String),text(String),receiver(list of String)--------MultiSendTeacherEmailPacket
public class ThreadFunctionEmail implements ThreadFunction {
    final private String LOGSENDER = "ThreadFunctionEmail";
    //实现线程的核心方法
    @Override
    public void func(Object data) {
        MultiSendTeacherEmailPacket mydata = (MultiSendTeacherEmailPacket) data;
        String title = mydata.title;
        String text = mydata.text;
        Teacher teacher = new Teacher();
        List<String> receivers = mydata.receivers;
        for(int i = 0 ; i < receivers.size() ; i++) {
            String receiver = receivers.get(i);
            //执行邮箱查询操作
            String target_email;
            teacher.teacher_name = receiver;
            if(teacher.set()){
                target_email = teacher.email;
                BasicEmailTool.sendMail(target_email,title+":"+text);
            }
            else{
                Logging.WARNING(LOGSENDER,"teacher_name:"+teacher.teacher_name+" is not existing!Send Email Failing!");
            }
        }
    }
}