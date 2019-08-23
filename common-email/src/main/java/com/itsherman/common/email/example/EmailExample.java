package com.itsherman.common.email.example;

import com.itsherman.common.email.domain.EmailMessage;
import com.itsherman.common.email.response.ResultMessage;
import com.itsherman.common.email.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * <p> </p>
 *
 * @author 俞淼霞
 * @since 2019-08-23
 */
@Component
public class EmailExample {

    @Autowired
    private EmailService emailService;

    public ResultMessage sendSimpleEmail(){
        EmailMessage emailMessage = new EmailMessage();
        emailMessage.setSubject("精彩古语")
                .to("1253950375@qq.com")
                .setContent(new EmailMessage.Content().setTextContent("学而时习之，温故而知新"));
        return emailService.send(emailMessage);
    }
}
