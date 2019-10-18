package com.itsherman.sje.web.command;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * <p> </p>
 *
 * @author 俞淼霞
 * @since 2019-10-18
 */
@Data
@ApiModel
public class OrderCreateCommand {

    @ApiModelProperty("买家名字")
    private String buyerName;

    @ApiModelProperty("留言")
    private String memo;

    @ApiModelProperty
    private List<OrderItemCreateCommand> orderItems = new ArrayList<>();
}
