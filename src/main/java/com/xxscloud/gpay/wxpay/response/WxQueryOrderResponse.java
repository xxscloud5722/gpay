package com.xxscloud.gpay.wxpay.response;

import com.xxscloud.gpay.wxpay.IWxResponse;
import lombok.Data;

@Data
public class WxQueryOrderResponse implements IWxResponse {
    private String result_code;
    private String err_code_des;
    private String return_msg;
    private String return_code;


    private String device_info;
    private String openid;
    private String is_subscribe;
    private String trade_type;
    private String trade_state;
    private String bank_type;
    private String total_fee;
    private String settlement_total_fee;
    private String fee_type;
    private String cash_fee;
    private String cash_fee_type;
    private String coupon_fee;
    private String coupon_count;
    private String transaction_id;
    private String out_trade_no;
    private String attach;
    private String time_end;
    private String trade_state_desc;
}
