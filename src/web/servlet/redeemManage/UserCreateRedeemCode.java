package web.servlet.redeemManage;

import com.google.gson.Gson;
import tool.BasicTool;
import tool.FormatCheckTool;
import web.dataBasePacket.Redeem;
import web.dataBasePacket.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//输入字段：  amount
//返回字段：  state: -0 保留   -1 成功    -2 未登录    -3 输入字段不正确    -4 不正确的amount值/格式不正确也算    -5 amount过大     -6 长时间生成冲突code
//                 -7 插入失败（一般不会）
//          redeemCode: 可有
//流程：  1.审核字段存在   2.检测amount范围以及转数字   3.检测登录状态   4.检测余额    5.生成code   6.插入数据库   7.返回code
@WebServlet("/userCreateRedeemCode")
public class UserCreateRedeemCode extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        BasicTool.setCharacterEncoding(req, resp);
        int state = 0;
        int maxTimeToCreateCode = 6;
        String redeemCode = null;
        String amount_str = req.getParameter("amount");
        if(amount_str == null){
            state = 3;
        }
        else {
            int amount_int = 0;
            try{
                amount_int = Integer.parseInt(amount_str);
            }
            catch (NumberFormatException e){
                amount_int = -1;
            }
            if(amount_int==-1){
                //错误amount转数字
                state = 4;
            }
            else{
                //检测amount的数字要求
                if(FormatCheckTool.checkAmount(amount_int)){
                    //检测登录状态
                    User user = (User)req.getSession().getAttribute("user");
                    if(user == null){
                        //未登录
                        state = 2;
                    }
                    else{
                        if(user.balance>=amount_int){
                            //生成code并插入
                            boolean available_code_flag = false;
                            Redeem new_redeem = new Redeem();
                            for(int i =0;i<maxTimeToCreateCode;i++){
                                new_redeem.code = BasicTool.generateRedeemCode();
                                if(!new_redeem.setWithCode()){
                                    //当不存在冲突时
                                    available_code_flag = true;
                                    break;
                                }
                            }
                            if(available_code_flag){
                                new_redeem.state =1;
                                new_redeem.create_time = System.currentTimeMillis();
                                new_redeem.used_time=0;
                                new_redeem.amount = amount_int;
                                new_redeem.type = 1;
                                new_redeem.append_inf = user.user_name;
                                if(new_redeem.insert()){
                                    //修改用户信息
                                    user.balance-=amount_int;
                                    user.update();
                                    state = 1;
                                    redeemCode = new_redeem.code;
                                }
                                else{
                                    //不会出现的情况 插入失败
                                    state = 7;
                                }
                            }
                            else{
                                state = 6;
                            }
                            //更新user

                        }
                        else{
                            state = 5;
                        }
                    }
                }
                else{
                    state = 4;
                }
            }
        }
        Gson gson = new Gson();
        UserCreateRedeemReturnPacket userCreateRedeemReturnPacket = new UserCreateRedeemReturnPacket();
        userCreateRedeemReturnPacket.state = state;
        userCreateRedeemReturnPacket.redeemCode = redeemCode;
        resp.getWriter().write(gson.toJson(userCreateRedeemReturnPacket));
    }

    private class UserCreateRedeemReturnPacket{
        public int state;
        public String redeemCode;
    }
}