package com.itsherman.web.common.locale;

import org.springframework.boot.autoconfigure.context.MessageSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * <p> </p>
 *
 * @author 俞淼霞
 * @since 2019-09-06
 */
@Component
@ConfigurationProperties(prefix = "spring.messages")
public class BundleMessageProperties extends MessageSourceProperties {
}
