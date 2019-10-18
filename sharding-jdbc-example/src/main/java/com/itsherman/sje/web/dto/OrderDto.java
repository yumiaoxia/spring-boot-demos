package com.itsherman.sje.web.dto;

import com.itsherman.commondto2.annotations.DtoModel;
import com.itsherman.sje.model.Order;
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
@DtoModel(from = Order.class)
public class OrderDto {

    @ApiModelProperty("订单号")
    private Long orderId;

    @ApiModelProperty("买家名字")
    private String buyerName;

    @ApiModelProperty("留言")
    private String memo;

    @ApiModelProperty
    private List<OrderItemDto> orderItems = new ArrayList<>();
}
