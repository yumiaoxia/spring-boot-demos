package com.itsherman.web.common.response;

import com.itsherman.web.common.config.properties.ApiLogProperties;
import com.itsherman.web.common.enums.ApiLogEnum;
import com.itsherman.web.common.request.RequestWrapper;
import com.itsherman.web.common.utils.IPUtil;
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
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Enumeration;
import java.util.Map;
import java.util.Set;

/**
 * <p> </p>
 *
 * @author 俞淼霞
 * @since 2019-09-06
 */
@ControllerAdvice(annotations = {RestController.class, ResponseBody.class, ControllerAdvice.class})
public class ResponseBodyHandlerAdvice implements ResponseBodyAdvice<ApiResponse> {

    private static final Logger log = LoggerFactory.getLogger(ResponseBodyHandlerAdvice.class);

    private static final String APPLICATION_JSON = "application/json";

    @Autowired
    private ApiLogProperties apiLogProperties;

    @Autowired
    private MessageSource messageSource;

    @Override
    public boolean supports(MethodParameter methodParameter, Class<? extends HttpMessageConverter<?>> aClass) {
        return true;
    }

    @Override
    public ApiResponse beforeBodyWrite(ApiResponse apiResponse, MethodParameter methodParameter, MediaType mediaType, Class<? extends HttpMessageConverter<?>> aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
        if (apiResponse != null) {
            HttpServletRequest request = ((ServletServerHttpRequest) serverHttpRequest).getServletRequest();
            apiResponse.setMessage(messageSource.getMessage(apiResponse.getCode(), null, LocaleContextHolder.getLocale()));
            if (apiResponse.getSuccess().equals(true)) {
                if (apiLogProperties.getType().equals(ApiLogEnum.ALL)) {
                    log.info("\nrequestURL: {},\nheaders:{}, \nparams: {}, \nuserIP: {}, \ndata: {}", request.getRequestURL(), getRequestHeaders(request), getRequestParams(request), IPUtil.getUserIP(request), apiResponse.getData());
                }
            }
            if (apiResponse.getSuccess().equals(false) && !apiLogProperties.getType().equals(ApiLogEnum.NONE)) {
                log.error("\nException Occurred! \nrequestURL: {},\nheaders:{}, \nparams: {}, \nuserIP: {}, \nerrorMessage: {}", request.getRequestURL(), getRequestHeaders(request), getRequestParams(request), IPUtil.getUserIP(request), apiResponse.getMessage());
            }
        }
        return apiResponse;
    }


    protected String getRequestParams(HttpServletRequest request) {
        StringBuilder result = new StringBuilder();
        if (request.getContentType() != null && request.getContentType().equalsIgnoreCase(APPLICATION_JSON)) {
            HttpServletRequestWrapper requestWrapper = new RequestWrapper(request);
            ServletInputStream inputStream = null;
            InputStreamReader reader = null;
            BufferedReader bufferedReader = null;
            try {
                inputStream = requestWrapper.getInputStream();
                reader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
                bufferedReader = new BufferedReader(reader);
                String line = "";
                StringBuilder sb = new StringBuilder("requestBody: ");
                while ((line = bufferedReader.readLine()) != null) {
                    sb.append(line);
                }
                result = result.append(sb);
                result = result.append(", ");
                bufferedReader.close();
                reader.close();
                inputStream.close();
            } catch (IOException e) {
                log.error(e.getMessage(), e);
            }
        }
        Map<String, String[]> parameterMap = request.getParameterMap();
        Set<Map.Entry<String, String[]>> entries = parameterMap.entrySet();
        StringBuilder paramBuilder = new StringBuilder("paramMap: {");
        int i = 0;
        for (Map.Entry<String, String[]> entry : entries) {
            if (i != 0) {
                paramBuilder.append(",");
            }
            paramBuilder.append(entry.getKey());
            paramBuilder.append(": '");
            int j = 0;
            for (String value : entry.getValue()) {
                if (j != 0) {
                    paramBuilder.append(",");
                }
                paramBuilder.append(value);
                j++;
            }
            paramBuilder.append("'");
            i++;
        }
        paramBuilder.append("}");
        result = result.append(paramBuilder);
        return result.toString();
    }

    protected String getRequestHeaders(HttpServletRequest request) {
        Enumeration<String> headerNames = request.getHeaderNames();
        StringBuilder headerSb = new StringBuilder();
        headerSb.append("{");
        int i = 0;
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            String headerValue = request.getHeader(headerName);
            if (i != 0) {
                headerSb.append(",");
            }
            headerSb.append(headerName).append(": ").append("'").append(headerValue).append("'");
            i++;
        }
        headerSb.append("}");
        return headerSb.toString();
    }
}
