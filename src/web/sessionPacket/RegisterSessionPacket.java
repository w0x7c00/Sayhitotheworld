package web.sessionPacket;

public class RegisterSessionPacket implements SessionPacketInterface{
    public String emailCode;
    public String email;
    public long time;
    public final long divideTime = 60000;    //间隔时间 60000ms = 60s

    public RegisterSessionPacket(){
        this.time = 0;
    }

    //成功返回-1    时间间隔不够返回现在的时间间隔（long ms为单位）
    public long set(String email,String emailCode) {
        long divdeNow = System.currentTimeMillis()-this.time;
        if(divdeNow>=divideTime){
            this.email = email;
            this.emailCode = emailCode;
            this.time = System.currentTimeMillis();
            return -1;
        }
        else{
            return divdeNow;
        }
    }

    public boolean checkEmailCode(String email,String emailCode){
        if(this.emailCode.equals(emailCode)&&this.email.equals(email)){
            return true;
        }
        else {
            return false;
        }
    }
}