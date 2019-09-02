package com.itsherman.common.email.service.impl;

import com.itsherman.common.email.domain.EmailInfo;
import com.itsherman.common.email.domain.explain.EmailExplainer;
import com.itsherman.common.email.domain.receive.EmailReceiver;
import com.itsherman.common.email.domain.send.EmailMessage;
import com.itsherman.common.email.domain.send.EmailSender;
import com.itsherman.common.email.pool.MessagePool;
import com.itsherman.common.email.response.ResultMessage;
import com.itsherman.common.email.service.EmailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.mail.*;
import java.util.ArrayList;
import java.util.List;

/**
 * <p> </p>
 *
 * @author 俞淼霞
 * @since 2019-08-23
 */
public class EmailServiceImpl implements EmailService {
    private static final Logger log = LoggerFactory.getLogger(EmailServiceImpl.class);

    private EmailSender emailSender;

    private EmailReceiver emailReceiver;

    private EmailExplainer emailExplainer;

    private MessagePool messagePool;

    public void setMessagePool(MessagePool messagePool) {
        this.messagePool = messagePool;
    }

    public EmailSender getEmailSender() {
        return emailSender;
    }

    public void setEmailSender(EmailSender emailSender) {
        this.emailSender = emailSender;
    }

    public EmailReceiver getEmailReceiver() {
        return emailReceiver;
    }

    public EmailExplainer getEmailExplainer() {
        return emailExplainer;
    }

    public void setEmailExplainer(EmailExplainer emailExplainer) {
        this.emailExplainer = emailExplainer;
    }

    public void setEmailReceiver(EmailReceiver emailReceiver) {
        this.emailReceiver = emailReceiver;
    }

    @Override
    public ResultMessage send(EmailMessage emailMessage) {
        return emailSender.send(emailMessage);
    }

    @Override
    public ResultMessage receiveAll() {
        ResultMessage msg = null;
        try {
            msg = emailReceiver.receive();
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return msg;
    }

    @Override
    public ResultMessage<List<EmailInfo>> loadAll() {
        List<EmailInfo> emailInfos = new ArrayList<>();
        ResultMessage<List<EmailInfo>> msg = new ResultMessage<>(false, "LOAD ALL EMAILINFOS", "SYSTEM ERROR", "Unknown Exception", emailInfos);
        try {
            List<Message> messages = new ArrayList<>(messagePool.loadAllMessage());
            if (messages.size() == 0) {
                ResultMessage receive = emailReceiver.receive();
                if (receive.getSuccess()) {
                    messages.addAll(messagePool.loadAllMessage());
                    ResultMessage<List<EmailInfo>> explain = emailExplainer.explain(messages);
                    if (explain.getSuccess()) {
                        emailInfos.addAll(explain.getData());
                        msg = new ResultMessage<>(true, "LOAD ALL EMAILINFOS", "SUCCESS", "", emailInfos);
                    } else {
                        msg = new ResultMessage<>(false, "LOAD ALL EMAILINFOS", "EXPLAIN FAILED", "", emailInfos);
                    }
                } else {
                    msg = new ResultMessage<>(false, "LOAD ALL EMAILINFOS", "RECEIVE FAILED", "", emailInfos);
                }
            } else {
                msg = new ResultMessage<>(true, "LOAD ALL EMAILINFOS", "SUCCESS", "", emailInfos);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return msg;
    }

    @Override
    public ResultMessage<List<EmailInfo>> loadUndeleted() {
        List<EmailInfo> emailInfos = new ArrayList<>();
        ResultMessage<List<EmailInfo>> msg = new ResultMessage<>(false, "LOAD UNDELETED EMAILINFOS", "SYSTEM ERROR", "Unknown Exception", emailInfos);
        try {
            List<Message> messages = new ArrayList<>(messagePool.loadMessageIgnoreFlags(Flags.Flag.DELETED));
            if (messages.size() == 0) {
                ResultMessage receive = emailReceiver.receive();
                if (receive.getSuccess()) {
                    messages.addAll(messagePool.loadMessageIgnoreFlags(Flags.Flag.DELETED));
                    ResultMessage<List<EmailInfo>> explain = emailExplainer.explain(messages);
                    if (explain.getSuccess()) {
                        emailInfos.addAll(explain.getData());
                        msg = new ResultMessage<>(true, "LOAD UNDELETED EMAILINFOS", "SUCCESS", "", emailInfos);
                    } else {
                        msg = new ResultMessage<>(false, "LOAD UNDELETED EMAILINFOS", "EXPLAIN FAILED", "", emailInfos);
                    }
                } else {
                    msg = new ResultMessage<>(false, "LOAD UNDELETED EMAILINFOS", "RECEIVE FAILED", "", emailInfos);
                }
            } else {
                msg = new ResultMessage<>(true, "LOAD UNDELETED EMAILINFOS", "SUCCESS", "", emailInfos);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return msg;
    }

    @Override
    public ResultMessage<Integer> delete(Message... messages) {
        ResultMessage<Integer> msg = new ResultMessage<>(false, "DELETE EMAILS", "SYSTEM ERROR", "UNKNOWN EXCEPTION", 0);
        Folder folder = null;
        Store store = null;
        try {
            int i = 0;
            for (Message message : messages) {
                folder = message.getFolder();
                store = folder.getStore();
                if (!folder.isOpen()) {
                    folder.open(Folder.READ_WRITE);
                }
                if (!store.isConnected()) {
                    store.connect();
                }
                message.setFlag(Flags.Flag.DELETED, true);
                log.info("DELETE: NO:{}, Subject:{} ReceiveTime: {}", i++, message.getSubject(), message.getReceivedDate());
            }
            folder.close(true);
            store.close();
            msg = new ResultMessage<>(true, "DELETE EMAILS", "SUCCESS", "", i);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return msg;
    }




}
