package com.github.xxscloud5722.data;

import java.util.Objects;
/**
 * @author Cat.
 */
public enum PayStatusEnum {
    SUCCESS("支付成功"),
    REFUND("转入退款"),
    NOTPAY("未支付"),
    CLOSED("已关闭"),
    REVOKED("已撤销"),
    USERPAYING("支付中"),
    PAYERROR("支付失败");
    private String desc;

    PayStatusEnum(String desc) {
        this.desc = desc;
    }

    public static PayStatusEnum parse(String status) {
        if (Objects.equals(status, "WAIT_BUYER_PAY")) {
            return  PayStatusEnum.NOTPAY;
        }
        if (Objects.equals(status, "TRADE_CLOSED")) {
            return  PayStatusEnum.CLOSED;
        }
        if (Objects.equals(status, "TRADE_SUCCESS")) {
            return  PayStatusEnum.SUCCESS;
        }
        if (Objects.equals(status, "TRADE_FINISHED")) {
            return  PayStatusEnum.PAYERROR;
        }
        return PayStatusEnum.valueOf(status);
    }
}
