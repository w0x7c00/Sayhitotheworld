package web.pay;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.alipay.api.response.AlipayTradeQueryResponse;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

//交易状态：WAIT_BUYER_PAY（交易创建，等待买家付款）、TRADE_CLOSED（未付款交易超时关闭，或支付完成后全额退款）、TRADE_SUCCESS（交易支付成功）、TRADE_FINISHED（交易结束，不可退款）

public class AliPay {
    //本类中通用的pay对象
    private static AlipayClient alipayClient;
    private static String APPID;
    private static String GET_WAY;
    private static String APP_PRIVATE_KEY;
    private static String FORMAT;
    private static String CHARSET;
    private static String ALIPAY_PUBLIC_KEY;
    private static String SIGN_TYPE;

    private static String RETURN_URL;
    private static String NOTIFY_URL;
    public static boolean initAlipay(Properties properties){
        initVal(properties);
        alipayClient = new DefaultAlipayClient(GET_WAY,APPID,APP_PRIVATE_KEY,FORMAT,CHARSET,ALIPAY_PUBLIC_KEY,SIGN_TYPE);
        return true;
    }
    private static boolean initVal(Properties properties){
        APPID = properties.getProperty("Alipay.APPID");
        GET_WAY = properties.getProperty("Alipay.GET_WAY");
        APP_PRIVATE_KEY = properties.getProperty("Alipay.APP_PRIVATE_KEY");
        FORMAT = properties.getProperty("Alipay.FORMAT");
        CHARSET = properties.getProperty("Alipay.CHARSET");
        ALIPAY_PUBLIC_KEY = properties.getProperty("Alipay.ALIPAY_PUBLIC_KEY");
        SIGN_TYPE = properties.getProperty("Alipay.SIGN_TYPE");
        RETURN_URL = properties.getProperty("Alipay.RETURN_URL");
        NOTIFY_URL = properties.getProperty("Alipay.NOTIFY_URL");
        return APPID != null && GET_WAY != null && APP_PRIVATE_KEY != null && FORMAT != null && CHARSET != null && ALIPAY_PUBLIC_KEY != null && SIGN_TYPE != null;
    }


    //创建失败 返回null
    public String createOrderForm(int id,int amount){
        AlipayTradePagePayRequest alipayRequest =  new  AlipayTradePagePayRequest(); //创建API对应的request

        alipayRequest.setReturnUrl(RETURN_URL);
        alipayRequest.setNotifyUrl(NOTIFY_URL); //在公共参数中设置回跳和通知地址
        BizContent bizContent = new BizContent();
        bizContent.total_amount=amount+"";
        bizContent.out_trade_no=id+"";
        bizContent.subject="Test-Subject";
        bizContent.body="Test-Body";
        bizContent.product_code="FAST_INSTANT_TRADE_PAY";
        alipayRequest.setBizContent(new Gson().toJson(bizContent)); //填充业务参数
        String form= "" ;
        try  {
            form = alipayClient.pageExecute(alipayRequest).getBody();  //调用SDK生成表单
        }  catch  (AlipayApiException e) {
            e.printStackTrace();
            return null;
        }
        return form;
    }

    public AlipayQueryPacket queryOrderStatus(int id){
        AlipayTradeQueryRequest alipayRequest =  new AlipayTradeQueryRequest(); //创建API对应的request
        BizContent bizContent = new BizContent();
        bizContent.out_trade_no=id+"";
        alipayRequest.setBizContent(new Gson().toJson(bizContent));
        AlipayQueryPacket re = new AlipayQueryPacket();
        re.code=null;
        re.trade_status=null;
        try  {
            AlipayTradeQueryResponse alipayTradeQueryResponse = alipayClient.execute(alipayRequest);
            re.code=alipayTradeQueryResponse.getCode();
            re.trade_status=alipayTradeQueryResponse.getTradeStatus();
        }  catch  (AlipayApiException e) {
            e.printStackTrace();
        }
        return re;
    }

    public boolean checkNotifySign(Map<String,String[]> requestParams){
        Map<String,String> params = new HashMap<String,String>();
        for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext();) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            params.put(name, valueStr);
        }

        //验签算法
        try{
            return AlipaySignature.rsaCheckV1(params,
                    ALIPAY_PUBLIC_KEY,
                    CHARSET,
                    SIGN_TYPE);
        }
        catch (AlipayApiException e){
            e.printStackTrace();
            return false;
        }
    }

    public class BizContent{
        public String out_trade_no;
        public String product_code;
        public String total_amount;
        public String subject;
        public String body;
    }
}