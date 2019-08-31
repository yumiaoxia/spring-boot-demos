package com.itsherman.common.email.service.impl;

import com.itsherman.common.email.domain.EmailInfo;
import com.itsherman.common.email.domain.receive.EmailReceiver;
import com.itsherman.common.email.domain.send.EmailMessage;
import com.itsherman.common.email.domain.send.EmailSender;
import com.itsherman.common.email.pool.MessagePool;
import com.itsherman.common.email.response.ResultMessage;
import com.itsherman.common.email.service.EmailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.Store;
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

    public void setEmailReceiver(EmailReceiver emailReceiver) {
        this.emailReceiver = emailReceiver;
    }

    @Override
    public ResultMessage send(EmailMessage emailMessage) {
        return emailSender.send(emailMessage);
    }

    @Override
    public ResultMessage receiveAll() {
        return emailReceiver.receive();
    }

    @Override
    public ResultMessage<List<EmailInfo>> loadAll() {
        List<EmailInfo> emailInfos = new ArrayList<>();
        List<Message> messages = new ArrayList<>(messagePool.loadAllMessage());
        ResultMessage<List<EmailInfo>> msg = new ResultMessage<>(true, "List Messages", "SUCCESS", "", emailInfos);
        if (CollectionUtils.isEmpty(messages)) {
            ResultMessage receiveResult = emailReceiver.receive();
            if (receiveResult.getSuccess()) {
                messages.addAll(messagePool.loadAllMessage());
            } else {
                return new ResultMessage<>(false, "List Messages", "RECEIVE FAILED", "", emailInfos);
            }
        }
        List<EmailInfo> returnEmailInfos = explainEmail(messages);
        if (returnEmailInfos != null) {
            emailInfos.addAll(returnEmailInfos);
        }
        return msg;
    }

    @Override
    public ResultMessage<List<Message>> loadUndeleted() {
        List<Message> messages = new ArrayList<>();
        ResultMessage<List<Message>> msg = new ResultMessage<>(true, "List Messages", "SUCCESS", "", messages);
        try {
            messages.addAll(messagePool.loadMessageIgnoreFlags(Flags.Flag.DELETED));
            if (messages.size() == 0) {
                ResultMessage receiveResult = emailReceiver.receive();
                if (receiveResult.getSuccess()) {
                    messages.addAll(messagePool.loadMessageIgnoreFlags(Flags.Flag.DELETED));
                } else {
                    msg = new ResultMessage<>(false, "List Messages", "RECEIVE FAILED", "", messages);
                }
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
