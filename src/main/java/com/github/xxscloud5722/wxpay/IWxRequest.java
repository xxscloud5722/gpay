package com.github.xxscloud5722.wxpay;


import java.util.TreeMap;

public interface IWxRequest {
    void setSign(String value);

    TreeMap<String,Object> toMap();

    Class<? extends IWxResponse> getResponseClass();
}
