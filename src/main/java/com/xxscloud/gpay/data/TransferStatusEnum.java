package com.xxscloud.gpay.data;

public enum TransferStatusEnum {
    SUCCESS("成功"),
    FAIL("失败"),
    INIT("等待处理"),
    DEALING("处理中"),
    REFUND("退票"),
    UNKNOWN("状态未知"),
    ;
    private String name;

    TransferStatusEnum(String name) {
        this.name = name;
    }

    public static TransferStatusEnum parse(String status) {
        return TransferStatusEnum.valueOf(status);
    }
}
