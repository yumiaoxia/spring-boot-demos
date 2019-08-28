package com.itsherman.common.email.enums;

/**
 * 收发邮件类型
 * @author yumiaoxia
 * created in 2019/8/28
 * auditor: /
 * audited in /
 */
public enum SendOrReceiveEnum {

    SEND("mail.transport.protocol"),RECEIVE("mail.store.protocol");

    private String protocolKey;

    SendOrReceiveEnum(String protocolKey) {
        this.protocolKey = protocolKey;
    }

    public String getProtocolKey() {
        return protocolKey;
    }
}
