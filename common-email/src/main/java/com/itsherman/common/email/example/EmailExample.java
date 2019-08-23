package com.itsherman.common.email.example;

import com.itsherman.common.email.domain.EmailMessage;
import com.itsherman.common.email.response.ResultMessage;
import com.itsherman.common.email.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;

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
                .content()
                .setTextContent("唧唧复唧唧，木兰当户织");
        return emailService.send(emailMessage);
    }

    public ResultMessage sendTemplateEmail() throws ParserConfigurationException, TransformerException, SAXException, IOException {
        DemoMessageMeta messageMeta = new DemoMessageMeta("Sherman", "此去经年，多少不更事", "2019-08-22 20:00:00");
        DemoAssembler assembler = new DemoAssembler();
        assembler.setTemplateUrl(EmailExample.class.getClassLoader().getResource("").getPath() + "/static/MailTemplate.html");
        assembler.setDemoMessageMeta(messageMeta);
        EmailMessage emailMessage = new EmailMessage();
        emailMessage.setSubject("校园通知")
                .to("1253950375@qq.com")
                .content()
                .setTextContent(assembler.assembleEmailMessage());
        return emailService.send(emailMessage);
    }
}
