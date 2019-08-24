package com.itsherman.common.email.domain;

import javax.mail.Address;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import java.io.File;
import java.util.*;

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

    public Content content() {
        if (this.content == null) {
            this.content = new Content();
        }
        return this.content;
    }

    public List<String> getToAddresses() {
        return toAddresses;
    }

    public void setToAddresses(List<String> toAddresses) {
        this.toAddresses = toAddresses;
    }

    public class Content {

        private String textContent;
        private Map<String, File> attachmentMap = new HashMap<>();

        public String getTextContent() {
            return textContent;
        }

        public Content setTextContent(String textContent) {
            this.textContent = textContent;
            return this;
        }

        public Content addAttachment(String fileName, File file) {
            this.attachmentMap.put(fileName, file);
            return this;
        }

        public Map<String, File> getAttachmentMap() {
            return this.attachmentMap;
        }
    }
}
