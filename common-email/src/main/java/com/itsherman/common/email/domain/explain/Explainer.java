package com.itsherman.common.email.domain.explain;

import cn.hutool.core.io.FileUtil;
import com.itsherman.common.email.domain.Attachment;
import com.itsherman.common.email.domain.EmailInfo;
import com.itsherman.common.email.response.ResultMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.internet.MimeUtility;
import java.io.IOException;
import java.util.Collection;
import java.util.List;

/**
 * <p>邮件解析器</p>
 *
 * @author 俞淼霞
 * @since 2019-08-31
 */
public class Explainer {
    private static Logger log = LoggerFactory.getLogger(Explainer.class);

    public ResultMessage<List<EmailInfo>> explain(Collection<Message> messages) {
        if (messages == null) {
            throw new NullPointerException("messages must be not null");
        }

        List<EmailInfo> returnEmailInfos = doExplain(messages);
        if (returnEmailInfos != null) {
            emailInfos.addAll(returnEmailInfos);
        }
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
                emailInfo.setTextContent(textContent);
            } else if (bodyPart.getContentType().startsWith("application/octet-stream")) {
                Attachment attachment = new Attachment();
                attachment.setFileName(MimeUtility.decodeText(bodyPart.getFileName()));
                attachment.setAutualSize(bodyPart.getSize());
                attachment.setEmailInfo(emailInfo);
                attachment.setDisplaySize(FileUtil.readableFileSize(attachment.getAutualSize()));
                attachment.setInputStream(bodyPart.getInputStream());
                emailInfo.getAttachments().add(attachment);
            } else {
                log.info("其他内容无法解析，contentType:{}, content: {}", bodyPart.getContentType(), bodyPart.getContent());
            }
        }
    }

}
