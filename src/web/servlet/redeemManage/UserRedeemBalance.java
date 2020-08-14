package web.servlet.redeemManage;

import tool.BasicTool;
import web.dataBasePacket.Redeem;
import web.dataBasePacket.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//输入字段： redeemCode
//输出字段： state：  -0 保留   -1 成功兑换  -2 未登录 -3 字段错误 -4 不存在的redeemCode  -5 redeemCode已经使用过
//流程: 检查输入字段   检查登录     检查redeemCode存在     检查是否已使用   修改user余额  修改redeem状态
@WebServlet("/userRedeemBalance")
public class UserRedeemBalance extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        BasicTool.setCharacterEncoding(req, resp);
        int state = 0;
        String redeemCode = req.getParameter("redeemCode");
        if(redeemCode==null){
            state = 3;
        }
        else{
            User user = (User) req.getSession().getAttribute("user");
            if(user == null ){
                state = 2;
            }
            else{
                Redeem redeem = new Redeem();
                redeem.code = redeemCode;
                if(redeem.setWithCode()){
                    if(redeem.state==0){
                        //已使用
                        state = 5;
                    }
                    else{
                        //更新用户账户金额
                        user.balance+=redeem.amount;
                        user.update();
                        //修改redeem
                        redeem.state=0;
                        redeem.used_time = System.currentTimeMillis();
                        redeem.update();
                        state = 1;
                    }
                }
                else{
                    state = 4;
                }
            }
        }
        resp.getWriter().write("{state:"+state+"}");
    }
}