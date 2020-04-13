package com.xxscloud.gpay;

import com.xxscloud.gpay.data.OrderInfo;
import com.xxscloud.gpay.data.PayChannelEnum;
import lombok.extern.slf4j.Slf4j;

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
            callback.run(parse(body));
            return response(channel, true);
        } catch (PayIOException ex) {
            log.info("签名校验异常: " + ex);
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

    private static OrderInfo parse(String body) {
        return new OrderInfo();
    }

    private static String response(PayChannelEnum channel, boolean flag) {
        switch (channel) {
            case ALI_PAY:
                return flag
                        ? "<xml><return_code><![CDATA[SUCCESS]]></return_code><return_msg><![CDATA[OK]]></return_msg></xml>"
                        : "";
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
