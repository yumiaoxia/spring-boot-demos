package com.itsherman.common.email.config;

import com.itsherman.common.email.domain.UsernamePasswordToken;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * <p> </p>
 *
 * @author 俞淼霞
 * @since 2019-08-26
 */
@Component
@ConfigurationProperties(prefix = "email.receive")
public class EmailReceiverConfigProperties {
    private Boolean userSsl;

    private String protocal = "imap";

    private ReceiveUsernamePasswordToken auth;

    public ReceiveUsernamePasswordToken getAuth() {
        return auth;
    }

    public void setAuth(ReceiveUsernamePasswordToken auth) {
        this.auth = auth;
    }

    public Boolean getUserSsl() {
        return userSsl;
    }

    public void setUserSsl(Boolean userSsl) {
        this.userSsl = userSsl;
    }

    public String getProtocal() {
        return protocal;
    }

    public void setProtocal(String protocal) {
        this.protocal = protocal;
    }

    public static class ReceiveUsernamePasswordToken extends UsernamePasswordToken {
    }
}
