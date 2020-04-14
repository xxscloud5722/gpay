package com.github.xxscloud5722;

import com.alipay.api.CertAlipayRequest;
import com.github.xxscloud5722.alipay.AliPay;
import com.github.xxscloud5722.wxpay.WeChatPay;
import com.github.xxscloud5722.wxpay.WeChatPayClient;
import com.github.xxscloud5722.data.LogTypeEnum;
import com.github.xxscloud5722.data.PayChannelEnum;

/**
 * @author Cat.
 */
public final class GPayFactory {
    public static IPay getPay(PayChannelEnum channel, Object obj) {
        return getPay(channel, obj, null);
    }

    public static IPay getPay(PayChannelEnum channel, Object obj, PayLogCallback callback) {
        switch (channel) {
            case ALI_PAY:
                if (!(obj instanceof CertAlipayRequest)) {
                    throw new PayIOException("client is error");
                }
                return new AliPay(obj, callback);
            case WE_CHAT_PAY:
                if (!(obj instanceof WeChatPayClient)) {
                    throw new PayIOException("client is error");
                }
                return new WeChatPay(obj, callback);
            default:
                throw new PayIOException("create pay fail");
        }
    }

    public interface PayLogCallback {
        void run(String m, String id, LogTypeEnum logType, String body);
    }
}
