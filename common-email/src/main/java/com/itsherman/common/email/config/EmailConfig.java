package com.itsherman.common.email.config;

import com.itsherman.common.email.domain.explain.EmailExplainer;
import com.itsherman.common.email.domain.receive.EmailReceiver;
import com.itsherman.common.email.domain.send.EmailSender;
import com.itsherman.common.email.pool.MessagePool;
import com.itsherman.common.email.service.EmailService;
import com.itsherman.common.email.service.impl.EmailServiceImpl;
import com.itsherman.common.email.session.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <p> </p>
 *
 * @author 俞淼霞
 * @since 2019-08-23
 */
@Configuration
@EnableConfigurationProperties(EmailSenderConfigProperties.class)
public class EmailConfig {

    @Bean
    public EmailSender emailSender(){
        EmailSender emailSender = new EmailSender();
        return emailSender;
    }

    @Autowired
    public SessionFactory sessionFactory(){
        SessionFactory sessionFactory = new SessionFactory();
        return sessionFactory;
    }

    @Bean
    public EmailService emailService(){
        EmailServiceImpl emailService = new EmailServiceImpl();
        emailService.setEmailSender(emailSender());
        emailService.setEmailReceiver(emailReceiver());
        emailService.setEmailExplainer(emailExplainer());
        emailService.setMessagePool(messagePool());
        return emailService;
    }

    @Bean
    public MessagePool messagePool(){
        return new MessagePool();
    }

    @Bean
    public EmailReceiver emailReceiver(){
        return new EmailReceiver();
    }

    @Bean
    public EmailExplainer emailExplainer() {
        return new EmailExplainer();
    }
}
