package com.github.xxscloud5722.alipay;

import com.alipay.api.AlipayApiException;
import com.alipay.api.CertAlipayRequest;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.*;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.*;
import com.alipay.api.response.*;
import com.github.xxscloud5722.IPay;
import com.github.xxscloud5722.PayIOException;
import com.github.xxscloud5722.GPayFactory;
import com.github.xxscloud5722.PayException;
import com.github.xxscloud5722.data.*;
import com.github.xxscloud5722.gson.JsonObject;
import com.github.xxscloud5722.gson.JsonUtils;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Objects;
import java.util.TreeMap;

@Slf4j
public class AliPay implements IPay {
    private final DefaultAlipayClient alipayClient;
    private final CertAlipayRequest certAlipayRequest;
    private final GPayFactory.PayLogCallback payLogCallback;

    @SneakyThrows
    public AliPay(Object obj, GPayFactory.PayLogCallback callback) {
        certAlipayRequest = (CertAlipayRequest) obj;
        alipayClient = new DefaultAlipayClient(certAlipayRequest);
        payLogCallback = callback == null ? (_1, _2, _3, _4) -> {
        } : callback;
    }

    @Override
    public String h5Pay(String ip, String flowNo, BigDecimal amount, String subject, String body,
                        String requestUrl, String notifyUrl, String attachArgs) {

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

        final AlipayTradeWapPayRequest request = new AlipayTradeWapPayRequest();
        request.setReturnUrl(requestUrl);
        request.setNotifyUrl(notifyUrl);

        final AlipayTradeWapPayModel wap = new AlipayTradeWapPayModel();
        wap.setProductCode("QUICK_WAP_PAY");
        wap.setOutTradeNo(flowNo);
        wap.setTotalAmount(amount.setScale(2, BigDecimal.ROUND_DOWN).toString());
        wap.setSubject(subject);
        wap.setBody(body);
        if (attachArgs != null && attachArgs.length() > 0) {
            wap.setPassbackParams(attachArgs);
        }
        request.setReturnUrl(requestUrl);
        request.setBizModel(wap);
        try {
            payLogCallback.run("h5Pay", flowNo, LogTypeEnum.REQUEST, JsonUtils.stringify(request));
            final AlipayTradeWapPayResponse response = alipayClient.pageExecute(request);
            payLogCallback.run("h5Pay", flowNo, LogTypeEnum.RESPONSE, response.getBody());
            if (!response.isSuccess()) {
                throw new PayIOException(response.getMsg());
            }
            return response.getBody();
        } catch (AlipayApiException e) {
            payLogCallback.run("h5Pay", flowNo, LogTypeEnum.ERROR, e.getMessage());
            throw new PayException(e);
        }
    }

    @Override
    public String pcPay(String ip, String flowNo, BigDecimal amount, String subject, String body,
                        String requestUrl, String notifyUrl, String attachArgs) {

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

        final AlipayTradePagePayRequest request = new AlipayTradePagePayRequest();
        final AlipayTradePagePayModel page = new AlipayTradePagePayModel();
        page.setOutTradeNo(flowNo);
        page.setProductCode("FAST_INSTANT_TRADE_PAY");
        page.setTotalAmount(amount.setScale(2, BigDecimal.ROUND_DOWN).toString());
        page.setSubject(subject);
        page.setBody(body);
        page.setPassbackParams(attachArgs);
        request.setReturnUrl(requestUrl);
        request.setNotifyUrl(notifyUrl);
        request.setBizModel(page);

        try {
            payLogCallback.run("pcPay", flowNo, LogTypeEnum.REQUEST, JsonUtils.stringify(request));
            final AlipayTradePagePayResponse response = alipayClient.pageExecute(request);
            payLogCallback.run("pcPay", flowNo, LogTypeEnum.RESPONSE, response.getBody());
            if (!response.isSuccess()) {
                throw new PayIOException(response.getMsg());
            }
            return response.getBody();
        } catch (AlipayApiException e) {
            payLogCallback.run("pcPay", flowNo, LogTypeEnum.ERROR, e.getMessage());
            throw new PayException(e);
        }
    }

