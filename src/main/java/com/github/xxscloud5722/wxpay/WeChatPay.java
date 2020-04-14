package com.github.xxscloud5722.wxpay;

import com.github.xxscloud5722.IPay;
import com.github.xxscloud5722.PayIOException;
import com.github.xxscloud5722.data.*;
import com.github.xxscloud5722.wxpay.request.*;
import com.github.xxscloud5722.wxpay.response.*;
import com.github.xxscloud5722.GPayFactory;
import com.github.xxscloud5722.PayException;
import com.github.xxscloud5722.gson.JsonObject;
import com.github.xxscloud5722.gson.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;


import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Objects;
import java.util.TreeMap;
import java.util.UUID;

@Slf4j
public class WeChatPay implements IPay {

    private final WeChatPayClient weChatPayClient;
    private static final String CREATE_ORDER = "https://api.mch.weixin.qq.com/pay/unifiedorder";
    private static final String QUERY_ORDER = "https://api.mch.weixin.qq.com/pay/orderquery";
    private static final String CLOSE_ORDER = "https://api.mch.weixin.qq.com/pay/closeorder";
    private static final String REFUND_ORDER = "https://api.mch.weixin.qq.com/secapi/pay/refund";
    private static final String QUERY_REFUND_ORDER = "https://api.mch.weixin.qq.com/pay/refundquery";

    private static final String TRANSFERS = "https://api.mch.weixin.qq.com/mmpaymkttransfers/promotion/transfers";
    private static final String QUERY_TRANSFERS = "https://api.mch.weixin.qq.com/pay/refundquery";
    private final GPayFactory.PayLogCallback payLogCallback;


    public WeChatPay(Object obj, GPayFactory.PayLogCallback callback) {
        payLogCallback = callback == null ? (_1, _2, _3, _4) -> {
        } : callback;
        weChatPayClient = (WeChatPayClient) obj;
    }

    private WxCreateOrderResponse createOrder(WxCreateOrderRequest orderRequest) {
        orderRequest.setAppid(weChatPayClient.getAppId());
        orderRequest.setMch_id(weChatPayClient.getMerchantId());
        orderRequest.setNonce_str(UUID.randomUUID().toString().replace("-", ""));
        return weChatPayClient.execute(CREATE_ORDER, orderRequest);
    }

    @Override
    public String h5Pay(String ip, String flowNo, BigDecimal amount, String subject, String body, String requestUrl, String notifyUrl, String attachArgs) {
        if (ip == null || ip.length() <= 0) {
            throw new PayException("IP信息异常");
        }
        if (flowNo == null || flowNo.length() <= 0) {
            throw new PayException("流水号异常");
        }
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new PayException("金额异常");
        }
        if (subject == null || subject.length() <= 0) {
            throw new PayException("支付信息头异常");
        }
        if (body == null || body.length() <= 0) {
            throw new PayException("支付信息正文异常");
        }
        if (notifyUrl == null || notifyUrl.length() <= 0) {
            throw new PayException("通知地址异常");
        }
        if (requestUrl == null || requestUrl.length() <= 0) {
            throw new PayException("请求地址异常");
        }

