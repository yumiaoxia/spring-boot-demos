package com.itsherman.common.email.domain;

import com.itsherman.common.email.config.EmailSenderConfigProperties;

/**
 * <p> </p>
 *
 * @author 俞淼霞
 * @since 2019-08-23
 */
public class SessionPropertity {
    private Boolean useSsl;

    private String protocal;

   private EmailSenderConfigProperties.Auth auth;

    public EmailSenderConfigProperties.Auth getAuth() {
        return auth;
    }

    public void setAuth(EmailSenderConfigProperties.Auth auth) {
        this.auth = auth;
    }

    public Boolean getUseSsl() {
        return useSsl;
    }

    public void setUseSsl(Boolean useSsl) {
        this.useSsl = useSsl;
    }

    public String getProtocal() {
        return protocal;
    }

    public void setProtocal(String protocal) {
        this.protocal = protocal;
    }

    @Override
    public String toString() {
        return "SessionPropertity{" +
                "useSsl=" + useSsl +
                ", protocal='" + protocal + '\'' +
                ", auth=" + auth +
                '}';
    }
}
