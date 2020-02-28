package com.itsherman.web.common.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.Valid;
import java.io.Serializable;

/**
 * <p> </p>
 *
 * @author 俞淼霞
 * @since 2019-09-03
 */
@ApiModel
public class ApiRequest<T> implements Serializable {

    private static final long serialVersionUID = 7373793858406747388L;

    /**
     * 请求参数载荷
     */
    @ApiModelProperty("请求参数体，载荷")
    @JsonProperty("data")
    @Valid
    private T command;


    public T getCommand() {
        return command;
    }

    public void setCommand(T command) {
        this.command = command;
    }

    @Override
    public String toString() {
        return "ApiRequest{" +
                ", commend=" + command +
                '}';
    }
}
