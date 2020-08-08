package tool;

import com.sun.deploy.net.HttpRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class BasicTool {
    public static void setCharacterEncoding(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.setCharacterEncoding("utf-8");
        resp.setCharacterEncoding("utf-8");
    }
    public static void clearSession(HttpSession httpSession){
        httpSession.removeAttribute("user");
        httpSession.removeAttribute("admin");
    }
    public static boolean checkEmailFormat(String email){
        return true;
    }
    public static String generateRandomEmailCode(){
        return "123456";
    }
}