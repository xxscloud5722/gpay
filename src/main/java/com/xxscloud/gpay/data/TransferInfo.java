package com.xxscloud.gpay.data;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author Cat.
 */
@Data
public final class TransferInfo {
    private String flowNo;
    private String transactionNo;
    private String openId;
    private String realName;
    private Date transferTime;
    private Date payTime;
    private BigDecimal amount;
    private TransferStatusEnum status;
    private String failReason;
    private String attach;
}
