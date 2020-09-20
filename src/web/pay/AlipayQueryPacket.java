package web.pay;

//code:             10000----订单查询成功
//                  40004----无此订单
//trade_status:     WAIT_BUYER_PAY（交易创建，等待买家付款）
//                  TRADE_CLOSED（未付款交易超时关闭，或支付完成后全额退款）
//                  TRADE_SUCCESS（交易支付成功）
//                  TRADE_FINISHED（交易结束，不可退款）

public class AlipayQueryPacket {
    public String code;
    public String trade_status;
}
