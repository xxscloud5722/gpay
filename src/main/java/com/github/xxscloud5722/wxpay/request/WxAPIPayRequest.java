package com.github.xxscloud5722.wxpay.request;

import com.github.xxscloud5722.wxpay.IWxRequest;
import com.github.xxscloud5722.wxpay.IWxResponse;
import com.github.xxscloud5722.wxpay.response.WxCreateOrderResponse;
import com.github.xxscloud5722.PayException;
import lombok.Data;

import java.lang.reflect.Field;
import java.util.Objects;
import java.util.TreeMap;

@Data
public class WxAPIPayRequest implements IWxRequest {
    private String appId;
    private String timeStamp;
    private String nonceStr;
    private String packages;
    private String signType;
    private String paySign;


    @Override
    public String toString() {
        final StringBuilder content = new StringBuilder();
        toMap().forEach((k, v) -> {
            if (!Objects.equals("signType", k) && (k.contains("sign") || v == null || v.toString().length() <= 0)) {
                return;
            }
            final String nk = Objects.equals(k, "packages") ? "package" : k;
            content.append(nk).append("=").append(v).append("&");
        });
        if (content.length() > 0) {
            content.delete(content.length() - 1, content.length());
        }
        return content.toString();
    }

    @Override
    public void setSign(String value) {
        this.paySign = value;
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
        return WxCreateOrderResponse.class;
    }
}
