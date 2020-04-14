package com.github.xxscloud5722.wxpay.response;

import com.github.xxscloud5722.wxpay.IWxResponse;
import lombok.Data;

@Data
public class WxTransferBankCardOrderReqsponse implements IWxResponse {
    private String result_code;
    private String err_code_des;
    private String return_msg;
    private String return_code;
}
