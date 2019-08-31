package com.itsherman.common.email.domain;

import java.io.InputStream;

/**
 * <p> </p>
 *
 * @author 俞淼霞
 * @since 2019-08-31
 */
public class Attachment {
    private EmailInfo emailInfo;
    private String fileName;
    private Integer autualSize;
    private String displaySize;
    private InputStream inputStream;

    public EmailInfo getEmailInfo() {
        return emailInfo;
    }

    public void setEmailInfo(EmailInfo emailInfo) {
        this.emailInfo = emailInfo;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Integer getAutualSize() {
        return autualSize;
    }

    public void setAutualSize(Integer autualSize) {
        this.autualSize = autualSize;
    }

    public String getDisplaySize() {
        return displaySize;
    }

    public void setDisplaySize(String displaySize) {
        this.displaySize = displaySize;
    }

    public InputStream getInputStream() {
        return inputStream;
    }

    public void setInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    @Override
    public String toString() {
        return "Attachment{" +
                ", fileName='" + fileName + '\'' +
                ", autualSize=" + autualSize +
                ", displaySize='" + displaySize + '\'' +
                ", inputStream=" + inputStream +
                '}';
    }
}
