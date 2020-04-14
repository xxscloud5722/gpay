package com.github.xxscloud5722.wxpay.response;

import com.github.xxscloud5722.wxpay.IWxResponse;
import lombok.Data;

@Data
public class WxQueryRefundOrderResponse implements IWxResponse {
    private String result_code;
    private String err_code_des;
    private String return_msg;
    private String return_code;


    private String appid;
    private String mch_id;
    private String nonce_str;
    private String sign;
    private String total_refund_count;
    private String transaction_id;
    private String out_trade_no;
    private String total_fee;
    private String refund_success_time_0;
    private String refund_status_0;


}
