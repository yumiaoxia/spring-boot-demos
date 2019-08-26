package com.itsherman.common.email.domain.receive;

import com.itsherman.common.email.config.EmailReceiverConfigProperties;
import com.itsherman.common.email.domain.SessionPropertity;
import com.itsherman.common.email.domain.send.EmailSender;
import com.itsherman.common.email.response.ResultMessage;
import com.itsherman.common.email.session.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.mail.Session;

/**
 * <p> </p>
 *
 * @author 俞淼霞
 * @since 2019-08-26
 */
public class Receiver {
    private static final Logger log = LoggerFactory.getLogger(EmailSender.class);

    @Autowired
    private EmailReceiverConfigProperties emailReceiverConfigProperties;


    public ResultMessage receive() {
        ResultMessage resultMsg = new ResultMessage(false, "Email Sending", "Exception", "Unknown Exception");
        SessionPropertity sessionPropertity = new SessionPropertity();
        BeanUtils.copyProperties(emailReceiverConfigProperties, sessionPropertity);
        log.info("connect message: {}", sessionPropertity);
        Session session = SessionFactory.openSession(sessionPropertity);
        return resultMsg;
    }
}
