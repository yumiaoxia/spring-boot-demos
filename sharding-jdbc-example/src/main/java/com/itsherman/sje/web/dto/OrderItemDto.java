package com.itsherman.sje.web.dto;

import com.itsherman.commondto2.annotations.DtoModel;
import com.itsherman.sje.model.OrderItem;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p> </p>
 *
 * @author 俞淼霞
 * @since 2019-10-18
 */
@DtoModel(from = OrderItem.class)
public class OrderItemDto {

    @ApiModelProperty("商品名字")
    private String itemName;

    @ApiModelProperty("商品数量")
    private long quantity;

}
