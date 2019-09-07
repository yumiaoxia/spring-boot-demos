package com.itsherman.commonweb.exception;

import com.itsherman.commonweb.request.RequestWrapper;
import com.itsherman.commonweb.response.ApiResponse;
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
import java.util.Map;

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
        log.error("Exception Occurred! requestURL: {}, params: {}", request.getRequestURL(), getRequestParams(request), ex);
        return ApiResponse.createError();
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
                bufferedReader.close();
                reader.close();
                inputStream.close();
            } catch (IOException e) {
                log.error(e.getMessage(), e);
            }
        }
        Map<String, String[]> parameterMap = request.getParameterMap();
        result = result.append(parameterMap);
        return result.toString();
    }
}
