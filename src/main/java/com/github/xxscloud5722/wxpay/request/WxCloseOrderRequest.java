package com.github.xxscloud5722.wxpay.request;

import com.github.xxscloud5722.wxpay.IWxRequest;
import com.github.xxscloud5722.wxpay.IWxResponse;
import com.github.xxscloud5722.wxpay.response.WxCloseOrderResponse;
import com.github.xxscloud5722.PayException;
import lombok.Data;

import java.lang.reflect.Field;
import java.util.TreeMap;

@Data
public class WxCloseOrderRequest implements IWxRequest {
    private String appid;
    private String mch_id;
    private String out_trade_no;
    private String nonce_str;
    private String sign;
    @Override
    public String toString() {
        final StringBuilder content = new StringBuilder();
        toMap().forEach((k, v) -> {
            if (k.contains("sign") || v == null) {
                return;
            }
            content.append(k).append("=").append(v).append("&");
        });
        if (content.length() > 0) {
            content.delete(content.length() - 1, content.length());
        }
        return content.toString();
    }

    @Override
    public TreeMap<String, Object> toMap() {
        try {
            final TreeMap<String, Object> treeMap = new TreeMap<>();
            for (Field field : this.getClass().getDeclaredFields()) {
                treeMap.put(field.getName(), field.get(this));
            }

            return treeMap;
        } catch (IllegalAccessException e) {
            throw new PayException("数据异常");
        }
    }

    @Override
    public Class<? extends IWxResponse> getResponseClass() {
        return WxCloseOrderResponse.class;
    }
}
