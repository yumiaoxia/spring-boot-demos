package com.itsherman.common.email.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * <p> </p>
 *
 * @author 俞淼霞
 * @since 2019-08-23
 */
@Component
@ConfigurationProperties(prefix = "email.sender")
public class EmailSenderConfigProperties {

    private Boolean useSsl;

    private String protocal = "smtp";

    private Auth auth;

    public Auth getAuth() {
        return auth;
    }

    public void setAuth(Auth auth) {
        this.auth = auth;
    }

    public Boolean getUseSsl() {
        return useSsl;
    }

    public void setUseSsl(Boolean useSsl) {
        this.useSsl = useSsl;
    }

    public String getProtocal() {
        return protocal;
    }

    public void setProtocal(String protocal) {
        this.protocal = protocal;
    }

   public static class Auth{

        private String account;

        private String password;

        public String getAccount() {
            return account;
        }

        public void setAccount(String account) {
            this.account = account;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

       @Override
       public String toString() {
           return "Auth{" +
                   "account='" + account + '\'' +
                   ", password='" + password + '\'' +
                   '}';
       }
   }
}