    @Override
    public String appPay(String ip, String flowNo, BigDecimal amount, String subject, String body,
                         String notifyUrl, String attachArgs) {
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


        final AlipayTradeAppPayRequest request = new AlipayTradeAppPayRequest();
        final AlipayTradeAppPayModel app = new AlipayTradeAppPayModel();
        app.setBody(body);
        app.setSubject(subject);
        app.setOutTradeNo(flowNo);
        app.setTotalAmount(amount.setScale(2, BigDecimal.ROUND_DOWN).toString());
        app.setProductCode("QUICK_MSECURITY_PAY");
        app.setPassbackParams(attachArgs);
        request.setBizModel(app);
        request.setNotifyUrl(notifyUrl);

        try {
            payLogCallback.run("appPay", flowNo, LogTypeEnum.RESPONSE, JsonUtils.stringify(request));
            final AlipayTradeAppPayResponse response = alipayClient.sdkExecute(request);
            payLogCallback.run("appPay", flowNo, LogTypeEnum.RESPONSE, response.getBody());
            if (!response.isSuccess()) {
                throw new PayIOException(response.getMsg());
            }
            return response.getBody();
        } catch (AlipayApiException e) {
            payLogCallback.run("appPay", flowNo, LogTypeEnum.ERROR, e.getMessage());
            throw new PayException(e);
        }
    }

    @Override
    public String jsApiPay(String ip, String flowNo, BigDecimal amount, String subject, String body, String requestUrl,
                           String notifyUrl, String attachArgs, String openId) {
        throw new PayException("ali pay no jsapi payment");
    }

    @Override
    public String nativePay(String ip, String flowNo, BigDecimal amount, String subject, String body, String
            requestUrl, String notifyUrl, String attachArgs, String openId) {
        throw new PayException("ali pay no native payment");
    }

    @Override
    public String appletsPay(String ip, String flowNo, BigDecimal amount, String subject, String body, String requestUrl, String notifyUrl, String attachArgs, String openId) {
        throw new PayException("ali pay no applets payment");
    }

    @Override
    public OrderInfo queryOrder(String flowNo) {
        if (flowNo == null || flowNo.length() <= 0) {
            throw new PayException("流水号异常");
        }
        final AlipayTradeQueryRequest request = new AlipayTradeQueryRequest();
        final AlipayTradeQueryModel query = new AlipayTradeQueryModel();
        query.setOutTradeNo(flowNo);
        request.setBizModel(query);
        try {
            payLogCallback.run("queryOrder", flowNo, LogTypeEnum.REQUEST, JsonUtils.stringify(request));
            final AlipayTradeQueryResponse response = alipayClient.certificateExecute(request);
            payLogCallback.run("queryOrder", flowNo, LogTypeEnum.RESPONSE, response.getBody());
            if (!response.isSuccess()) {
                throw new PayIOException(response.getMsg());
            }
            final OrderInfo orderInfo = new OrderInfo();
            orderInfo.setFlowNo(response.getOutTradeNo());
            orderInfo.setTransactionNo(response.getTradeNo());
            orderInfo.setOpenId(response.getBuyerUserId());
            orderInfo.setTransactionType("");
            orderInfo.setStatus(PayStatusEnum.parse(response.getTradeStatus()));
            final StringBuilder banks = new StringBuilder();
            if (response.getFundBillList() != null) {
                response.getFundBillList().forEach(it -> banks.append(it.getBankCode()).append("=").append(it.getAmount()).append("&"));
                if (banks.length() > 0) {
                    banks.delete(banks.length() - 1, banks.length());
                }
            }
            orderInfo.setBankType(banks.toString());
            orderInfo.setAmount(new BigDecimal(response.getTotalAmount()));
            orderInfo.setPayTime(response.getSendPayDate());
            orderInfo.setCurrency(response.getSettleCurrency() == null ? "CNY" : response.getSettleCurrency());
            orderInfo.setAttach("");
            return orderInfo;
        } catch (AlipayApiException e) {
            payLogCallback.run("queryOrder", flowNo, LogTypeEnum.ERROR, e.getMessage());
            throw new PayException(e);
        }
    }

