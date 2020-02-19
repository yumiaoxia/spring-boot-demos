package com.itsherman.web.common.exception;

import com.itsherman.web.common.response.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

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
    public ApiResponse handleException(Exception ex) {
        log.error(ex.getMessage(), ex);
        return ApiResponse.createError();
    }

    @ResponseBody
    @ExceptionHandler(ServiceException.class)
    public ApiResponse handleServiceException(ServiceException ex) {
        log.error(ex.getMessage(), ex);
        return ApiResponse.createError(ex.getErrorCode());
    }


}
