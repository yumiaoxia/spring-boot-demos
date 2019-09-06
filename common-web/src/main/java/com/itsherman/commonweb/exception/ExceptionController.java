package com.itsherman.commonweb.exception;

import com.itsherman.commonweb.response.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

/**
 * <p> </p>
 *
 * @author 俞淼霞
 * @since 2019-09-04
 */
@ControllerAdvice
public class ExceptionController {

    private static final Logger log = LoggerFactory.getLogger(ExceptionController.class);

    @ResponseBody
    @ExceptionHandler(Exception.class)
    public ApiResponse handleException(HttpServletRequest request, Exception ex) {
        log.error("Exception Occurred: {}, requestURL: {},params: {}", ex.getMessage(), request.getRequestURL(), getRequestParams(request));
        log.error(ex.getMessage(), ex);
        return ApiResponse.createError();
    }


    private String getRequestParams(HttpServletRequest request) {
        String result = "";
        if (RequestMethod.GET.name().equals(request.getMethod())) {
            result = request.getQueryString();
        } else if (RequestMethod.POST.name().equals(request.getMethod()) || RequestMethod.PUT.name().equals(request.getMethod())) {
            HttpServletRequestWrapper requestWrapper = new HttpServletRequestWrapper(request);
            try {
                ServletInputStream inputStream = requestWrapper.getInputStream();
                InputStreamReader streamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
                BufferedReader bufferedReader = new BufferedReader(streamReader);
                String line = "";
                StringBuilder sb = new StringBuilder(line);
                while ((line = bufferedReader.readLine()) != null) {
                    sb.append(line);
                }
                result = sb.toString();
            } catch (IOException e) {
                e.printStackTrace();
                return result;
            }
        }
        return result;
    }
}
