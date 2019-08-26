package com.itsherman.common.email.domain;

import com.itsherman.common.email.enums.EmailProtocal;

/**
 * <p> </p>
 *
 * @author 俞淼霞
 * @since 2019-08-26
 */
public class SessionPropertyKey {
    public static EmailProtocal emailProtocal;

    private String host = "email.%s.host";
    private String auth = "email.%s.auth";
    private String port = "email.%s.port";
    private String protocal = "mail.transport.protocol";
    private String sslEnable = "email.%s.ssl.enabled";
    private String sslClass = "email.%s.ssl.class";
    private String sslFallback = "email.%s.ssl.fallback";
    private String sslPort = "ssl.%s.ssl.port";

    public String getHost() {
        return String.format(host, emailProtocal.getValue());
    }

    public String getAuth() {
        return String.format(auth, emailProtocal.getValue());
    }

    public String getPort() {
        return String.format(port, emailProtocal.getValue());
    }

    public String getProtocal() {
        return protocal;
    }

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
