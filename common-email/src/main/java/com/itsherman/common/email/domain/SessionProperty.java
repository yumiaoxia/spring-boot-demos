package com.itsherman.common.email.domain;

import com.itsherman.common.email.enums.SendOrReceiveEnum;

/**
 * <p> </p>
 *
 * @author 俞淼霞
 * @since 2019-08-23
 */
public class SessionProperty {
    private Boolean useSsl;

    private String protocal;

    private SendOrReceiveEnum sendOrReceiveEnum;

    private UsernamePasswordToken auth;

    public SendOrReceiveEnum getSendOrReceiveEnum() {
        return sendOrReceiveEnum;
    }

    public void setSendOrReceiveEnum(SendOrReceiveEnum sendOrReceiveEnum) {
        this.sendOrReceiveEnum = sendOrReceiveEnum;
    }

    public UsernamePasswordToken getAuth() {
        return auth;
    }

    public void setAuth(UsernamePasswordToken auth) {
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
