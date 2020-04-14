package com.github.xxscloud5722.wxpay;

public interface IWxResponse {


    String getReturn_msg();

    String getReturn_code();

    String getResult_code();

    String getErr_code_des();
}
