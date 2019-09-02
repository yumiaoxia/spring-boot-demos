package com.itsherman.common.email.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p> </p>
 *
 * @author 俞淼霞
 * @since 2019-08-31
 */
public class EmailInfo implements Serializable {
    private String id;
    private String subject;
    private Date sendDate;
    private Date receiveDate;
    private String from;

    private List<String> textContents = new ArrayList<>();
    private List<Attachment> attachments = new ArrayList<>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public List<String> getTextContents() {
        return textContents;
    }

    public Date getSendDate() {
        return sendDate;
    }

    public void setSendDate(Date sendDate) {
        this.sendDate = sendDate;
    }

    public Date getReceiveDate() {
        return receiveDate;
    }

    public void setReceiveDate(Date receiveDate) {
        this.receiveDate = receiveDate;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public List<Attachment> getAttachments() {
        return attachments;
    }

    @Override
    public String toString() {
        return "EmailInfo{" +
                "id='" + id + '\'' +
                ", subject='" + subject + '\'' +
                ", sendDate=" + sendDate +
                ", receiveDate=" + receiveDate +
                ", from='" + from + '\'' +
                ", textContents=" + textContents +
                ", attachments=" + attachments +
                '}';
    }
}
