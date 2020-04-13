package com.xxscloud.gpay.wxpay;

import com.xxscloud.gpay.IPay;
import com.xxscloud.gpay.PayException;
import com.xxscloud.gpay.PayIOException;
import com.gpay.pay.data.*;
import com.xxscloud.gpay.data.*;
import com.xxscloud.gpay.gson.JsonObject;
import com.xxscloud.gpay.gson.JsonUtils;
import com.gpay.pay.wxpay.request.*;
import com.gpay.pay.wxpay.response.*;
import com.xxscloud.gpay.wxpay.request.*;
import com.xxscloud.gpay.wxpay.response.*;
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


    public WeChatPay(Object obj) {
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
        final WxCreateOrderRequest orderRequest = new WxCreateOrderRequest();
        orderRequest.setBody(subject);
        orderRequest.setDetail(body);
        orderRequest.setOut_trade_no(flowNo);
        orderRequest.setTotal_fee(amount.setScale(2, BigDecimal.ROUND_DOWN).multiply(BigDecimal.valueOf(100)).toBigInteger());
        orderRequest.setNotify_url(notifyUrl);
        orderRequest.setTrade_type("MWEB");
        orderRequest.setAttach(attachArgs);
        orderRequest.setSpbill_create_ip(ip);
        final WxCreateOrderResponse orderInfo = createOrder(orderRequest);
        return orderInfo.getMweb_url();
    }

    @Override
    public String pcPay(String ip, String flowNo, BigDecimal amount, String subject, String body, String requestUrl, String notifyUrl, String attachArgs) {
        throw new PayException("wx pay no pc payment");
    }

    @Override
    public String appPay(String ip, String flowNo, BigDecimal amount, String subject, String body, String notifyUrl, String attachArgs) {
        final WxCreateOrderRequest orderRequest = new WxCreateOrderRequest();
        orderRequest.setBody(subject);
        orderRequest.setDetail(body);
        orderRequest.setOut_trade_no(flowNo);
        orderRequest.setTotal_fee(amount.setScale(2, BigDecimal.ROUND_DOWN).multiply(BigDecimal.valueOf(100)).toBigInteger());
        orderRequest.setNotify_url(notifyUrl);
        orderRequest.setTrade_type("APP");
        orderRequest.setAttach(attachArgs);
        orderRequest.setSpbill_create_ip(ip);
        final WxCreateOrderResponse orderInfo = createOrder(orderRequest);

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
        final WxCreateOrderResponse orderInfo = createOrder(orderRequest);

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
        final WxCreateOrderResponse orderInfo = createOrder(orderRequest);
        return orderInfo.getCode_url();
    }

    @Override
    public String appletsPay(String ip, String flowNo, BigDecimal amount, String subject, String body, String requestUrl,
                             String notifyUrl, String attachArgs, String openId) {
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
        final WxCreateOrderResponse orderInfo = createOrder(orderRequest);
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
        final WxQueryOrderRequest request = new WxQueryOrderRequest();
        request.setAppid(weChatPayClient.getAppId());
        request.setMch_id(weChatPayClient.getMerchantId());
        request.setOut_trade_no(flowNo);
        request.setNonce_str(UUID.randomUUID().toString().replace("-", ""));
        final WxQueryOrderResponse response = weChatPayClient.execute(QUERY_ORDER, request);
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
        final WxQueryRefundOrderRequest request = new WxQueryRefundOrderRequest();
        request.setAppid(weChatPayClient.getAppId());
        request.setMch_id(weChatPayClient.getMerchantId());
        request.setNonce_str(UUID.randomUUID().toString().replace("-", ""));
        request.setOut_trade_no(flowNo);
        request.setOut_refund_no(refundNo);
        request.setOffset(String.valueOf(offset));

        final WxQueryRefundOrderResponse response = weChatPayClient.execute(QUERY_REFUND_ORDER, request);
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
        final WxRefundOrderResponse response = weChatPayClient.execute(REFUND_ORDER, request);
        return Objects.equals(response.getResult_code(), "SUCCESS");
    }

    @Override
    public boolean closeOrder(String flowNo) {
        final WxCloseOrderRequest request = new WxCloseOrderRequest();
        request.setAppid(weChatPayClient.getAppId());
        request.setMch_id(weChatPayClient.getMerchantId());
        request.setOut_trade_no(flowNo);
        request.setNonce_str(UUID.randomUUID().toString().replace("-", ""));
        final WxCloseOrderResponse response = weChatPayClient.execute(CLOSE_ORDER, request);
        return Objects.equals(response.getResult_code(), "SUCCESS");
    }

    @Override
    public boolean transfer(String flowNo, BigDecimal amount, String payeeId, String realName, String attachArgs) {
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
        final WxTransferOrderResponse response = weChatPayClient.execute(TRANSFERS, request);
        return Objects.equals(response.getResult_code(), "SUCCESS");
    }

    @Override
    public boolean transferBankCard(String flowNo, BigDecimal amount, String bankCardNo, String realName, String bankCode, String attachArgs) {
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
        final WxTransferBankCardOrderReqsponse response = weChatPayClient.execute(TRANSFERS, request);
        return Objects.equals(response.getResult_code(), "SUCCESS");
    }

    @Override
    public TransferInfo queryTransferInfo(String flowNo) {
        if (weChatPayClient.getCertificate() == null) {
            throw new PayIOException("查询转账需要证书: https://pay.weixin.qq.com/wiki/doc/api/H5.php?chapter=4_3");
        }
        final WxQueryTransferOrderRequest request = new WxQueryTransferOrderRequest();
        request.setAppid(weChatPayClient.getAppId());
        request.setMch_id(weChatPayClient.getMerchantId());
        request.setPartner_trade_no(flowNo);
        request.setNonce_str(UUID.randomUUID().toString().replace("-", ""));
        final WxQueryTransferOrderResponse response = weChatPayClient.execute(QUERY_TRANSFERS, request);
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
