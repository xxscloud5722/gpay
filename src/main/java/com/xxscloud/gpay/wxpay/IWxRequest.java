package com.xxscloud.gpay.wxpay;


import java.util.TreeMap;

public interface IWxRequest {
    void setSign(String value);

    TreeMap<String,Object> toMap();

    Class<? extends IWxResponse> getResponseClass();
}
