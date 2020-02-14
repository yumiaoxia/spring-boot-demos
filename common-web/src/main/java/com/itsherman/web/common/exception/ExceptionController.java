package com.itsherman.web.common.exception;

import com.itsherman.web.common.response.ApiResponse;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * <p> </p>
 *
 * @author 俞淼霞
 * @since 2019-09-04
 */
@ControllerAdvice
public class ExceptionController {


    @Resource
    private MessageSource messageSource;

    @ResponseBody
    @ExceptionHandler(Exception.class)
    public ApiResponse handleException(HttpServletRequest request, Exception ex) {
        return ApiResponse.createError();
    }

    @ResponseBody
    @ExceptionHandler(ServiceException.class)
    public ApiResponse handleServiceException(HttpServletRequest request, ServiceException ex) {
        ApiResponse<?> apiResponse = ApiResponse.createError(ex.getErrorCode());
        try {
            apiResponse.setMessage(messageSource.getMessage(ex.getErrorCode(), ex.getVariables(), LocaleContextHolder.getLocale()));
        } catch (Exception e) {
            apiResponse.setMessage(ex.getMessage());
        }
        return apiResponse;
    }


}
