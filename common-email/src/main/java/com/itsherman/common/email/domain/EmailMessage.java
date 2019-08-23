package com.itsherman.common.email.domain;

import javax.mail.Address;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * <p> </p>
 *
 * @author 俞淼霞
 * @since 2019-08-23
 */
public class EmailMessage {

    private String subject;

    private Content content;

    private List<String> toAddresses = new ArrayList<>();

    public String getSubject() {
        return subject;
    }

    public EmailMessage setSubject(String subject) {
        this.subject = subject;
        return this;
    }

    public EmailMessage to(String... toAddresses) {
        this.toAddresses.addAll(Arrays.asList(toAddresses));
        return this;
    }

    public Address[] getAllRicipients() {
        Address[] collect = new Address[toAddresses.size()];
        try {
            for (int i = 0; i < toAddresses.size(); i++) {
                collect[i] = new InternetAddress(toAddresses.get(i));
            }
        }catch (AddressException e){
            e.printStackTrace();
        }
        return collect;
    }

    public Content getContent() {
        return content;
    }

    public EmailMessage setContent(Content content) {
        this.content = content;
        return this;
    }

    public List<String> getToAddresses() {
        return toAddresses;
    }

    public void setToAddresses(List<String> toAddresses) {
        this.toAddresses = toAddresses;
    }

    public static class Content{

        private String textContent;
        private File attachment;

        public String getTextContent() {
            return textContent;
        }

        public Content setTextContent(String textContent) {
            this.textContent = textContent;
            return this;
        }

        public File getAttachment() {
            return attachment;
        }

        public Content setAttachment(File attachment) {
            this.attachment = attachment;
            return this;
        }
    }
}
