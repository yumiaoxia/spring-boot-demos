package com.itsherman.sje.web.command;

import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * <p> </p>
 *
 * @author 俞淼霞
 * @since 2019-10-18
 */
@ApiModel
@Data
public class OrderItemCreateCommand {


    private String itemName;

    private long quantity;


}
