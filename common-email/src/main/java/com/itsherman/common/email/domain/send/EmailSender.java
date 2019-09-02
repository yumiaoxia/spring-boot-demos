package com.itsherman.common.email.domain.send;

import cn.hutool.core.collection.CollectionUtil;
import com.itsherman.common.email.config.EmailSenderConfigProperties;
import com.itsherman.common.email.domain.SessionProperty;
import com.itsherman.common.email.enums.SendOrReceiveEnum;
import com.itsherman.common.email.response.ResultMessage;
import com.itsherman.common.email.session.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p> </p>
 *
 * @author 俞淼霞
 * @since 2019-08-23
 */
public class EmailSender {
    private static final Logger log = LoggerFactory.getLogger(EmailSender.class);
    private static final SendOrReceiveEnum SEND_OR_RECEIVE_ENUM = SendOrReceiveEnum.SEND;

    @Autowired
    private EmailSenderConfigProperties emailSenderConfigProperties;


    public ResultMessage send(EmailMessage emailMessage){
        ResultMessage resultMsg = new ResultMessage(false,"Email Sending","Exception","Unknown Exception");
        SessionProperty sessionProperty = new SessionProperty();
        BeanUtils.copyProperties(emailSenderConfigProperties, sessionProperty);
        sessionProperty.setSendOrReceiveEnum(SEND_OR_RECEIVE_ENUM);
        log.info("connect message: {}", sessionProperty);
        Session session = SessionFactory.openSession(sessionProperty);
        try {
            MimeMessage message = buildEmailMessage(emailMessage, session, sessionProperty.getAuth().getUsername());
            Transport transport = session.getTransport();
            transport.connect(sessionProperty.getAuth().getUsername(), sessionProperty.getAuth().getPassword());
            transport.sendMessage(message, message.getAllRecipients());
            resultMsg = new ResultMessage(true, "Email Sending","SUCCESS", "Send Successfully!");
            transport.close();
        } catch (MessagingException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return resultMsg;
    }

    private MimeMessage buildEmailMessage(EmailMessage emailMessage, Session session, String from) throws MessagingException, UnsupportedEncodingException {
        MimeMessage message = new MimeMessage(session);
        message.setFrom(new InternetAddress(from));
        message.setSubject(emailMessage.getSubject());
        Address[] allRicipients = emailMessage.getAllRicipients();
        message.setRecipients(Message.RecipientType.TO, allRicipients);
        message.addRecipient(Message.RecipientType.CC, new InternetAddress(from));
        message.setSentDate(new Date());
        Multipart multipart = new MimeMultipart();
        //发送文本内容
        List<String> textContents = emailMessage.content().getTextContents();
        if (CollectionUtil.isNotEmpty(textContents)) {
            for (String textContent : textContents) {
                BodyPart textPart = new MimeBodyPart();
                log.info("send content：" + textContent);
                textPart.setContent(textContent, "Text/html; charset=utf-8");
                multipart.addBodyPart(textPart);
            }
        }
        // 发送附件
        Map<String, File> attachmentMap = emailMessage.content().getAttachmentMap();
        if (attachmentMap != null && attachmentMap.size() > 0) {
            for (Map.Entry<String, File> attachmentEntry : attachmentMap.entrySet()) {
                BodyPart attachmentPart = new MimeBodyPart();
                DataSource dataSource = new FileDataSource(attachmentEntry.getValue());
                attachmentPart.setDataHandler(new DataHandler(dataSource));
                attachmentPart.setFileName(MimeUtility.encodeText(attachmentEntry.getKey()));
                multipart.addBodyPart(attachmentPart);
            }
        }
        message.setContent(multipart);
        message.saveChanges();
        return message;
    }


}