        final WxCreateOrderRequest orderRequest = new WxCreateOrderRequest();
        orderRequest.setBody(subject);
        orderRequest.setDetail(body);
        orderRequest.setOut_trade_no(flowNo);
        orderRequest.setTotal_fee(amount.setScale(2, BigDecimal.ROUND_DOWN).multiply(BigDecimal.valueOf(100)).toBigInteger());
        orderRequest.setNotify_url(notifyUrl);
        orderRequest.setTrade_type("MWEB");
        orderRequest.setAttach(attachArgs);
        orderRequest.setSpbill_create_ip(ip);
        payLogCallback.run("h5Pay", flowNo, LogTypeEnum.REQUEST, JsonUtils.stringify(orderRequest));
        final WxCreateOrderResponse orderInfo = createOrder(orderRequest);
        payLogCallback.run("h5Pay", flowNo, LogTypeEnum.RESPONSE, JsonUtils.stringify(orderInfo));
        return orderInfo.getMweb_url();
    }

    @Override
    public String pcPay(String ip, String flowNo, BigDecimal amount, String subject, String body, String requestUrl, String notifyUrl, String attachArgs) {
        throw new PayException("wx pay no pc payment");
    }

    @Override
    public String appPay(String ip, String flowNo, BigDecimal amount, String subject, String body, String notifyUrl, String attachArgs) {
        if (ip == null || ip.length() <= 0) {
            throw new PayException("IP信息异常");
        }
        if (flowNo == null || flowNo.length() <= 0) {
            throw new PayException("流水号异常");
        }
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new PayException("金额异常");
        }
        if (subject == null || subject.length() <= 0) {
            throw new PayException("支付信息头异常");
        }
        if (body == null || body.length() <= 0) {
            throw new PayException("支付信息正文异常");
        }
        if (notifyUrl == null || notifyUrl.length() <= 0) {
            throw new PayException("通知地址异常");
        }
        final WxCreateOrderRequest orderRequest = new WxCreateOrderRequest();
        orderRequest.setBody(subject);
        orderRequest.setDetail(body);
        orderRequest.setOut_trade_no(flowNo);
        orderRequest.setTotal_fee(amount.setScale(2, BigDecimal.ROUND_DOWN).multiply(BigDecimal.valueOf(100)).toBigInteger());
        orderRequest.setNotify_url(notifyUrl);
        orderRequest.setTrade_type("APP");
        orderRequest.setAttach(attachArgs);
        orderRequest.setSpbill_create_ip(ip);
        payLogCallback.run("h5Pay", flowNo, LogTypeEnum.REQUEST, JsonUtils.stringify(orderRequest));
        final WxCreateOrderResponse orderInfo = createOrder(orderRequest);
        payLogCallback.run("h5Pay", flowNo, LogTypeEnum.RESPONSE, JsonUtils.stringify(orderInfo));

        final WxAPPPayRequest request = new WxAPPPayRequest();
        request.setAppid(weChatPayClient.getAppId());
        request.setPartnerid(weChatPayClient.getMerchantId());
        request.setPrepayid(orderInfo.getPrepay_id());
        request.setPackages("Sign=WXPay");
        request.setNonceStr(UUID.randomUUID().toString().replace("-", ""));
        request.setTimeStamp(String.valueOf(System.currentTimeMillis()));
        return JsonUtils.stringify(weChatPayClient.sdkExecute(request));
    }

    @Override
    public String jsApiPay(String ip, String flowNo, BigDecimal amount, String subject, String body,
                           String requestUrl, String notifyUrl, String attachArgs, String openId) {
        if (ip == null || ip.length() <= 0) {
            throw new PayException("IP信息异常");
        }
        if (flowNo == null || flowNo.length() <= 0) {
            throw new PayException("流水号异常");
        }
        if (openId == null || openId.length() <= 0) {
            throw new PayException("OpenId信息异常");
        }
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new PayException("金额异常");
        }
        if (subject == null || subject.length() <= 0) {
            throw new PayException("支付信息头异常");
        }
        if (body == null || body.length() <= 0) {
            throw new PayException("支付信息正文异常");
        }
        if (notifyUrl == null || notifyUrl.length() <= 0) {
            throw new PayException("通知地址异常");
        }
        if (requestUrl == null || requestUrl.length() <= 0) {
            throw new PayException("请求地址异常");
        }
        final WxCreateOrderRequest orderRequest = new WxCreateOrderRequest();
        orderRequest.setBody(subject);
        orderRequest.setDetail(body);
        orderRequest.setOut_trade_no(flowNo);
        orderRequest.setTotal_fee(amount.setScale(2, BigDecimal.ROUND_DOWN).multiply(BigDecimal.valueOf(100)).toBigInteger());
        orderRequest.setNotify_url(notifyUrl);
        orderRequest.setTrade_type("JSAPI");
        orderRequest.setAttach(attachArgs);
        orderRequest.setSpbill_create_ip(ip);
        orderRequest.setOpenid(openId);
        payLogCallback.run("h5Pay", flowNo, LogTypeEnum.REQUEST, JsonUtils.stringify(orderRequest));
        final WxCreateOrderResponse orderInfo = createOrder(orderRequest);
        payLogCallback.run("h5Pay", flowNo, LogTypeEnum.RESPONSE, JsonUtils.stringify(orderInfo));

        final WxAPIPayRequest request = new WxAPIPayRequest();
        request.setAppId(weChatPayClient.getAppId());
        request.setTimeStamp(String.valueOf(System.currentTimeMillis()));
        request.setNonceStr(UUID.randomUUID().toString().replace("-", ""));
        request.setPackages("prepay_id=" + orderInfo.getPrepay_id());
        request.setSignType("MD5");
        return JsonUtils.stringify(weChatPayClient.sdkExecute(request));
    }

    @Override
    public String nativePay(String ip, String flowNo, BigDecimal amount, String subject, String body, String requestUrl,
                            String notifyUrl, String attachArgs, String openId) {
        if (ip == null || ip.length() <= 0) {
            throw new PayException("IP信息异常");
        }
        if (flowNo == null || flowNo.length() <= 0) {
            throw new PayException("流水号异常");
        }
        if (openId == null || openId.length() <= 0) {
            throw new PayException("OpenId信息异常");
        }
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new PayException("金额异常");
        }
        if (subject == null || subject.length() <= 0) {
            throw new PayException("支付信息头异常");
        }
        if (body == null || body.length() <= 0) {
            throw new PayException("支付信息正文异常");
        }
        if (notifyUrl == null || notifyUrl.length() <= 0) {
            throw new PayException("通知地址异常");
        }
        if (requestUrl == null || requestUrl.length() <= 0) {
            throw new PayException("请求地址异常");
        }
        final WxCreateOrderRequest orderRequest = new WxCreateOrderRequest();
        orderRequest.setBody(subject);
        orderRequest.setDetail(body);
        orderRequest.setOut_trade_no(flowNo);
        orderRequest.setTotal_fee(amount.setScale(2, BigDecimal.ROUND_DOWN).multiply(BigDecimal.valueOf(100)).toBigInteger());
        orderRequest.setNotify_url(notifyUrl);
        orderRequest.setTrade_type("NATIVE");
        orderRequest.setAttach(attachArgs);
        orderRequest.setSpbill_create_ip(ip);
        orderRequest.setOpenid(openId);
        payLogCallback.run("nativePay", flowNo, LogTypeEnum.REQUEST, JsonUtils.stringify(orderRequest));
        final WxCreateOrderResponse orderInfo = createOrder(orderRequest);
        payLogCallback.run("nativePay", flowNo, LogTypeEnum.RESPONSE, JsonUtils.stringify(orderInfo));

        return orderInfo.getCode_url();
    }

    @Override
    public String appletsPay(String ip, String flowNo, BigDecimal amount, String subject, String body, String requestUrl,
                             String notifyUrl, String attachArgs, String openId) {
        if (ip == null || ip.length() <= 0) {
            throw new PayException("IP信息异常");
        }
        if (flowNo == null || flowNo.length() <= 0) {
            throw new PayException("流水号异常");
        }
        if (openId == null || openId.length() <= 0) {
            throw new PayException("OpenId信息异常");
        }
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new PayException("金额异常");
        }
        if (subject == null || subject.length() <= 0) {
            throw new PayException("支付信息头异常");
        }
        if (body == null || body.length() <= 0) {
            throw new PayException("支付信息正文异常");
        }
        if (notifyUrl == null || notifyUrl.length() <= 0) {
            throw new PayException("通知地址异常");
        }
        if (requestUrl == null || requestUrl.length() <= 0) {
            throw new PayException("请求地址异常");
        }
        final WxCreateOrderRequest orderRequest = new WxCreateOrderRequest();
        orderRequest.setBody(subject);
        orderRequest.setDetail(body);
        orderRequest.setOut_trade_no(flowNo);
        orderRequest.setTotal_fee(amount.setScale(2, BigDecimal.ROUND_DOWN).multiply(BigDecimal.valueOf(100)).toBigInteger());
        orderRequest.setNotify_url(notifyUrl);
        orderRequest.setTrade_type("JSAPI");
        orderRequest.setAttach(attachArgs);
        orderRequest.setSpbill_create_ip(ip);
        orderRequest.setOpenid(openId);
        payLogCallback.run("appletsPay", flowNo, LogTypeEnum.REQUEST, JsonUtils.stringify(orderRequest));
        final WxCreateOrderResponse orderInfo = createOrder(orderRequest);
        payLogCallback.run("appletsPay", flowNo, LogTypeEnum.RESPONSE, JsonUtils.stringify(orderInfo));

        final WxAppletsPayRequest wxAppletsPayRequest = new WxAppletsPayRequest();
        wxAppletsPayRequest.setAppId(weChatPayClient.getAppId());
        wxAppletsPayRequest.setTimeStamp(String.valueOf(System.currentTimeMillis()));
        wxAppletsPayRequest.setNonceStr(UUID.randomUUID().toString().replace("-", ""));
        wxAppletsPayRequest.setPackages("prepay_id=" + orderInfo.getPrepay_id());
        wxAppletsPayRequest.setSignType("md5");
        return JsonUtils.stringify(weChatPayClient.sdkExecute(wxAppletsPayRequest));
    }

    @Override
    public OrderInfo queryOrder(String flowNo) {
        if (flowNo == null || flowNo.length() <= 0) {
            throw new PayException("流水号异常");
        }
        final WxQueryOrderRequest request = new WxQueryOrderRequest();
        request.setAppid(weChatPayClient.getAppId());
        request.setMch_id(weChatPayClient.getMerchantId());
        request.setOut_trade_no(flowNo);
        request.setNonce_str(UUID.randomUUID().toString().replace("-", ""));
        payLogCallback.run("queryOrder", flowNo, LogTypeEnum.REQUEST, JsonUtils.stringify(request));
        final WxQueryOrderResponse response = weChatPayClient.execute(QUERY_ORDER, request);
        payLogCallback.run("queryOrder", flowNo, LogTypeEnum.RESPONSE, JsonUtils.stringify(response));

        final OrderInfo orderInfo = new OrderInfo();
        orderInfo.setFlowNo(response.getOut_trade_no());
        orderInfo.setTransactionNo(response.getTransaction_id());
        orderInfo.setOpenId(response.getOpenid());
        orderInfo.setTransactionType(response.getTrade_type());
        orderInfo.setStatus(PayStatusEnum.parse(response.getTrade_state()));
        orderInfo.setBankType(response.getBank_type());
        orderInfo.setAmount(new BigDecimal(response.getTotal_fee()).divide(BigDecimal.valueOf(100), 2, BigDecimal.ROUND_DOWN));
        orderInfo.setCurrency(response.getFee_type());
        orderInfo.setAttach(response.getAttach());
        final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        try {
            orderInfo.setPayTime(response.getTime_end() != null && response.getTime_end().length() > 12
                    ? simpleDateFormat.parse(response.getTime_end())
                    : null);
        } catch (ParseException e) {
            throw new PayException("payTime is fail");
        }
        return orderInfo;
    }

    @Override
    public RefundOrderInfo queryRefundOrderInfo(String flowNo, String refundNo, int offset) {
        if (flowNo == null || flowNo.length() <= 0) {
            throw new PayException("流水号异常");
        }
        if (refundNo == null || refundNo.length() <= 0) {
            throw new PayException("退款流水号异常");
        }
        final WxQueryRefundOrderRequest request = new WxQueryRefundOrderRequest();
        request.setAppid(weChatPayClient.getAppId());
        request.setMch_id(weChatPayClient.getMerchantId());
        request.setNonce_str(UUID.randomUUID().toString().replace("-", ""));
        request.setOut_trade_no(flowNo);
        request.setOut_refund_no(refundNo);
        request.setOffset(String.valueOf(offset));

        payLogCallback.run("queryRefundOrderInfo", flowNo, LogTypeEnum.REQUEST, JsonUtils.stringify(request));
        final WxQueryRefundOrderResponse response = weChatPayClient.execute(QUERY_REFUND_ORDER, request);
        payLogCallback.run("queryRefundOrderInfo", flowNo, LogTypeEnum.RESPONSE, JsonUtils.stringify(response));


        final RefundOrderInfo refundOrderInfo = new RefundOrderInfo();
        refundOrderInfo.setFlowNo(flowNo);
        refundOrderInfo.setRefundNo(refundNo);
        refundOrderInfo.setTransactionNo(response.getTransaction_id());
        refundOrderInfo.setRefundReason("");
        refundOrderInfo.setRefundAmount(new BigDecimal(response.getTotal_fee()).divide(BigDecimal.valueOf(100), 2, BigDecimal.ROUND_DOWN));
        refundOrderInfo.setStatus(RefundStatusEnum.parse(response.getRefund_status_0()));
        final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            refundOrderInfo.setRefundDate(simpleDateFormat.parse(response.getRefund_success_time_0()));
        } catch (ParseException e) {
            throw new PayIOException("数据解析异常");
        }
        refundOrderInfo.setRemark("微信退款");
        return refundOrderInfo;
    }

    @Override
    public boolean refundOrder(String flowNo, String refundNo, BigDecimal orderAmount, BigDecimal refundAmount, String refundReason) {
        if (flowNo == null || flowNo.length() <= 0) {
            throw new PayException("流水号异常");
        }
        if (refundNo == null || refundNo.length() <= 0) {
            throw new PayException("退款流水号异常");
        }
        if (orderAmount == null || orderAmount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new PayException("订单金额异常");
        }
        if (refundAmount == null || refundAmount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new PayException("退款金额异常");
        }
        if (refundReason == null || refundReason.length() <= 0) {
            throw new PayException("退款原因异常");
        }
        if (weChatPayClient.getCertificate() == null) {
            throw new PayIOException("退款需要证书: https://pay.weixin.qq.com/wiki/doc/api/H5.php?chapter=4_3");
        }
        final WxRefundOrderRequest request = new WxRefundOrderRequest();
        request.setAppid(weChatPayClient.getAppId());
        request.setMch_id(weChatPayClient.getMerchantId());
        request.setNonce_str(weChatPayClient.getAppId());
        request.setOut_trade_no(flowNo);
        request.setOut_refund_no(refundNo);
        request.setTotal_fee(String.valueOf(orderAmount.setScale(2, BigDecimal.ROUND_DOWN).multiply(BigDecimal.valueOf(100)).toBigInteger()));
        request.setRefund_fee(String.valueOf(refundAmount.setScale(2, BigDecimal.ROUND_DOWN).multiply(BigDecimal.valueOf(100)).toBigInteger()));
        request.setRefund_desc(refundReason);

        payLogCallback.run("refundOrder", flowNo, LogTypeEnum.REQUEST, JsonUtils.stringify(request));
        final WxRefundOrderResponse response = weChatPayClient.execute(REFUND_ORDER, request);
        payLogCallback.run("refundOrder", flowNo, LogTypeEnum.RESPONSE, JsonUtils.stringify(response));

        return Objects.equals(response.getResult_code(), "SUCCESS");
    }

    @Override
    public boolean closeOrder(String flowNo) {
        if (flowNo == null || flowNo.length() <= 0) {
            throw new PayException("流水号异常");
        }
        final WxCloseOrderRequest request = new WxCloseOrderRequest();
        request.setAppid(weChatPayClient.getAppId());
        request.setMch_id(weChatPayClient.getMerchantId());
        request.setOut_trade_no(flowNo);
        request.setNonce_str(UUID.randomUUID().toString().replace("-", ""));
        payLogCallback.run("closeOrder", flowNo, LogTypeEnum.REQUEST, JsonUtils.stringify(request));
        final WxCloseOrderResponse response = weChatPayClient.execute(CLOSE_ORDER, request);
        payLogCallback.run("closeOrder", flowNo, LogTypeEnum.RESPONSE, JsonUtils.stringify(response));

        return Objects.equals(response.getResult_code(), "SUCCESS");
    }

    @Override
    public boolean transfer(String flowNo, BigDecimal amount, String payeeId, String realName, String attachArgs) {
        if (flowNo == null || flowNo.length() <= 0) {
            throw new PayException("流水号异常");
        }
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new PayException("金额异常");
        }
        if (payeeId == null || payeeId.length() <= 0) {
            throw new PayException("收款人信息异常");
        }
        if (realName == null || realName.length() <= 0) {
            throw new PayException("真实姓名信息异常");
        }
        if (weChatPayClient.getCertificate() == null) {
            throw new PayIOException("转账需要证书: https://pay.weixin.qq.com/wiki/doc/api/H5.php?chapter=4_3");
        }
        final WxTransferOrderRequest request = new WxTransferOrderRequest();
        request.setMch_appid(weChatPayClient.getAppId());
        request.setMchid(weChatPayClient.getMerchantId());
        request.setNonce_str(UUID.randomUUID().toString().replace("-", ""));
        request.setPartner_trade_no(flowNo);
        request.setOpenid(payeeId);
        request.setCheck_name("FORCE_CHECK");
        request.setRe_user_name(realName);
        request.setAmount(String.valueOf(amount.multiply(BigDecimal.valueOf(100)).setScale(2, BigDecimal.ROUND_DOWN).toBigInteger()));
        request.setDesc(attachArgs);
        payLogCallback.run("transfer", flowNo, LogTypeEnum.REQUEST, JsonUtils.stringify(request));
        final WxTransferOrderResponse response = weChatPayClient.execute(TRANSFERS, request);
        payLogCallback.run("transfer", flowNo, LogTypeEnum.RESPONSE, JsonUtils.stringify(response));

        return Objects.equals(response.getResult_code(), "SUCCESS");
    }

    @Override
    public boolean transferBankCard(String flowNo, BigDecimal amount, String bankCardNo, String realName, String bankCode, String attachArgs) {
        if (flowNo == null || flowNo.length() <= 0) {
            throw new PayException("流水号异常");
        }
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new PayException("金额异常");
        }
        if (bankCardNo == null || bankCardNo.length() <= 0) {
            throw new PayException("卡号信息异常");
        }
        if (bankCode == null || bankCode.length() <= 0) {
            throw new PayException("银行代码信息异常");
        }
        if (realName == null || realName.length() <= 0) {
            throw new PayException("真实姓名信息异常");
        }
        if (weChatPayClient.getCertificate() == null) {
            throw new PayIOException("转账需要证书: https://pay.weixin.qq.com/wiki/doc/api/H5.php?chapter=4_3");
        }
        final WxTransferBankCardOrderRequest request = new WxTransferBankCardOrderRequest();
        request.setMchid(weChatPayClient.getMerchantId());
        request.setNonce_str(UUID.randomUUID().toString().replace("-", ""));
        request.setPartner_trade_no(flowNo);
        request.setBank_code(bankCardNo);
        request.setEnc_true_name(realName);
        request.setBank_code(bankCode);
        request.setAmount(String.valueOf(amount.multiply(BigDecimal.valueOf(100)).setScale(2, BigDecimal.ROUND_DOWN).toBigInteger()));
        request.setDesc(attachArgs);
        payLogCallback.run("transferBankCard", flowNo, LogTypeEnum.REQUEST, JsonUtils.stringify(request));
        final WxTransferBankCardOrderReqsponse response = weChatPayClient.execute(TRANSFERS, request);
        payLogCallback.run("transferBankCard", flowNo, LogTypeEnum.RESPONSE, JsonUtils.stringify(response));

        return Objects.equals(response.getResult_code(), "SUCCESS");
    }

    @Override
    public TransferInfo queryTransferInfo(String flowNo) {
        if (flowNo == null || flowNo.length() <= 0) {
            throw new PayException("流水号异常");
        }
        if (weChatPayClient.getCertificate() == null) {
            throw new PayIOException("查询转账需要证书: https://pay.weixin.qq.com/wiki/doc/api/H5.php?chapter=4_3");
        }
        final WxQueryTransferOrderRequest request = new WxQueryTransferOrderRequest();
        request.setAppid(weChatPayClient.getAppId());
        request.setMch_id(weChatPayClient.getMerchantId());
        request.setPartner_trade_no(flowNo);
        request.setNonce_str(UUID.randomUUID().toString().replace("-", ""));
        payLogCallback.run("queryTransferInfo", flowNo, LogTypeEnum.REQUEST, JsonUtils.stringify(request));
        final WxQueryTransferOrderResponse response = weChatPayClient.execute(QUERY_TRANSFERS, request);
        payLogCallback.run("queryTransferInfo", flowNo, LogTypeEnum.RESPONSE, JsonUtils.stringify(response));

        final TransferInfo transferInfo = new TransferInfo();
        transferInfo.setFlowNo(flowNo);
        transferInfo.setTransactionNo(response.getDetail_id());
        transferInfo.setOpenId(response.getOpenid());
        transferInfo.setStatus(null);
        transferInfo.setFailReason(response.getReason());
        transferInfo.setRealName(response.getTransfer_name());
        transferInfo.setAmount(new BigDecimal(response.getTransfer_name()).multiply(BigDecimal.valueOf(100)));
        final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            transferInfo.setTransferTime(simpleDateFormat.parse(response.getTransfer_time()));
            transferInfo.setPayTime(simpleDateFormat.parse(response.getPayment_time()));
        } catch (ParseException e) {
            throw new PayIOException("解析参数异常");
        }
        transferInfo.setAttach(response.getDesc());
        return transferInfo;
    }

    @Override
    public boolean checkSignature(HashMap<String, String> header, String body) {
        final JsonObject data = weChatPayClient.toBean(body);
        final TreeMap<String, Object> treeMap = new TreeMap<>();
        data.forEach(treeMap::put);
        final StringBuilder content = new StringBuilder();
        treeMap.forEach((k, v) -> {
            if (k.contains("sign") || v == null || Objects.equals(v, "")) {
                return;
            }
            content.append(k).append("=").append(v).append("&");
        });
        if (content.length() > 0) {
            content.delete(content.length() - 1, content.length());
        }
        final String sign = data.getString("sign");
        content.append("&key=").append(weChatPayClient.getKey());
        log.info("[WeChatService] 微信回调参数待加密：" + content.toString());
        log.info("[WeChatService] 微信签名：" + sign);
        return sign != null && Objects.equals(DigestUtils.md5Hex(content.toString()).toUpperCase(), sign.toUpperCase());
    }
}
