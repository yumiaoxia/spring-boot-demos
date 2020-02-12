package com.itsherman.web.common.response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * <p> </p>
 *
 * @author 俞淼霞
 * @since 2019-09-06
 */
@ControllerAdvice(annotations = {RestController.class, ResponseBody.class, ControllerAdvice.class})
public class ResponseBodyHandlerAdvice implements ResponseBodyAdvice<ApiResponse> {

    private static final Logger log = LoggerFactory.getLogger(ResponseBodyHandlerAdvice.class);

    @Autowired
    private MessageSource messageSource;

    @Override
    public boolean supports(MethodParameter methodParameter, Class<? extends HttpMessageConverter<?>> aClass) {
        return true;
    }

    @Override
    public ApiResponse beforeBodyWrite(ApiResponse apiResponse, MethodParameter methodParameter, MediaType mediaType, Class<? extends HttpMessageConverter<?>> aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
        if (apiResponse != null) {
            String code = apiResponse.getCode();
            String msg = "";
            try {
                msg = messageSource.getMessage(code, null, LocaleContextHolder.getLocale());
                if (msg == null) {
                    msg = code;
                }
            } catch (Exception e) {
                log.error(e.getMessage(), e);
                msg = code;
            }
            apiResponse.setMessage(msg);
        }
        return apiResponse;
    }
}
