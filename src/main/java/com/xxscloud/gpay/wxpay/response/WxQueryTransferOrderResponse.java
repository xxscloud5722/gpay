package com.xxscloud.gpay.wxpay.response;

import com.xxscloud.gpay.wxpay.IWxResponse;
import lombok.Data;

@Data
public class WxQueryTransferOrderResponse implements IWxResponse {
    private String result_code;
    private String err_code_des;
    private String return_msg;
    private String return_code;

    private String detail_id;
    private String status;
    private String reason;
    private String openid;
    private String transfer_name;
    private String payment_amount;
    private String transfer_time;
    private String payment_time;
    private String desc;

}
