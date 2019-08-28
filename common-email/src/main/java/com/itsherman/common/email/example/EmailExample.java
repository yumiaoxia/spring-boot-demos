package com.itsherman.common.email.example;

import com.itsherman.common.email.domain.send.EmailMessage;
import com.itsherman.common.email.response.ResultMessage;
import com.itsherman.common.email.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.File;
import java.io.IOException;

/**
 * <p>使用实例</p>
 *
 * @author 俞淼霞
 * @since 2019-08-23
 */
@Component
public class EmailExample {

    @Autowired
    private EmailService emailService;

    /**
     * 发送简单文本邮件
     *
     * @return
     */
    public ResultMessage sendSimpleEmail(){
        EmailMessage emailMessage = new EmailMessage();
        emailMessage.setSubject("精彩古语")
                .to("1253950375@qq.com")
                .content()
                .setTextContent("唧唧复唧唧，木兰当户织");
        return emailService.send(emailMessage);
    }

    /**
     * 发送带模板的邮件
     * @return
     * @throws ParserConfigurationException
     * @throws TransformerException
     * @throws SAXException
     * @throws IOException
     */
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

    public ResultMessage sendAttachmentEmail() {
        EmailMessage emailMessage = new EmailMessage();
        File file1 = new File("F:/笔记库/邮件附件.rar");
        File file2 = new File("F:/笔记库/技术推送.rar");
        emailMessage.setSubject("附件邮件")
                .to("1253950375@qq.com")
                .content()
                .setTextContent("一封带附件的邮件，可以携带多个附件")
                .addAttachment("附件邮件.rar", file1)
                .addAttachment("技术推送.rar", file2);
        return emailService.send(emailMessage);
    }

    public ResultMessage receiveEmails(){
        return emailService.receive();
    }
}
