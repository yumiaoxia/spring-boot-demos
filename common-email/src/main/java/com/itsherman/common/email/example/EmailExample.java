package com.itsherman.common.email.example;

import com.itsherman.common.email.domain.EmailInfo;
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
import java.util.List;

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
        emailMessage.setSubject("鑫谷精神")
                .to("1253950375@qq.com")
                .content()
                .addTextContent("新中国成立70年来，我们党领导人民创造了世所罕见的经济发展奇迹和政治稳定奇迹，中华民族迎来了从站起来、富起来到强起来的伟大飞跃。实践证明，中国特色社会主义制度和国家治理体系是植根中国大地、具有深厚中华文化根基、深得人民拥护的制度和治理体系，是具有强大生命力和巨大优越性的制度和治理体系，是能够持续推动拥有近14亿人口大国进步和发展、确保拥有5000多年文明史的中华民族实现“两个一百年”奋斗目标进而实现伟大复兴的制度和治理体系\n" +
                        "[WareDelOrderDetail {\n" +
                        "\trecordId = 1, skuCode = '100020868', quantity = 3, itemName = '潘婷丝质顺滑去屑洗发露200毫升', itemStyle = '', originPrice = 22, actualPrice = 22.00, itemFlag = 'N', promotionFlag = 'N', promotionId = '', promotionDesc = '', promotionSequence = 1, itemWeight = 705, lineAmount = 66.000000, unitId = 99516021, parentUnitId = 99516021, promotionParentId = 99516021, orderId = '20190831145964601', prmType = '', payCommis = null, postFee = null, pointFee = null, actualPriceContainAp = null, couponFee = 0.0000, ep2PrmType = 'null', ep2PrmId = 'null', actualSinglePayment = null, actualTotalPayment = null, mixedSku = 'null'\n" +
                        "}, WareDelOrderDetail {\n" +
                        "\trecordId = 2, skuCode = '100020872', quantity = 2, itemName = '潘婷乳液修护去屑洗发露750毫升', itemStyle = '', originPrice = 68, actualPrice = 65.00, itemFlag = 'N', promotionFlag = 'N', promotionId = '', promotionDesc = '', promotionSequence = 1, itemWeight = 1686, lineAmount = 130.000000, unitId = 99516022, parentUnitId = 99516022, promotionParentId = 99516022, orderId = '20190831145964602', prmType = '', payCommis = null, postFee = null, pointFee = null, actualPriceContainAp = null, couponFee = 0.0000, ep2PrmType = 'null', ep2PrmId = 'null', actualSinglePayment = null, actualTotalPayment = null, mixedSku = 'null'\n" +
                        "}]");
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
        ResultMessage msg = null;
        try {
            DemoMessageMeta messageMeta = new DemoMessageMeta("Sherman", "此去经年，多少不更事", "2019-08-22 20:00:00");
            DemoAssembler assembler = new DemoAssembler();
            assembler.setTemplateUrl(EmailExample.class.getClassLoader().getResource("").getPath() + "/static/MailTemplate.html");
            assembler.setDemoMessageMeta(messageMeta);
            EmailMessage emailMessage = new EmailMessage();
            emailMessage.setSubject("校园通知")
                    .to("1253950375@qq.com")
                    .content()
                    .addTextContent(assembler.assembleEmailMessage());
            msg = emailService.send(emailMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return msg;
    }

    /**
     * 发送带附件邮件
     *
     * @return 返回结果
     */
    public ResultMessage sendAttachmentEmail() {
        EmailMessage emailMessage = new EmailMessage();
        File file1 = new File("F:/笔记库/邮件附件.rar");
        File file2 = new File("F:/笔记库/技术推送.rar");
        emailMessage.setSubject("技术文件")
                .to("1253950375@qq.com")
                .content()
                .addTextContent("WareDelOrderDetail {\n" +
                        "\trecordId = 2, skuCode = '100020872', quantity = 2, itemName = '潘婷乳液修护去屑洗发露750毫升', itemStyle = '', originPrice = 68, actualPrice = 65.00, itemFlag = 'N', promotionFlag = 'N', promotionId = '', promotionDesc = '', promotionSequence = 1, itemWeight = 1686, lineAmount = 130.000000, unitId = 99516022, parentUnitId = 99516022, promotionParentId = 99516022, orderId = '20190831145964602', prmType = '', payCommis = null, postFee = null, pointFee = null, actualPriceContainAp = null, couponFee = 0.0000, ep2PrmType = 'null', ep2PrmId = 'null', actualSinglePayment = null, actualTotalPayment = null, mixedSku = 'null'\n" +
                        "}]")
                .addAttachment("附件邮件.rar", file1)
                .addAttachment("技术推送.rar", file2);
        return emailService.send(emailMessage);
    }

    public ResultMessage receiveEmails() {
        return emailService.receiveAll();
    }

    public ResultMessage<List<EmailInfo>> loadALl() {
        return emailService.loadAll();
    }

    public ResultMessage<List<EmailInfo>> loadUndeleted() {
        return emailService.loadUndeleted();
    }

}
