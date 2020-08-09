package tool;

import com.sun.deploy.net.HttpRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Random;

public class BasicTool {
    public static void setCharacterEncoding(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.setCharacterEncoding("utf-8");
        resp.setCharacterEncoding("utf-8");
    }
    public static void clearSession(HttpSession httpSession){
        httpSession.removeAttribute("user");
        httpSession.removeAttribute("admin");
    }
    public static String generateRandomEmailCode(){
        Random random = new Random();
        random.setSeed(System.currentTimeMillis());
        int m = random.nextInt(1000000);
        while(m<100000){
            m*=10;
        }
        return ""+m;
    }
}