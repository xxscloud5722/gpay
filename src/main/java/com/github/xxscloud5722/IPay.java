package com.github.xxscloud5722;

import com.github.xxscloud5722.data.OrderInfo;
import com.github.xxscloud5722.data.RefundOrderInfo;
import com.github.xxscloud5722.data.TransferInfo;

import java.math.BigDecimal;
import java.util.HashMap;

/**
 * @author  Cat.
 */
public interface IPay {

    /**
     * H5Pay.
     *
     * @param ip         客户端IP.
     * @param flowNo     我方流水号.
     * @param amount     金额.
     * @param subject    标题.
     * @param body       内容(选填) 空字符串.
     * @param requestUrl 响应地址.
     * @param notifyUrl  通知地址.
     * @param attachArgs 附加参数 (选填) 空字符串.
     * @return 字符串.
     */
    String h5Pay(String ip, String flowNo, BigDecimal amount, String subject, String body,
                 String requestUrl, String notifyUrl, String attachArgs);

    /**
     * H5Pay.
     *
     * @param ip         客户端IP.
     * @param flowNo     我方流水号.
     * @param amount     金额.
     * @param subject    标题.
     * @param body       内容(选填) 空字符串.
     * @param requestUrl 响应地址.
     * @param notifyUrl  通知地址.
     * @param attachArgs 附加参数 (选填) 空字符串.
     * @return 字符串.
     */
    String pcPay(String ip, String flowNo, BigDecimal amount, String subject, String body,
                 String requestUrl, String notifyUrl, String attachArgs);

    /**
     * App 支付.
     *
     * @param ip         客户端IP.
     * @param flowNo     我方流水号.
     * @param amount     金额.
     * @param subject    标题.
     * @param body       内容(选填) 空字符串.
     * @param notifyUrl  通知地址.
     * @param attachArgs 附加参数 (选填) 空字符串.
     * @return 字符串.
     */
    String appPay(String ip, String flowNo, BigDecimal amount, String subject, String body,
                  String notifyUrl, String attachArgs);

    /**
     * JS 支付.
     *
     * @param ip         客户端IP.
     * @param flowNo     我方流水号.
     * @param amount     金额.
     * @param subject    标题.
     * @param body       内容(选填) 空字符串.
     * @param requestUrl 响应地址.
     * @param notifyUrl  通知地址.
     * @param attachArgs 附加参数 (选填) 空字符串.
     * @param openId     用户ID.
     * @return 字符串.
     */
    String jsApiPay(String ip, String flowNo, BigDecimal amount, String subject, String body,
                    String requestUrl, String notifyUrl, String attachArgs, String openId);

    /**
     * 扫码支付.
     *
     * @param ip         客户端IP.
     * @param flowNo     我方流水号.
     * @param amount     金额.
     * @param subject    标题.
     * @param body       内容(选填) 空字符串.
     * @param requestUrl 响应地址.
     * @param notifyUrl  通知地址.
     * @param attachArgs 附加参数 (选填) 空字符串.
     * @param openId     用户ID.
     * @return 字符串.
     */
    String nativePay(String ip, String flowNo, BigDecimal amount, String subject, String body,
                     String requestUrl, String notifyUrl, String attachArgs, String openId);

    /**
     * H5Pay.
     *
     * @param ip         客户端IP.
     * @param flowNo     我方流水号.
     * @param amount     金额.
     * @param subject    标题.
     * @param body       内容(选填) 空字符串.
     * @param requestUrl 响应地址.
     * @param notifyUrl  通知地址.
     * @param attachArgs 附加参数 (选填) 空字符串.
     * @param openId     用户ID.
     * @return 字符串.
     */
    String appletsPay(String ip, String flowNo, BigDecimal amount, String subject, String body,
                      String requestUrl, String notifyUrl, String attachArgs, String openId);

    /**
     * 查询支付订单信息.
     *
     * @param flowNo 我方流水号.
     * @return 订单信息.
     */
    OrderInfo queryOrder(String flowNo);


    /**
     * 查询退款订单信息.
     *
     * @param flowNo   我方流水号.
     * @param refundNo 我方退款号.
     * @param offset   第几次量.
     * @return 退款订单信息.
     */
    RefundOrderInfo queryRefundOrderInfo(String flowNo, String refundNo, int offset);

    /**
     * 申请退款.
     *
     * @param flowNo       我方流水号.
     * @param refundNo     退款流水号.
     * @param orderAmount  订单金额.
     * @param refundAmount 退款金额.
     * @param refundReason 退款原因 (选填) 空字符.
     * @return 是否申请成功.
     */
    boolean refundOrder(String flowNo, String refundNo, BigDecimal orderAmount, BigDecimal refundAmount, String refundReason);

    /**
     * 关闭订单.
     *
     * @param flowNo 我方流水号.
     * @return 是否成功.
     */
    boolean closeOrder(String flowNo);

    /**
     * 转账.
     *
     * @param flowNo     我方转账流水号.
     * @param amount     转账金额.
     * @param payeeId    收款人用户唯一表示 (微信是openId 支付宝是用户登陆账号).
     * @param realName   真实姓名.
     * @param attachArgs 附加参数  (选填) 空字符.
     * @return 是否成功.
     */
    boolean transfer(String flowNo, BigDecimal amount, String payeeId, String realName, String attachArgs);

    /**
     * 转账到银行卡.
     *
     * @param flowNo     我放转账流水号.
     * @param amount     转账金额.
     * @param bankCardNo 银行卡号.
     * @param realName   真实姓名.
     * @param bankCode   银行代码.
     * @param attachArgs 附加参数 (选填) 空字符.
     * @return 是否成功.
     */
    boolean transferBankCard(String flowNo, BigDecimal amount, String bankCardNo, String realName,
                             String bankCode, String attachArgs);

    /**
     * 查询转账信息.
     *
     * @param flowNo 转账流水号.
     * @return 账单信息.
     */
    TransferInfo queryTransferInfo(String flowNo);


    /**
     * 检查签名.
     *
     * @param header 头部.
     * @param body   内容.
     * @return 是否签名正确.
     */
    boolean checkSignature(HashMap<String, String> header, String body);
}
