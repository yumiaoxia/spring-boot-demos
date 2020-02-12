package com.itsherman.web.common.exception;

import com.itsherman.web.common.request.RequestWrapper;
import com.itsherman.web.common.response.ApiResponse;
import com.itsherman.web.common.utils.IPUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.Enumeration;
import java.util.Map;
import java.util.Set;

/**
 * <p> </p>
 *
 * @author 俞淼霞
 * @since 2019-09-04
 */
@ControllerAdvice
public class ExceptionController {
    private static final String APPLICATION_JSON = "application/json";
    private static final Logger log = LoggerFactory.getLogger(ExceptionController.class);

    @ResponseBody
    @ExceptionHandler(Exception.class)
    public ApiResponse handleException(HttpServletRequest request, Exception ex) {
        log.error("\nException Occurred! \nrequestURL: {},\nheaders:{}, \nparams: {}, \nuserIP: {}", request.getRequestURL(), getRequestHeaders(request), getRequestParams(request), IPUtil.getUserIP(request), ex);
        return ApiResponse.createError();
    }

    @ResponseBody
    @ExceptionHandler(ServiceException.class)
    public ApiResponse handleServiceException(HttpServletRequest request, ServiceException ex) {
        log.error("\nException Occurred! \nrequestURL: {},\nheaders:{}, \nparams: {}, \nuserIP: {}", request.getRequestURL(), getRequestHeaders(request), getRequestParams(request), IPUtil.getUserIP(request), ex);
        return ApiResponse.createError(ex.getErrorCode());
    }


    protected String getRequestParams(HttpServletRequest request) {
        StringBuilder result = new StringBuilder();
        if (request.getContentType().equals(APPLICATION_JSON)) {
            HttpServletRequestWrapper requestWrapper = new RequestWrapper(request);
            ServletInputStream inputStream = null;
            InputStreamReader reader = null;
            BufferedReader bufferedReader = null;
            try {
                inputStream = requestWrapper.getInputStream();
                reader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
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
