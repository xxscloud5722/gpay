# GPAY 聚合支付项目
> 抹平各平台支付差异的SDK Java 版本

## 支持项目列表
|  序号   | 支付渠道  | 是否支持 | 备注 |
|  ----  | ----  | ----  | ---- |
| 1  | 支付宝支付 | 是 | 支持所有资金类API |
| 2  | 微信支付 | 是 | 支持所有资金类API |


## 渠道说明
### 微信
```Java
// 创建微信配置
final Object weChatPayClient =  new WeChatPayClient("wxa95794c4caf294fc", "1555631301",
"zzxp6686zzxp6686zzxp6686zzxp6686", Files.readAllBytes(Paths.get("/Users/xxscloud/code/xxscloud/com.pay.java/wx.p12")));

// 传入对应的渠道初始化IPay
final IPay pay = GPayFactory.getPay(PayChannelEnum.WE_CHAT, weChatPayClient);

//微信回执参数颜值
String xml = "<xml><appid><![CDATA[wx0bab1e29fbcd1b87]]></appid>\n" +
                "<attach><![CDATA[179_sulAEFior1]]></attach>\n" +
                "<bank_type><![CDATA[CMB_DEBIT]]></bank_type>\n" +
                "<cash_fee><![CDATA[14172]]></cash_fee>\n" +
                "<fee_type><![CDATA[CNY]]></fee_type>\n" +
                "<is_subscribe><![CDATA[N]]></is_subscribe>\n" +
                "<mch_id><![CDATA[1555631301]]></mch_id>\n" +
                "<nonce_str><![CDATA[102d5db9424a465ea1457cef98fd4ac5]]></nonce_str>\n" +
                "<openid><![CDATA[o96vBw9fl-olflRB1y_UvgdkSJQM]]></openid>\n" +
                "<out_trade_no><![CDATA[su3O2yyJ0Y]]></out_trade_no>\n" +
                "<result_code><![CDATA[SUCCESS]]></result_code>\n" +
                "<return_code><![CDATA[SUCCESS]]></return_code>\n" +
                "<sign><![CDATA[7353DE197F953E9367D1AB3AA134E2F4]]></sign>\n" +
                "<time_end><![CDATA[20200331122539]]></time_end>\n" +
                "<total_fee>14172</total_fee>\n" +
                "<trade_type><![CDATA[APP]]></trade_type>\n" +
                "<transaction_id><![CDATA[4200000473202003315764102113]]></transaction_id>\n" +
                "</xml>";
Signature.callbackCheck(PayChannelEnum.WE_CHAT_PAY, weChatPayClient, xml, null, o -> {
    System.out.println(JsonUtils.stringify(o));
});
```

### 支付宝
- 证书上传
    1) 下载支付宝开放平台
    2) 秘钥工具 - 生成秘钥 找到 生成秘钥  `注意私钥就是这里生成的私钥记得备份`
    3) 在最后找到 "获取CSR文件" 点击获取
    4) 在旁边有打开文件位置
    5) 找到文件位置上传到阿里
    6) 然后在从阿里下载根据CSR生成的3个证书分别放入程序
```Java
// 创建支付宝配置 
// 必须使用上传证书来确定加密方式, 如果上传证书
CertAlipayRequest certAlipayRequest = new CertAlipayRequest();
//默认
certAlipayRequest.setServerUrl("https://openapi.alipay.com/gateway.do");
//商户APPID
certAlipayRequest.setAppId("");
//商户私钥字符串
certAlipayRequest.setPrivateKey("");
//默认
certAlipayRequest.setFormat("json");
//默认
certAlipayRequest.setCharset("UTF-8");
//默认
certAlipayRequest.setSignType("RSA2");
//我方证书
certAlipayRequest.setCertPath("/Users/xxscloud/code/xxscloud/com.pay.java/appCertPublicKey_2021001113639731.crt");
//支付宝证书
certAlipayRequest.setAlipayPublicCertPath("/Users/xxscloud/code/xxscloud/com.pay.java/alipayCertPublicKey_RSA2.crt");
//我方根证书
certAlipayRequest.setRootCertPath("/Users/xxscloud/code/xxscloud/com.pay.java/alipayRootCert.crt");

// 传入对应的渠道初始化IPay
final IPay pay = GPayFactory.getPay(PayChannelEnum.ALIPAY, weChatPayClient);

// 参数验证
body = 获取 获取当前支付宝POST表单提交过来的 参数的Map 转Json
String body = "{\"gmt_create\":\"2020-04-10 14:37:26\",\"charset\":\"UTF-8\",\"seller_email\":\"3432215688@qq.com\",\"subject\":\"测试\",\"sign\":\"DcLH4xrtHVfgG/yCiUIL+zy34oYEpDu3AaIBrL6sO5A3BNYSlt73OhDtXlW9o5z14YAE3l1qnia2uhNGtWqElxU8vN1qCsNIbyH9H0u9jlf8EJt6xZb6eVJ3YLooC/ff8+WaScIHP/kbvz6A+o0B/F7NInni8Atisw4nFQ5mDjN/pBDdeYnEXWCX3EfD012G2EH9YcbuKhxtdyqz1ymvgqQ6xpgw8gltdQM13vQXwejrGofRZcIMIM7VhOxskvgZ7q8qWV5tlqtWneAMwuKeuI1Cet7L/Bsqu+GTC9VF1PYSzPxARq2Jwdf7UJ7mvb/t+PdNq4zK5w2+qiUSOxHwUA==\",\"body\":\"测试商品\",\"buyer_id\":\"2088512795384321\",\"invoice_amount\":\"0.01\",\"notify_id\":\"2020041000222143727084321412499469\",\"fund_bill_list\":\"[{\\\"amount\\\":\\\"0.01\\\",\\\"fundChannel\\\":\\\"PCREDIT\\\"}]\",\"notify_type\":\"trade_status_sync\",\"trade_status\":\"TRADE_SUCCESS\",\"receipt_amount\":\"0.01\",\"buyer_pay_amount\":\"0.01\",\"app_id\":\"2021001113639731\",\"sign_type\":\"RSA2\",\"seller_id\":\"2088631353531343\",\"gmt_payment\":\"2020-04-10 14:37:26\",\"notify_time\":\"2020-04-10 14:37:27\",\"version\":\"1.0\",\"out_trade_no\":\"TEST222222212312\",\"total_amount\":\"0.01\",\"trade_no\":\"2020041022001484321441793982\",\"auth_app_id\":\"2021001113639731\",\"buyer_logon_id\":\"156***@163.com\",\"point_amount\":\"0.00\"}";
Signature.callbackCheck(PayChannelEnum.ALI_PAY, certAlipayRequest, body, o -> {
    System.out.println(JsonUtils.stringify(o));
});
```


```Java
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

```