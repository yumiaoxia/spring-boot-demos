package com.itsherman.common.email.domain;

import com.itsherman.common.email.enums.EmailProtocal;
import com.itsherman.common.email.enums.SendOrReceiveEnum;

/**
 * <p> </p>
 *
 * @author 俞淼霞
 * @since 2019-08-26
 */
public class SessionPropertyKey {
    private final SendOrReceiveEnum sendOrReceiveEnum;
    public static EmailProtocal emailProtocal;

    private String host = "mail.%s.host";
    private String auth = "mail.%s.auth";
    private String port = "mail.%s.port";
    private String sslEnable = "mail.%s.ssl.enable";
    private String sslClass = "mail.%s.socketFactory.class";
    private String sslFallback = "mail.%s.socketFactory.fallback";
    private String sslPort = "mail.%s.socketFactory.port";

    public SessionPropertyKey(SendOrReceiveEnum sendOrReceiveEnum){
        this.sendOrReceiveEnum = sendOrReceiveEnum;
    }

    public String getHost() {
        return String.format(host, emailProtocal.getValue());
    }

    public String getAuth() {
        return String.format(auth, emailProtocal.getValue());
    }

    public String getPort() {
        return String.format(port, emailProtocal.getValue());
    }

    public String getProtocol(){return sendOrReceiveEnum.getProtocolKey();};

    public String getSslEnable() {
        return String.format(sslEnable, emailProtocal.getValue());
    }

    public String getSslClass() {
        return String.format(sslClass, emailProtocal.getValue());
    }

    public String getSslFallback() {
        return String.format(sslFallback, emailProtocal.getValue());
    }

    public String getSslPort() {
        return String.format(sslPort, emailProtocal.getValue());
    }
}