    @Override
    public RefundOrderInfo queryRefundOrderInfo(String flowNo, String refundNo, int offset) {
        if (flowNo == null || flowNo.length() <= 0) {
            throw new PayException("流水号异常");
        }
        if (refundNo == null || refundNo.length() <= 0) {
            throw new PayException("退款流水号异常");
        }
        final AlipayTradeFastpayRefundQueryRequest request = new AlipayTradeFastpayRefundQueryRequest();
        final AlipayTradeFastpayRefundQueryModel refund = new AlipayTradeFastpayRefundQueryModel();
        refund.setOutRequestNo(refundNo);
        refund.setOutTradeNo(flowNo);
        request.setBizModel(refund);

        try {
            payLogCallback.run("queryRefundOrderInfo", flowNo, LogTypeEnum.REQUEST, JsonUtils.stringify(request));
            final AlipayTradeFastpayRefundQueryResponse response = alipayClient.certificateExecute(request);
            payLogCallback.run("queryRefundOrderInfo", flowNo, LogTypeEnum.RESPONSE, response.getBody());
            if (!response.isSuccess()) {
                throw new PayIOException(response.getSubCode() + ":" + response.getSubMsg());
            }
            final RefundOrderInfo refundOrderInfo = new RefundOrderInfo();
            refundOrderInfo.setFlowNo(flowNo);
            refundOrderInfo.setRefundNo(refundNo);
            refundOrderInfo.setTransactionNo(response.getTradeNo());
            refundOrderInfo.setRefundReason(response.getRefundReason());
            refundOrderInfo.setStatus(RefundStatusEnum.SUCCESS);
            refundOrderInfo.setRefundAmount(response.getTotalAmount() == null ? null : new BigDecimal(response.getTotalAmount()));
            refundOrderInfo.setRefundDate(response.getGmtRefundPay());
            refundOrderInfo.setRemark("如果支付宝返回部分需要开通权限支付宝才会返回");
            return refundOrderInfo;
        } catch (AlipayApiException e) {
            payLogCallback.run("queryRefundOrderInfo", flowNo, LogTypeEnum.ERROR, e.getMessage());
            throw new PayException(e);
        }
    }


    @Override
    public boolean closeOrder(String flowNo) {
        if (flowNo == null || flowNo.length() <= 0) {
            throw new PayException("流水号异常");
        }

        final AlipayTradeCloseRequest request = new AlipayTradeCloseRequest();
        final AlipayTradeCloseModel transaction = new AlipayTradeCloseModel();
        transaction.setOutTradeNo(flowNo);
        request.setBizModel(transaction);
        try {
            payLogCallback.run("closeOrder", flowNo, LogTypeEnum.REQUEST, JsonUtils.stringify(request));
            final AlipayTradeCloseResponse response = alipayClient.execute(request);
            payLogCallback.run("closeOrder", flowNo, LogTypeEnum.RESPONSE, response.getBody());

            if (!response.isSuccess()) {
                throw new PayIOException(response.getSubCode() + ":" + response.getSubMsg());
            }
            return true;
        } catch (AlipayApiException e) {
            payLogCallback.run("closeOrder", flowNo, LogTypeEnum.ERROR, e.getMessage());
            throw new PayException(e);
        }
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

        final AlipayTradeRefundRequest request = new AlipayTradeRefundRequest();
        final AlipayTradeRefundModel transaction = new AlipayTradeRefundModel();
        transaction.setOutTradeNo(refundNo);
        transaction.setRefundAmount(refundAmount.setScale(2, BigDecimal.ROUND_DOWN).toString());
        transaction.setRefundReason(refundReason);

        request.setBizModel(transaction);
        try {
            payLogCallback.run("refundOrder", flowNo, LogTypeEnum.REQUEST, JsonUtils.stringify(request));
            final AlipayTradeRefundResponse response = alipayClient.execute(request);
            payLogCallback.run("refundOrder", flowNo, LogTypeEnum.RESPONSE, response.getBody());

            if (!response.isSuccess()) {
                throw new PayIOException(response.getSubCode() + ":" + response.getSubMsg());
            }
            return true;
        } catch (AlipayApiException e) {
            payLogCallback.run("refundOrder", flowNo, LogTypeEnum.ERROR, e.getMessage());
            throw new PayException(e);
        }
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

        final AlipayFundTransUniTransferRequest request = new AlipayFundTransUniTransferRequest();
        final AlipayFundTransUniTransferModel transfer = new AlipayFundTransUniTransferModel();
        transfer.setOutBizNo(flowNo);
        transfer.setTransAmount(amount.setScale(2, BigDecimal.ROUND_DOWN).toString());
        transfer.setProductCode("TRANS_ACCOUNT_NO_PWD");
        transfer.setBizScene("DIRECT_TRANSFER");
        final Participant participant = new Participant();
        participant.setIdentity(payeeId);
        participant.setIdentityType("ALIPAY_LOGON_ID");
        participant.setName(realName);
        transfer.setPayeeInfo(participant);
        transfer.setRemark(attachArgs);
        request.setBizModel(transfer);
        try {
            payLogCallback.run("transfer", flowNo, LogTypeEnum.REQUEST, JsonUtils.stringify(request));
            final AlipayFundTransUniTransferResponse response = alipayClient.certificateExecute(request);
            payLogCallback.run("transfer", flowNo, LogTypeEnum.RESPONSE, response.getBody());

            if (!response.isSuccess()) {
                throw new PayIOException(response.getSubCode() + ":" + response.getSubMsg());
            }
            return true;
        } catch (AlipayApiException e) {
            payLogCallback.run("transfer", flowNo, LogTypeEnum.ERROR, e.getMessage());
            throw new PayException(e);
        }
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
        final AlipayFundTransUniTransferRequest request = new AlipayFundTransUniTransferRequest();
        final AlipayFundTransUniTransferModel transfer = new AlipayFundTransUniTransferModel();
        transfer.setOutBizNo(flowNo);
        transfer.setTransAmount(amount.setScale(2, BigDecimal.ROUND_DOWN).toString());
        transfer.setProductCode("TRANS_BANKCARD_NO_PWD");
        transfer.setBizScene("DIRECT_TRANSFER");
        final Participant participant = new Participant();
        participant.setIdentity(bankCardNo);
        participant.setIdentityType("ALIPAY_LOGON_ID");
        participant.setName(realName);
        transfer.setPayeeInfo(participant);
        transfer.setRemark(attachArgs);
        request.setBizModel(transfer);
        try {
            payLogCallback.run("transferBankCard", flowNo, LogTypeEnum.REQUEST, JsonUtils.stringify(request));
            final AlipayFundTransUniTransferResponse response = alipayClient.certificateExecute(request);
            payLogCallback.run("transferBankCard", flowNo, LogTypeEnum.RESPONSE, response.getBody());

            if (!response.isSuccess()) {
                throw new PayIOException(response.getSubCode() + ":" + response.getSubMsg());
            }
            return true;
        } catch (AlipayApiException e) {
            payLogCallback.run("transferBankCard", flowNo, LogTypeEnum.ERROR, e.getMessage());
            throw new PayException(e);
        }
    }

