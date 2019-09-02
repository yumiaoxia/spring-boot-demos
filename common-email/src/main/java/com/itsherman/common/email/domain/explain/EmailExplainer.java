package com.itsherman.common.email.domain.explain;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.io.FileUtil;
import com.itsherman.common.email.domain.Attachment;
import com.itsherman.common.email.domain.EmailInfo;
import com.itsherman.common.email.response.ResultMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeUtility;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * <p>邮件解析器</p>
 *
 * @author 俞淼霞
 * @since 2019-08-31
 */
public class EmailExplainer {

    private static Logger log = LoggerFactory.getLogger(EmailExplainer.class);

    public ResultMessage<List<EmailInfo>> explain(Collection<Message> messages) throws MessagingException, IOException {
        if (messages == null) {
            throw new NullPointerException("messages must be not null");
        }
        List<EmailInfo> emailInfos = new ArrayList<>();
        ResultMessage<List<EmailInfo>> msg = new ResultMessage<>(false, "Explain Messages", "SYSTEM ERROR", "Unknown Exception", emailInfos);
        Folder folder = null;
        Store store = null;
        int i = 0;
        for (Message message : messages) {
            folder = message.getFolder();
            store = folder.getStore();
            if (!folder.isOpen()) {
                folder.open(Folder.READ_WRITE);
            }
            if (!store.isConnected()) {
                store.connect();
            }
            log.info("EXPLAIN: no: {}, subject:{}", i, message.getSubject());
            InternetAddress fromAddress = (InternetAddress) message.getFrom()[0];
            String from = fromAddress.getAddress();
            EmailInfo emailInfo = new EmailInfo();
            emailInfo.setId(String.valueOf(i));
            emailInfo.setSubject(message.getSubject());
            emailInfo.setFrom(from);
            emailInfo.setSendDate(message.getSentDate());
            emailInfo.setReceiveDate(message.getReceivedDate());
            try {
                if (message.getContent() instanceof Multipart) {
                    Multipart rootPart = (Multipart) message.getContent();
                    doExplain(rootPart, emailInfo, String.valueOf(i));
                } else if (CollectionUtil.isNotEmpty(message.getAllHeaders()) && message.isMimeType("text/*")) {
                    String textContent = (String) message.getContent();
                    textContent = MimeUtility.decodeText(textContent);
                    emailInfo.getTextContents().add(textContent);
                } else {
                    log.warn("can'not explain the part,contentType: {}, NO: {}", message.getContentType(), i);
                }
            } catch (MessagingException e) {
                i++;
                continue;
            }
            emailInfos.add(emailInfo);
            i++;
        }
        if (folder.isOpen()) {
            folder.close(true);
        }
        if (store.isConnected()) {
            store.close();
        }
        msg = new ResultMessage<>(true, "Explain Messages", "SUCCESS", "", emailInfos);
        return msg;
    }

    private void doExplain(Multipart multipart, EmailInfo emailInfo, String emailInfoId) throws MessagingException, IOException {
        int partSize = multipart.getCount();
        log.info("NO: {}, partSize: {}", emailInfoId, partSize);
        for (int i = partSize - 1; i >= 0; i--) {
            BodyPart bodyPart = multipart.getBodyPart(i);
            emailInfoId = emailInfoId + "." + i;
            if (bodyPart.isMimeType("multipart/*")) {
                Multipart part = (Multipart) bodyPart.getContent();
                doExplain(part, emailInfo, emailInfoId);
            } else if (bodyPart.isMimeType("Text/*")) {
                String textContent = (String) bodyPart.getContent();
                emailInfo.getTextContents().add(MimeUtility.decodeText(textContent));
            } else if (bodyPart.isMimeType("application/octet-stream")) {
                Attachment attachment = new Attachment();
                attachment.setFileName(MimeUtility.decodeText(bodyPart.getFileName()));
                attachment.setAutualSize(bodyPart.getSize());
                attachment.setEmailInfo(emailInfo);
                attachment.setDisplaySize(FileUtil.readableFileSize(attachment.getAutualSize()));
                attachment.setInputStream(bodyPart.getInputStream());
                emailInfo.getAttachments().add(attachment);
            } else {
                log.info("the content can not explain，emailInfoId: {},contentType:{}, content: {}", emailInfoId, bodyPart.getContentType(), bodyPart.getContent());
            }
        }
    }

}
