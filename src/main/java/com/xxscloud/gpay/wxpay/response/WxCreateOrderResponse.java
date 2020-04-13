package com.xxscloud.gpay.wxpay.response;

import com.xxscloud.gpay.wxpay.IWxResponse;
import lombok.Data;

@Data
public class WxCreateOrderResponse implements IWxResponse {
    private String appid;
    private String mch_id;
    private String device_info;
    private String nonce_str;
    private String sign;
    private String prepay_id;
    private String code_url;
    private String mweb_url;

    private String result_code;
    private String err_code_des;
    private String return_msg;
    private String return_code;
}
