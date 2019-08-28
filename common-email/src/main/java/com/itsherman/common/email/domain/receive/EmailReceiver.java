package com.itsherman.common.email.domain.receive;

import com.itsherman.common.email.config.EmailReceiverConfigProperties;
import com.itsherman.common.email.domain.SessionProperty;
import com.itsherman.common.email.enums.SendOrReceiveEnum;
import com.itsherman.common.email.pool.MessagePool;
import com.itsherman.common.email.response.ResultMessage;
import com.itsherman.common.email.session.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.mail.*;

/**
 * <p> </p>
 *
 * @author 俞淼霞
 * @since 2019-08-26
 */
public class EmailReceiver {

    private static final Logger log = LoggerFactory.getLogger(EmailReceiver.class);
    private static final SendOrReceiveEnum SEND_OR_RECEIVE_ENUM = SendOrReceiveEnum.RECEIVE;

    @Autowired
    private EmailReceiverConfigProperties emailReceiverConfigProperties;

    @Autowired
    private MessagePool messagePool;

    public ResultMessage receive() {
        ResultMessage resultMsg = new ResultMessage(false, "Email Receiving", "Exception", "Unknown Exception");
        try{
            //获取连接
            Folder inbox = getInbox();
            inbox.open(Folder.READ_WRITE);
            log.info("connect successfully,receive total {} emails",inbox.getMessageCount());
            messagePool.init(inbox.getMessages());
            inbox.close(false);
            inbox.getStore().close();
            resultMsg = new ResultMessage(true,"Email Receive","OK",String.format("Received %s messages",inbox.getMessageCount()));
        }catch (MessagingException e){
            log.error("mail connect failed! message: {}",e.getMessage());
            e.printStackTrace();
        }

        return resultMsg;
    }


    public Folder getInbox() throws MessagingException {
        SessionProperty sessionProperty = new SessionProperty();
        BeanUtils.copyProperties(emailReceiverConfigProperties, sessionProperty);
        sessionProperty.setSendOrReceiveEnum(SEND_OR_RECEIVE_ENUM);
        log.info("connect message: {}", sessionProperty);
        Session session = SessionFactory.openSession(sessionProperty);
        Store store = session.getStore();
        store.connect(sessionProperty.getAuth().getUsername(), sessionProperty.getAuth().getPassword());
        return store.getFolder("INBOX");
    }
}
