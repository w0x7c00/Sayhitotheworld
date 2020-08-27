package tool;

import web.sessionPacket.AdminSessionPacket;
import javax.servlet.http.HttpSession;

public class PermissionCheck{
    public HttpSession httpSession = null;
    public PermissionCheck(HttpSession httpSession){
        this.httpSession = httpSession;
    }
    public boolean checkAdmin(){
        return httpSession.getAttribute("admin") != null;
    }
    public boolean checkSuperAdmin(){
        if(this.checkAdmin()){
            AdminSessionPacket adminSessionPacket = (AdminSessionPacket) httpSession.getAttribute("admin");
            return adminSessionPacket.type == 0;
        }
        else{
            return false;
        }
    }
    public boolean checkUser(){
        return httpSession.getAttribute("user") !=null;
    }
    public boolean checkTeacher(){
        return httpSession.getAttribute("teacher") !=null;
    }
}
