package com.itsherman.common.email.session;

import com.itsherman.common.email.constants.PopKeyConstants;
import com.itsherman.common.email.domain.EmailServerInfo;
import com.itsherman.common.email.domain.SessionPropertity;
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
        EmailProtocal emailProtocal = Arrays.stream(EmailProtocal.values()).filter(protocal -> protocal.getValue().equals(sessionPropertity.getProtocal())).findFirst().orElse(EmailProtocal.SMTP);
        EmailServerType emailServerType = Arrays.stream(EmailServerType.values()).filter(serverType->account.contains(serverType.getFlagKey())).findFirst().orElse(null);
        if(emailServerType == null){
            throw new IllegalArgumentException("The address of From is incorrect");
        }
        EmailServerInfo serverInfo = new EmailServerInfo(emailServerType,emailProtocal);
        Properties prop = new Properties();
        prop.setProperty(PopKeyConstants.TRANSPORT_PROTOCAL,emailProtocal.getValue());
        prop.put(PopKeyConstants.NO_SSL_SMTP_HOST,serverInfo.getHost());
        if(!sessionPropertity.getUseSsl()){
            prop.put(PopKeyConstants.SMTP_PORT, serverInfo.getPort());
            prop.put(PopKeyConstants.NO_SSL_SMTP_AUTH,true);
        }else{
            prop.put(PopKeyConstants.SMTP_SSL_ENABLE, "true");
            prop.put(PopKeyConstants.SSL_SMTP_CLASS,SOCKETFACTORY_CLASS);
            prop.put(PopKeyConstants.SSL_SMTP_FALLBACK,"false");
            prop.put(PopKeyConstants.SSL_SMTP_PORT,serverInfo.getSslPort());
        }
        return Session.getDefaultInstance(prop);
    }
}
