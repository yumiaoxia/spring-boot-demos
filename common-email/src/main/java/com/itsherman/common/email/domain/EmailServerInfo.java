package com.itsherman.common.email.domain;

import com.itsherman.common.email.enums.EmailProtocal;
import com.itsherman.common.email.enums.EmailServerType;

import java.io.FileReader;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.Properties;

/**
 * <p> </p>
 *
 * @author 俞淼霞
 * @since 2019-08-23
 */
public class EmailServerInfo {
    private static final String HOST_FORMAT = "email.%s.%s.host";
    private static final String PORT_FORMAT = "email.%s.%s.port";
    private static final String SSL_PORT_FORMAT = "email.%s.%s.ssl.port";
    private static final Properties prop =  new Properties();
    private final EmailServerType emailServerType;
    private final EmailProtocal emailProtocal;
    private String host;
    private Integer port;
    private Integer SslPort;

    public EmailServerInfo(EmailServerType emailServerType, EmailProtocal emailProtocal) {
        this.emailServerType = emailServerType;
        this.emailProtocal = emailProtocal;
    }

    static {
        try {
            prop.load(new FileReader(MessageFormat.format("{0}/mailserver.properties", EmailServerInfo.class.getClassLoader().getResource("").getPath())));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public String getHost() {
       String hostKey = String.format(HOST_FORMAT,emailServerType.getValue(),emailProtocal.getValue());
       return prop.getProperty(hostKey);
    }

    public Integer getPort() {
        String portKey =  String.format(PORT_FORMAT,emailServerType.getValue(),emailProtocal.getValue());
        return Integer.valueOf(prop.getProperty(portKey));
    }

    public Integer getSslPort() {
        String sslPortKey =  String.format(SSL_PORT_FORMAT,emailServerType.getValue(),emailProtocal.getValue());
        return Integer.valueOf(prop.getProperty(sslPortKey));
    }
}
