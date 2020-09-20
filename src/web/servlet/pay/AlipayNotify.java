package web.servlet.pay;

import tool.BasicTool;
import web.dataBasePacket.Recharge;
import web.dataBasePacket.User;
import web.pay.AliPay;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/alipayNotify")
public class AlipayNotify extends HttpServlet {
    private short RECHARGE_STATE_SUCCESS=1;
    private String ALIPAY_NOTIFY_RETURN="success";
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        BasicTool.setCharacterEncoding(req, resp);
        boolean result = new AliPay().checkNotifySign(req.getParameterMap());
        System.out.println("result:"+result);
        if(result){
            //成功验签
            String out_trade_no = req.getParameter("out_trade_no");
            int id = BasicTool.str2int(out_trade_no);
            Recharge recharge = new Recharge();
            recharge.id=id;
            if(recharge.set()){
                if(recharge.state!=RECHARGE_STATE_SUCCESS){
                    //如果数据库内容没有被设置为成功
                    if(rechargeHandle(recharge)){
                        resp.getWriter().write(ALIPAY_NOTIFY_RETURN);
                    }
                }
            }
        }
    }

    private boolean rechargeHandle(Recharge recharge){
        recharge.state=RECHARGE_STATE_SUCCESS;
        recharge.update();
        User user = new User();
        user.user_name=recharge.user_name;
        if(user.set()){
            user.balance+=recharge.amount;
            return true;
        }
        else {
            return false;
        }
    }
}