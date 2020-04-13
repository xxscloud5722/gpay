package com.xxscloud.gpay.wxpay.response;

import com.xxscloud.gpay.wxpay.IWxResponse;
import lombok.Data;

@Data
public class WxTransferBankCardOrderReqsponse implements IWxResponse {
    private String result_code;
    private String err_code_des;
    private String return_msg;
    private String return_code;
}
