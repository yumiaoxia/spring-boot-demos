package com.itsherman.common.email.enums;

/**
 * <p> </p>
 *
 * @author 俞淼霞
 * @since 2019-08-23
 */
public enum EmailServerType {
    QQ("qq","qq.com"),
    WANGYI_163("163","163.com"),
    WANGYI_126("126","126.com");

    EmailServerType(String value,String flagKey) {
        this.value=value;
        this.flagKey = flagKey;
    }

    private String value;
    private String flagKey;


    public String getFlagKey() {
        return this.flagKey;
    }

    public String getValue() {
        return this.value;
    }

}
