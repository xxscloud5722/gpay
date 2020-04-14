package com.github.xxscloud5722.data;

/**
 * @author Cat.
 */
public enum PayChannelEnum {
    ALI_PAY("支付宝支付"),
    WE_CHAT_PAY("微信支付");

    private String name;

    PayChannelEnum(String name) {
        this.name = name;
    }
}
