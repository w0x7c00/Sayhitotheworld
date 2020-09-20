package web.email;

import javax.mail.Address;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class BasicEmailTool {
    static String smtphost;//smtp服务器地址
    static String from; // 邮件发送人的邮件地址
    static String username; // 发件人的邮件帐户
    static String password; // 发件人的邮件密码
    static String port;
    public static boolean initEmail(String smtphost,String from,String username,String password,String port){
        BasicEmailTool.smtphost = smtphost;
        BasicEmailTool.from = from;
        BasicEmailTool.username = username;
        BasicEmailTool.password = password;
        BasicEmailTool.port=port;
        return true;
    }

    /**
     * @param email    接收人的邮箱地址
     * @param emailMsg 邮箱内容
     * @return
     */
    public static boolean sendMail(String email, String emailMsg) {

        String to = email; // 邮件接收人的邮件地址
        // 定义Properties对象,设置环境信息
        Properties props = System.getProperties();
        // 设置邮件服务器的地址
        props.setProperty("mail.smtp.host", smtphost); // 指定的smtp服务器
        props.setProperty("mail.smtp.auth", "true");
        props.setProperty("mail.transport.protocol", "smtp");// 设置发送邮件使用的协议
        props.setProperty("mail.smtp.port", port);
        if(!port.equals("25")){
            props.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
            props.setProperty("mail.smtp.socketFactory.fallback", "false");
            props.setProperty("mail.smtp.socketFactory.port", port);
            props.put("mail.smtp.ssl.enable", "true");
        }
        // 创建Session对象,session对象表示整个邮件的环境信息
        Session session = Session.getInstance(props);
        // 设置输出调试信息
        session.setDebug(true);
        try {
            // Message的实例对象表示一封电子邮件
            MimeMessage message = new MimeMessage(session);
            // 设置发件人的地址
            message.setFrom(new InternetAddress(from));
            // 设置主题
            message.setSubject("Say Hi To The World");
            // 设置邮件的文本内容
            // message.setText("Welcome to JavaMail World!");
            message.setContent((emailMsg), "text/html;charset=utf-8");

            // 设置附件
            // message.setDataHandler(dh);

            // 从session的环境中获取发送邮件的对象
            Transport transport = session.getTransport();
            // 连接邮件服务器
            transport.connect(username, password);
            // 设置收件人地址,并发送消息
            transport.sendMessage(message, new Address[]{new InternetAddress(to)});
            transport.close();
            return true;
        } catch (MessagingException e) {
            e.printStackTrace();
            return false;
        }
    }
}