    @Override
    public TransferInfo queryTransferInfo(String flowNo) {
        if (flowNo == null || flowNo.length() <= 0) {
            throw new PayException("流水号异常");
        }
        final AlipayFundTransOrderQueryRequest request = new AlipayFundTransOrderQueryRequest();
        final AlipayFundTransOrderQueryModel trans = new AlipayFundTransOrderQueryModel();
        trans.setOutBizNo(flowNo);
        request.setBizModel(trans);
        try {
            payLogCallback.run("queryTransferInfo", flowNo, LogTypeEnum.REQUEST, JsonUtils.stringify(request));
            final AlipayFundTransOrderQueryResponse response = alipayClient.certificateExecute(request);
            payLogCallback.run("queryTransferInfo", flowNo, LogTypeEnum.RESPONSE, response.getBody());

            if (!response.isSuccess()) {
                throw new PayIOException(response.getSubCode() + ":" + response.getSubMsg());
            }
            final TransferInfo transferInfo = new TransferInfo();
            transferInfo.setFlowNo(flowNo);
            transferInfo.setTransactionNo(response.getOrderId());
            transferInfo.setStatus(TransferStatusEnum.parse(response.getStatus()));
            transferInfo.setOpenId("");
            transferInfo.setRealName("");
            transferInfo.setTransferTime(null);
            final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            transferInfo.setPayTime(simpleDateFormat.parse(response.getPayDate()));
            transferInfo.setAmount(null);
            transferInfo.setFailReason(response.getFailReason());
            transferInfo.setAttach("");
            return transferInfo;
        } catch (AlipayApiException | ParseException e) {
            payLogCallback.run("queryTransferInfo", flowNo, LogTypeEnum.ERROR, e.getMessage());
            throw new PayException(e);
        }
    }

    @Override
    public boolean checkSignature(HashMap<String, String> header, String body) {
        final TreeMap<String, String> treeMap = new TreeMap<>();
        final JsonObject data = new JsonObject(body);
        data.forEach((k, v) -> {
            if (!Objects.equals(k, "sign") && !Objects.equals(k, "sign_type")) {
                treeMap.put(k, v.toString());
            }
        });
        final String sign = data.getString("sign");
        final StringBuilder stringBuilder = new StringBuilder();
        treeMap.forEach((k, v) -> stringBuilder.append(k).append("=").append(v).append("&"));
        stringBuilder.delete(stringBuilder.length() - 1, stringBuilder.length());
        log.info("[AliPayService] 支付宝回调参数待加密：{}", stringBuilder.toString());
        log.info("[AliPayService] 支付宝签名：{}", sign);
        try {
            return AlipaySignature.rsa256CheckContent(
                    stringBuilder.toString(), sign,
                    AlipaySignature.getAlipayPublicKey(certAlipayRequest.getAlipayPublicCertPath()),
                    "UTF-8");
        } catch (AlipayApiException e) {
            e.printStackTrace();
            return false;
        }
    }
}
