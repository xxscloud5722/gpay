package com.github.xxscloud5722.data;
/**
 * @author Cat.
 */
public enum RefundStatusEnum {
    SUCCESS("退款成功"),
    ERROR("退款失败"),
    UNKNOWN("退款失败");
    private String desc;

    RefundStatusEnum(String desc) {
        this.desc = desc;
    }

    public static RefundStatusEnum parse(String status) {
        if (status == null) {
            return RefundStatusEnum.UNKNOWN;
        }
        return RefundStatusEnum.valueOf(status);
    }
}
