package com.xxscloud.gpay;

import com.alipay.api.CertAlipayRequest;
import com.xxscloud.gpay.alipay.AliPay;
import com.xxscloud.gpay.data.PayChannelEnum;
import com.xxscloud.gpay.wxpay.WeChatPay;
import com.xxscloud.gpay.wxpay.WeChatPayClient;

public final class GPayFactory {
    public static IPay getPay(PayChannelEnum channel, Object obj) {
        switch (channel) {
            case ALI_PAY:
                if (!(obj instanceof CertAlipayRequest)) {
                    throw new PayIOException("client is error");
                }
                return new AliPay(obj);
            case WE_CHAT_PAY:
                if (!(obj instanceof WeChatPayClient)) {
                    throw new PayIOException("client is error");
                }
                return new WeChatPay(obj);
            default:
                throw new PayIOException("create pay fail");
        }
    }
}
