package com.itsherman.common.email.service.impl;

import com.itsherman.common.email.domain.send.EmailMessage;
import com.itsherman.common.email.domain.send.EmailSender;
import com.itsherman.common.email.response.ResultMessage;
import com.itsherman.common.email.service.EmailService;

/**
 * <p> </p>
 *
 * @author 俞淼霞
 * @since 2019-08-23
 */
public class EmailServiceImpl implements EmailService {

    private EmailSender emailSender;

    public EmailSender getEmailSender() {
        return emailSender;
    }

    public void setEmailSender(EmailSender emailSender) {
        this.emailSender = emailSender;
    }


    @Override
    public ResultMessage send(EmailMessage emailMessage) {
        return emailSender.send(emailMessage);
    }
}
