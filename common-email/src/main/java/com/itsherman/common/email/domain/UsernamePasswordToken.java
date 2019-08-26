package com.itsherman.common.email.domain;

import org.springframework.boot.context.properties.ConfigurationPropertiesBinding;
import org.springframework.stereotype.Component;

/**
 * <p> </p>
 *
 * @author 俞淼霞
 * @since 2019-08-26
 */
@Component
@ConfigurationPropertiesBinding
public class UsernamePasswordToken {
    private String username;
    private String password;

    public UsernamePasswordToken() {
        this(null, null);
    }

    public UsernamePasswordToken(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "UsernamePasswordToken{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
