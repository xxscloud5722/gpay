package com.github.xxscloud5722.data;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
/**
 * @author Cat.
 */
@Data
public final class RefundOrderInfo {
    private String flowNo;
    private String transactionNo;
    private String refundNo;
    private String refundReason;
    private BigDecimal refundAmount;
    private RefundStatusEnum status;
    private Date refundDate;
    private String remark;
}
