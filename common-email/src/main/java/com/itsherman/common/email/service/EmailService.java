package com.itsherman.common.email.service;

import com.itsherman.common.email.domain.EmailInfo;
import com.itsherman.common.email.domain.send.EmailMessage;
import com.itsherman.common.email.response.ResultMessage;

import javax.mail.Message;
import java.util.List;

/**
 * <p> </p>
 *
 * @author 俞淼霞
 * @since 2019-08-23
 */
public interface EmailService {

   ResultMessage send(EmailMessage emailMessage);

    ResultMessage receiveAll();

    ResultMessage<List<EmailInfo>> loadAll();

    ResultMessage<List<EmailInfo>> loadUndeleted();

    ResultMessage<Integer> delete(Message... messages);
}
