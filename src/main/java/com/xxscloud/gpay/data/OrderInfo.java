package com.xxscloud.gpay.data;

import lombok.Data;


import java.math.BigDecimal;
import java.util.Date;
/**
 * @author Cat.
 */
@Data
public final class OrderInfo {
    private String flowNo;
    private String transactionNo;
    private String openId;
    private String transactionType;
    private PayStatusEnum status;
    private String bankType;
    private BigDecimal amount;
    private String currency;
    private String attach;
    private Date payTime;
}
