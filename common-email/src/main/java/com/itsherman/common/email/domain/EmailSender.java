package com.itsherman.common.email.domain;

import com.itsherman.common.email.config.EmailSenderConfigProperties;
import com.itsherman.common.email.response.ResultMessage;
import com.itsherman.common.email.session.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.Date;

/**
 * <p> </p>
 *
 * @author 俞淼霞
 * @since 2019-08-23
 */
public class EmailSender {
    private static final Logger log = LoggerFactory.getLogger(EmailSender.class);

    @Autowired
    private EmailSenderConfigProperties emailSenderConfigProperties;


    public ResultMessage send(EmailMessage emailMessage){
        ResultMessage resultMsg = new ResultMessage(false,"Email Sending","Exception","Unknown Exception");
        SessionPropertity sessionPropertity = new SessionPropertity();
        BeanUtils.copyProperties(emailSenderConfigProperties,sessionPropertity);
        log.info("connect message: {}",sessionPropertity);
        Session session = SessionFactory.openSession(sessionPropertity);
        try {
            MimeMessage message = buildEmailMessage(emailMessage, session, sessionPropertity.getAuth().getAccount());
            Transport transport = session.getTransport();
            transport.connect(sessionPropertity.getAuth().getAccount(),sessionPropertity.getAuth().getPassword());
            transport.sendMessage(message, message.getAllRecipients());
            resultMsg = new ResultMessage(true, "Email Sending","SUCCESS", "Send Successfully!");
            transport.close();
        }catch (MessagingException e){
            e.printStackTrace();
        }
        return resultMsg;
    }

    private MimeMessage buildEmailMessage(EmailMessage emailMessage,Session session,String from) throws MessagingException {
        MimeMessage message = new MimeMessage(session);
        message.setFrom(new InternetAddress(from));
        message.setSubject(emailMessage.getSubject());
        Address[] allRicipients = emailMessage.getAllRicipients();
        message.setRecipients(Message.RecipientType.TO, allRicipients);
        message.addRecipient(Message.RecipientType.CC, new InternetAddress(from));
        message.setSentDate(new Date());
        Multipart multipart = new MimeMultipart();
        BodyPart textPart = new MimeBodyPart();
        String textContent = emailMessage.getContent().getTextContent();
        log.info("send content："+textContent);
        textPart.setContent(textContent,"text/html; charset=utf-8");
        multipart.addBodyPart(textPart);
        message.setContent(multipart);
        message.saveChanges();
        return message;
    }


}
