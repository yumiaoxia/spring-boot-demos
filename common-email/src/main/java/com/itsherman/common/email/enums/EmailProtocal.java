package com.itsherman.common.email.enums;

/**
 * <p> </p>
 *
 * @author 俞淼霞
 * @since 2019-08-23
 */
public enum  EmailProtocal {
    SMTP("smtp"),POP3("pop"),IMAP("imap");

    EmailProtocal(String value) {
        this.value = value;
    }

    private String value;

    public String getValue() {
        return value;
    }
}
