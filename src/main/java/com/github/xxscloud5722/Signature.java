package com.github.xxscloud5722;

import com.github.xxscloud5722.data.OrderInfo;
import com.github.xxscloud5722.data.PayChannelEnum;
import com.github.xxscloud5722.data.PayStatusEnum;
import com.github.xxscloud5722.gson.JsonObject;
import com.github.xxscloud5722.wxpay.WeChatPayClient;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.HashMap;

/**
 * @author Cat.
 * 异步回执签名校验类.
 */
@Slf4j
public final class Signature {

    /**
     * 回调函数检查.
     *
     * @param channel  渠道代码.
     * @param obj      客户端组件.
     * @param body     请求过来的消息body String.
     * @param header   请求过来的消息头.
     * @param callback 回执函数  如果执行失败抛出PayIOException异常会默认通知第三方失败.
     * @return 需要返回给第三方的字符串.
     */
    public static String callbackCheck(PayChannelEnum channel, Object obj, String body, HashMap<String, String> header,
                                       SignatureCallback callback) {

        try {
            final IPay pay = GPayFactory.getPay(channel, obj);
            if (!pay.checkSignature(header, body)) {
                throw new PayIOException("签名错误");
            }
            callback.run(parse(channel, body));
            return response(channel, true);
        } catch (PayIOException ex) {
            log.info("回执签名校验执行异常: " + ex);
            return response(channel, false);
        }
    }


    /**
     * 回调函数检查.
     *
     * @param channel  渠道代码.
     * @param obj      客户端组件.
     * @param body     请求过来的消息body String.
     * @param callback 回执函数  如果执行失败抛出PayIOException异常会默认通知第三方失败.
     * @return 需要返回给第三方的字符串.
     */
    public static String callbackCheck(PayChannelEnum channel, Object obj, String body,
                                       SignatureCallback callback) {

        return callbackCheck(channel, obj, body, null, callback);
    }

    @SneakyThrows
    private static OrderInfo parse(PayChannelEnum channel, String body) {
        final OrderInfo orderInfo;
        switch (channel) {
            case ALI_PAY:
                final JsonObject aliPayObject = new JsonObject(body);
                orderInfo = new OrderInfo();
                orderInfo.setFlowNo(aliPayObject.getString("out_trade_no"));
                orderInfo.setTransactionNo(aliPayObject.getString("trade_no"));
                orderInfo.setOpenId(aliPayObject.getString("buyer_id"));
                orderInfo.setTransactionType("");
                orderInfo.setStatus(PayStatusEnum.parse(aliPayObject.getString("trade_status")));
                orderInfo.setBankType("");
                orderInfo.setAmount(aliPayObject.getBigDecimal("total_amount"));
                orderInfo.setPayTime(aliPayObject.getDate("gmt_payment"));
                orderInfo.setCurrency("");
                orderInfo.setAttach(aliPayObject.getString("passback_params"));
                return orderInfo;
            case WE_CHAT_PAY:
                final JsonObject weChatObject = WeChatPayClient.toBean(body);
                orderInfo = new OrderInfo();
                orderInfo.setFlowNo(weChatObject.getString("out_trade_no"));
                orderInfo.setTransactionNo(weChatObject.getString("transaction_id"));
                orderInfo.setOpenId(weChatObject.getString("openid"));
                orderInfo.setTransactionType(weChatObject.getString("trade_type"));
                orderInfo.setStatus(PayStatusEnum.parse(weChatObject.getString("result_code")));
                orderInfo.setBankType("");
                orderInfo.setAmount(weChatObject.getBigDecimal("total_fee").divide(BigDecimal.valueOf(100), 2, BigDecimal.ROUND_DOWN));
                final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
                orderInfo.setPayTime(simpleDateFormat.parse(weChatObject.getString("time_end")));
                orderInfo.setCurrency("");
                orderInfo.setAttach(weChatObject.getString("attach"));
                return orderInfo;
            default:
                return new OrderInfo();
        }
    }

    private static String response(PayChannelEnum channel, boolean flag) {
        switch (channel) {
            case ALI_PAY:
                return flag
                        ? "success"
                        : "error";
            case WE_CHAT_PAY:
                return flag
                        ? "<xml><return_code><![CDATA[SUCCESS]]></return_code><return_msg><![CDATA[OK]]></return_msg></xml>"
                        : "<xml><return_code><![CDATA[FAIL]]></return_code><return_msg><![CDATA[ERROR]]></return_msg></xml>";
            default:
                throw new PayIOException("渠道错误");
        }
    }

    public interface SignatureCallback {
        void run(OrderInfo orderInfo);
    }
}
