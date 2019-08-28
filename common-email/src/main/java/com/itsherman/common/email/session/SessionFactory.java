package com.itsherman.common.email.session;

import com.itsherman.common.email.domain.EmailServerInfo;
import com.itsherman.common.email.domain.SessionPropertity;
import com.itsherman.common.email.domain.SessionPropertyKey;
import com.itsherman.common.email.enums.EmailProtocal;
import com.itsherman.common.email.enums.EmailServerType;

import javax.mail.Session;
import java.util.Arrays;
import java.util.Properties;

/**
 * <p> </p>
 *
 * @author 俞淼霞
 * @since 2019-08-23
 */
public class SessionFactory {

    private static final String SOCKETFACTORY_CLASS = "javax.net.ssl.SSLSocketFactory";

    public static Session openSession(SessionPropertity sessionPropertity){
        String account = sessionPropertity.getAuth().getUsername();
        EmailProtocal emailProtocal = Arrays.stream(EmailProtocal.values()).filter(protocal -> protocal.getValue().equals(sessionPropertity.getProtocal())).findFirst().orElse(null);
        EmailServerType emailServerType = Arrays.stream(EmailServerType.values()).filter(serverType->account.contains(serverType.getFlagKey())).findFirst().orElse(null);
        if(emailServerType == null){
            throw new IllegalArgumentException("The pattern of email is incorrect");
        }
        EmailServerInfo serverInfo = new EmailServerInfo(emailServerType,emailProtocal);

        SessionPropertyKey sessionPropertyKey = new SessionPropertyKey(sessionPropertity.getSendOrReceiveEnum());
        SessionPropertyKey.emailProtocal = emailProtocal;
        Properties prop = new Properties();
        prop.setProperty(sessionPropertyKey.getProtocol(), emailProtocal.getValue());
        prop.put(sessionPropertyKey.getHost(), serverInfo.getHost());
        if(!sessionPropertity.getUseSsl()){
            prop.put(sessionPropertyKey.getPort(), serverInfo.getPort());
            prop.put(sessionPropertyKey.getAuth(), "true");
        }else{
            prop.put(sessionPropertyKey.getSslEnable(), "true");
            prop.put(sessionPropertyKey.getSslClass(), SOCKETFACTORY_CLASS);
            prop.put(sessionPropertyKey.getSslFallback(), "false");
            prop.put(sessionPropertyKey.getSslPort(), serverInfo.getSslPort());
        }
        return Session.getDefaultInstance(prop);
    }
}
