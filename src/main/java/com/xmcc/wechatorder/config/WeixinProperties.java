package com.xmcc.wechatorder.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "wechat")
@Data
public class WeixinProperties {

    private String appid;
    private String secret;

    private String mchId;//商户号
    private String mchKey;//商户密匙
    private String keyPath;//商户证书路径
    private String notifyUrl;//微信支付异步通知
}
