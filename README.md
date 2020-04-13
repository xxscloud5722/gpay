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
```
